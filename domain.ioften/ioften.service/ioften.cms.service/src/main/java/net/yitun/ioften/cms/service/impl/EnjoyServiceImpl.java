package net.yitun.ioften.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.amqp.support.AmqpSender;
import net.yitun.basic.dict.YesNo;
import net.yitun.basic.model.Result;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.ioften.cms.ShowApi;
import net.yitun.ioften.cms.dicts.GainStatus;
import net.yitun.ioften.cms.entity.Browse;
import net.yitun.ioften.cms.model.enjoy.EnjoyDetail;
import net.yitun.ioften.cms.model.gain.GainDetail;
import net.yitun.ioften.cms.model.show.NewsDetail;
import net.yitun.ioften.cms.service.BrowseService;
import net.yitun.ioften.cms.service.EnjoyService;
import net.yitun.ioften.cms.service.GainService;
import net.yitun.ioften.cms.service.dto.GainTask;
import net.yitun.ioften.crm.conf.Conf;
import net.yitun.ioften.crm.dicts.MesgType;
import net.yitun.ioften.crm.model.mesg.MesgStore;
import net.yitun.ioften.pay.dicts.Way;

@Slf4j
@Service("cms.EnjoyService")
public class EnjoyServiceImpl implements EnjoyService {

    @Autowired
    protected ShowApi showApi;

    @Autowired
    protected AmqpSender amqp;

    @Autowired
    protected GainService gainService;

    @Autowired
    protected BrowseService browseService;

    @Override
    @Transactional
    public EnjoyDetail enjoy(Long uid, Long aid) {
        // 限制同时点赞多篇文章或多次同一文章
        if (!this.browseService.lock(uid.toString(), aid + ".enjoy", 1200L))
            return new EnjoyDetail(YesNo.NO, "您操作太频繁", null);

        // 判断文章是否不存在
        Result<NewsDetail> newsResult = null;
        if (null == (newsResult = this.showApi.major(aid)) //
                || null == newsResult.getData())
            return new EnjoyDetail(YesNo.NO, "资讯不存在", null);

        Browse browse = null;
        // 查找浏览记录，判断是否直接进入点赞
        if (null == (browse = this.browseService.get(uid, aid)))
            return new EnjoyDetail(YesNo.NO, "点赞无效", null);

        // 判断是否已享受挖矿点赞
        if (0L < browse.getEnjoyAward())
            return new EnjoyDetail(YesNo.YES, "已点赞挖矿", null);

        Way way = Way.ENJOY;
        NewsDetail news = newsResult.getData();
        // 通过资讯ID和挖矿方式就行计算奖励，如果奖励等于NULL, 可能当前不能挖矿
        GainTask task = this.gainService.calcAward(uid, aid, way, news);
        if (log.isInfoEnabled())
            log.info("<<< {}.article() calc enjoy award, task:{}", this.getClass().getName(), JsonUtil.toJson(task));

        Long award = (null == task) ? null : task.getAward();

        // 不能挖矿时，仅进行单纯的点赞，判断是否重复点赞清空
        if ((null != award && 0L < award) //
                || (null == browse || YesNo.NO == browse.getEnjoy()))
            // 更新浏览记录，记录点赞标记和点赞挖矿数量
            this.browseService.modify(uid, aid, way, award);

        if (null != award && 0L < award) // 累加分享挖矿奖励，同时执行派发分享的挖矿奖励
            this.gainService.dispatch(uid, aid, way /* way */, award, browse.getId() /* target: */);

        // 当原始浏览记录中未标记当前用户点赞时，方可向资讯的作者发送系统消息：点赞
        if (null == browse || YesNo.NO == browse.getEnjoy())
            this.amqp.syncSend(Conf.MQ_EXCHANGE, Conf.MQ_ROUTEKEY_USER_MESGS,
                    new MesgStore(news.getUsersId(), MesgType.ENJOY, uid, aid, null, null));

        GainDetail gainDetail //
                = (task.getStatus() != GainStatus.SUCCESS) ? null : new GainDetail(award, null, task.getStatus());
        return new EnjoyDetail(YesNo.YES, "已点赞", gainDetail);
    }

}
