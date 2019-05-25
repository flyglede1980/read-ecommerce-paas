package net.yitun.ioften.cms.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.Code;
import net.yitun.basic.amqp.support.AmqpSender;
import net.yitun.basic.cache.utils.IocUtil;
import net.yitun.basic.exp.BizException;
import net.yitun.basic.model.Result;
import net.yitun.basic.utils.IdUtil;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.ioften.cms.ShowApi;
import net.yitun.ioften.cms.conf.Conf;
import net.yitun.ioften.cms.conf.Constant;
import net.yitun.ioften.cms.dicts.ShareWay;
import net.yitun.ioften.cms.entity.Share;
import net.yitun.ioften.cms.model.share.ShareDetail;
import net.yitun.ioften.cms.model.show.NewsList;
import net.yitun.ioften.cms.repository.ShareRepository;
import net.yitun.ioften.cms.service.GainService;
import net.yitun.ioften.cms.service.ShareService;
import net.yitun.ioften.cms.service.dto.GainTask;
import net.yitun.ioften.crm.InviteApi;
import net.yitun.ioften.crm.UserApi;
import net.yitun.ioften.crm.model.invite.InviteMy;
import net.yitun.ioften.crm.model.user.UserDetail;
import net.yitun.ioften.pay.dicts.Way;

@Slf4j
@Service("cms.ShareService")
@CacheConfig(cacheNames = Constant.CK_SHARE)
public class ShareServiceImpl implements ShareService {

    @Autowired
    protected UserApi userApi;

    @Autowired
    protected InviteApi inviteApi;

    @Autowired
    protected ShowApi showApi;

    @Autowired
    protected AmqpSender amqp;

    @Autowired
    protected GainService gainService;

    @Autowired
    protected ShareRepository repository;

    @Override
    @Cacheable(key = "#id")
    public Share get(Long id) {
        return this.repository.get(id);
    }

    @Override
    @Transactional
    @Cacheable(key = "'by'+#way+'.'+#uid+'.'+#aid")
    public ShareDetail gen(Long uid, Long aid, ShareWay way) {
        // 判断资讯是否有效, 便于提供资讯标题、封面等信息
        Result<NewsList> news = this.showApi.basic(aid);
        if (null == news || !news.ok() || null == news.getData())
            throw new BizException(Code.BIZ_ERROR, "资讯不存在");

        Share share = null;
        // 根据当前登录用户ID以及资讯ID查找其分享记录
        if (null == (share = this.repository.lockFind(uid, aid))) {
            // 无分享记录时，则构建分析记录并持久化保存
            Date nowTime = new Date(System.currentTimeMillis());
            share = new Share(IdUtil.id(), uid, aid, way, 0L /* award */, 0 /* views */, nowTime, nowTime);
            if (!this.repository.create(share))
                throw new BizException(Code.BIZ_ERROR, "分享代码生成失败");
        }

        // 获取当前用户的邀请码信息
        InviteMy invite = this.inviteApi.code().getData();

        return new ShareDetail(share.getId(), share.getUid(), share.getAid(), share.getAward(), share.getViews(),
                share.getCtime(), share.getMtime(), news.getData(),
                null == invite ? null : new UserDetail(invite.getUid(), invite.getNick(), null, invite.getCode()));
    }

    @Override
    public ShareDetail show(Long id) {
        Share share = null;
        // 获取并锁定指定的分享记录, 不存在时只能对其就行忽略处理
        ShareService service = IocUtil.getBean(ShareService.class);
        if (null == (share = service.get(id)))
            throw new BizException(Code.NOT_FOUND, "分享代码不存在");

        // 发起分享的用户ID
        Long uid = share.getUid();
        // 获取当前发起分享用户的信息-邀请码
        Result<UserDetail> user = this.userApi.nick(uid);

        // 推送MQ消息, 当前分享有游客查看同时便于处理分享挖矿和浏览次数的累计
        this.amqp.syncSend(Conf.MQ_EXCHANGE, Conf.MQ_ROUTEKEY_SHARE_SHOW, id);

        return new ShareDetail(share.getId(), share.getUid(), share.getAid(), share.getAward(), 1 + share.getViews(),
                share.getCtime(), share.getMtime(), null, user.getData());
    }

    @Override
    @Transactional
    @CacheEvict(key = "#id", beforeInvocation = false)
    public Result<Share> calcGain(Long id) {
        Share share = null;
        // 锁定当前分享记录，如果不存在直接决绝计算和累计展示次数
        if (null == (share = this.repository.lock(id)))
            return new Result<>(Code.NOT_FOUND, "分享记录不存在");

        Way way = Way.ENJOY;
        Long uid = share.getUid(), aid = share.getAid(), award = null, oldAward = share.getAward();
        share.setAward(null); // 强制将当前已获的奖励置空, 便于非必要时忽略调账分享奖励

        // 判断是否第一次被分享展示即有效分享时，需要计算当前分享挖矿奖励
        if (null == oldAward || 0L >= oldAward) {
            // 通过资讯ID和挖矿方式就行计算奖励，如果奖励等于NULL, 可能当前不能挖矿
            GainTask task = this.gainService.calcAward(uid, aid, way, null);
            award = (null == task) ? null : task.getAward();
            if (log.isInfoEnabled())
                log.info("<<< {}.calcGain() calc share award, task:{}", this.getClass().getName(), JsonUtil.toJson(task));
        }

        // 累加分享挖矿奖励，同时执行派发分享的挖矿奖励
        if (null != award && 0L < award)
            share.setAward(award + oldAward);
        share.setViews(1 + share.getViews()); // 累加一次展示次数
        share.setMtime(new Date(System.currentTimeMillis()));
        // 更新分享展示浏览次数和首次分享挖矿直接覆盖其原始奖励
        if (!this.repository.modify(share))
            throw new BizException(Code.BIZ_ERROR, "浏览记录更新保存失败");

        if (null != award && 0L < award) // 累加分享挖矿奖励，同时执行派发分享的挖矿奖励
            this.gainService.dispatch(uid, aid, way /* way */, award, share.getId() /* target: */);

        return new Result<>(share);
    }

}
