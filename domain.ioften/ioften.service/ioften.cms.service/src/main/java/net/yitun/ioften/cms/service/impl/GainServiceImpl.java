package net.yitun.ioften.cms.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.Code;
import net.yitun.basic.amqp.support.AmqpSender;
import net.yitun.basic.dict.YesNo;
import net.yitun.basic.exp.BizException;
import net.yitun.basic.model.Result;
import net.yitun.basic.utils.CalcUtil;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.ioften.cms.ArticleApi;
import net.yitun.ioften.cms.ShowApi;
import net.yitun.ioften.cms.conf.Constant;
import net.yitun.ioften.cms.dicts.GainStatus;
import net.yitun.ioften.cms.entity.Browse;
import net.yitun.ioften.cms.model.show.NewsDetail;
import net.yitun.ioften.cms.service.BrowseService;
import net.yitun.ioften.cms.service.GainService;
import net.yitun.ioften.cms.service.dto.GainTask;
import net.yitun.ioften.pay.ConfApi;
import net.yitun.ioften.pay.RateApi;
import net.yitun.ioften.pay.WalletApi;
import net.yitun.ioften.pay.dicts.Channel;
import net.yitun.ioften.pay.dicts.Currency;
import net.yitun.ioften.pay.dicts.Way;
import net.yitun.ioften.pay.model.conf.gain.GainConfDetail;
import net.yitun.ioften.pay.model.wallet.WalletAdjust;

@Slf4j
@Service(value = "cms.GainService")
@CacheConfig(cacheNames = Constant.CK_GAIN)
public class GainServiceImpl implements GainService {

    protected Cache cache;

    @Autowired
    protected ConfApi confApi;

    @Autowired
    protected RateApi rateApi;

    @Autowired
    protected ShowApi showApi;

    @Autowired
    protected WalletApi walletApi;

    @Autowired
    protected ArticleApi articleApi;

    @Autowired
    protected AmqpSender amqp;

    @Autowired
    protected BrowseService browseService;

    @Autowired
    protected CacheManager cacheManager;

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());

        this.cache = this.cacheManager.getCache(Constant.CK_GAIN);
    }

    @Override
    @Caching(//
            cacheable = { @Cacheable(key = "'view.'+#uid") }, // 仅用用户ID做缓存KEY, 记录当前唯一文章的挖坑任务, 规避多篇文章同时挖矿
            evict = { @CacheEvict(key = "'view.'+#uid", beforeInvocation = true) })
    public GainTask view(Long uid, Long aid) {
        // >>> >>>
        Float ratio = null;
        Long stock = null;
        NewsDetail news = null;
        Result<NewsDetail> newsResult = null;
        // 判断文章是否不存在、已关闭挖矿
        if (null == (newsResult = this.showApi.major(aid)) //
                || null == (news = newsResult.getData()))
            return new GainTask(GainStatus.FAILED, "资讯不存在");
        else if (YesNo.NO == news.getReadSwitch()) // 阅读方式挖矿是否关闭
            return new GainTask(GainStatus.OFF, "挖矿已关闭");
        // 判断当前剩余服豆库存是否已挖空和奖励计算系数是否有效
        else if (null == (stock = news.getBeansNum()) || 0L >= stock //
                || null == (ratio = news.getRechargeRatio()) || 0.00F >= ratio)
            return new GainTask(GainStatus.FAILED, "服豆已被挖空");

        Long viewTime = null;
        GainConfDetail conf = null;
        // 判断挖矿时限无效, 即阅读挖矿已被关闭/暂停
        if (null == (conf = this.confApi.gainConfInfo()))
            return new GainTask(GainStatus.OFF, "挖矿无配置");
        // 判断挖矿时限无效, 即阅读挖矿已被关闭/暂停
        else if (null == (viewTime = conf.getViewTime()) || 0L >= viewTime)
            return new GainTask(GainStatus.OFF, "挖矿未开启");

        Browse browse = null;
        // 查找浏览记录，判断是否重复挖矿
        if (null == (browse = this.browseService.get(uid, aid))) //
            return new GainTask(GainStatus.FAILED, "阅读无效");

        if (YesNo.YES == browse.getView())
            return new GainTask(GainStatus.REPEAT, "已阅读挖矿");
        // <<< <<<

        // 设定挖矿开始时间并返回当前可以挖矿
        long stime = System.currentTimeMillis();
        GainTask task // 挖矿计时已开启
                = new GainTask(uid, aid, Way.VIEW, stime, viewTime, GainStatus.ON);
        if (log.isDebugEnabled())
            log.debug("<<< {}.view() view gain start, task:{}", this.getClass().getName(), JsonUtil.toJson(task));
        return task;
    }

    @Override
    @Transactional
    @CacheEvict(key = "'view.'+#uid", beforeInvocation = false) // 不管是否挖矿成功，都需对计时置零(清空挖矿任务缓存)
    public GainTask calcView(Long uid, Long aid) {
        // 限制同时点赞多篇文章或多次同一文章
        if (!this.browseService.lock(uid.toString(), aid + ".view", 1200L))
            return new GainTask(GainStatus.FAILED, "您操作太频繁");

        Way way = Way.VIEW;
        GainTask task = this.calcAward(uid, aid, way, null);
        Long award = (null == task) ? null : task.getAward();
        if (log.isInfoEnabled())
            log.info("<<< {}.calcView() calc view award, task:{}", this.getClass().getName(), JsonUtil.toJson(task));

        // 有效挖矿数量方可就行累加入账
        if (null != award && 0L < award) {
            Browse browse = null;
            // 更新浏览记录，记录阅读标记和阅读挖矿数量
            browse = this.browseService.modify(uid, aid, way, award);

            // 累加阅读挖矿奖励，同时执行派发阅读的挖矿奖励
            this.dispatch(uid, aid, way /* way */, award, browse.getId() /* target: */);
        }

        if (log.isDebugEnabled())
            log.debug("<<< {}.calcView() calc view award, task:{}", this.getClass().getName(), JsonUtil.toJson(task));
        return task;
    }

    @Override
    public GainTask calcAward(Long uid, Long aid, Way way, NewsDetail news) {
        GainConfDetail conf = null;
        // 获取全局挖矿配置信息，便于后续挖矿奖励的计算
        if (null == (conf = this.confApi.gainConfInfo()))
            return new GainTask(GainStatus.OFF, "挖矿无配置");

        GainTask task = null;
        // 是否在计算阅读挖矿的奖励
        if (way == Way.VIEW) {
            Long viewTime = null;
            // 判断挖矿时限无效, 即阅读挖矿已被关闭/暂停
            if (null == (viewTime = conf.getViewTime()) || 0L >= viewTime)
                return new GainTask(GainStatus.OFF, "挖矿未开启");

            Long _stime = null, _aid = null;
            // 判断阅读挖矿有没有开始计时标记
            String taskKey = way.name().toLowerCase() + "." + uid; // 挖矿任务缓存KEY
            if (null == (task = this.cache.get(taskKey, GainTask.class)) //
                    || GainStatus.ON != task.getStatus() || null == (_stime = task.getStime()) // 无效开始计时时间戳和状态
                    || null == (_aid = task.getAid()) || !aid.equals(_aid)) // 提交所完成阅读的文章ID与计时器绑定的ID不匹配
                return null == task ? //
                        new GainTask(GainStatus.FAILED, "挖矿无效(违规)") : task.setResult(GainStatus.FAILED, "挖矿无效(违规)");
            // 计算当前阅读挖矿时长是否达标
            else if (task.getViewTime() * 1000L > System.currentTimeMillis() - _stime)
                return task.setResult(GainStatus.REDO, "任务不达标, 请重新挖矿");
        }

        // 非阅读类挖矿需要此时初始化挖矿任务
        task = (null != task) ? task : //
                new GainTask(uid, aid, way, null, null, GainStatus.ON);

        Float ratio = null;
        Long stock = null;
        // 判断文章是否不存在、已关闭挖矿
        Result<NewsDetail> newsResult = null;
        if (null == news && (null == (newsResult = this.showApi.major(aid)) //
                || null == (news = newsResult.getData())))
            return task.setResult(GainStatus.FAILED, "资讯不存在");
        else if ((way == Way.VIEW && YesNo.NO == news.getReadSwitch()) // 阅读方式挖矿是否关闭
                || (way == Way.SHARE && YesNo.NO == news.getShareSwitch()) // 分享方式挖矿是否关闭
                || (way == Way.ENJOY && YesNo.NO == news.getPraiseSwitch()))// 点赞方式挖矿是否关闭
            return task.setResult(GainStatus.OFF, "挖矿已关闭");
        // 判断当前剩余服豆库存是否已挖空和奖励计算系数是否有效
        else if (null == (stock = news.getBeansNum()) || 0L >= stock //
                || null == (ratio = news.getRechargeRatio()) || 0.00F >= ratio)
            return task.setResult(GainStatus.FAILED, "服豆已被挖空");

        // 计算当前时间挖矿数量: A*(1-B)*C / E
        float fee = conf.getHandFee(); // B手续费比率,
        long rate = this.rateApi.now(), cardinal = 0L; // E服豆汇率、A挖矿基数

        if (way == Way.VIEW) // A阅读挖矿随机基数
            cardinal = RandomUtils.nextLong(conf.getViewCardinals()[0], conf.getViewCardinals()[1]);
        else if (way == Way.ENJOY) // A点赞挖矿随机基数
            cardinal = RandomUtils.nextLong(conf.getEnjoyCardinals()[0], conf.getEnjoyCardinals()[1]);
        else if (way == Way.SHARE) // A分享挖矿随机基数
            cardinal = RandomUtils.nextLong(conf.getShareCardinals()[0], conf.getShareCardinals()[1]);

        // 计算当前时间挖矿数量: A*(1-B)*C / E
        long award = new BigDecimal(cardinal) // A
                .multiply(new BigDecimal(1).subtract(new BigDecimal(String.valueOf(fee)))) // (1-B)
                .multiply(new BigDecimal(String.valueOf(ratio))) // C
                .divide(new BigDecimal(rate).divide(new BigDecimal(CalcUtil.SCALE)), 0, RoundingMode.HALF_UP) // E
                .setScale(0, RoundingMode.HALF_UP).longValue();

        // TODO 需要考虑VIP用户对应的挖矿翻倍

        // 如果当前挖矿数量已经操作当前剩余库存，则直接托底全部分配给当前使用
        award = stock >= award ? award : stock;

        boolean deductRes = true;
        if (!deductRes) // TODO 同步扣减缓存级库存
            return task.setResult(GainStatus.FAILED, "服豆已被挖空");

        return task.setResult(GainStatus.SUCCESS, award, null); // 绑定当前挖矿可分配到的奖励数量
    }

    @Override
    @Transactional
    public void dispatch(long uid, long aid, Way way, long award, long target) {
        if (0L >= award)
            return; // 如果派发奖励为0，则不需要账户调账和核销资讯的服豆库存

        // 触动对应资讯扣减当前需要派发数量的奖励
        Result<?> subStock = this.articleApi.reduceStock(aid, award);
        if (!subStock.ok())
            throw new BizException(Code.BIZ_ERROR, "服豆已被挖空");

        // 需要对当前分析挖矿所得奖励进行内部调账入账
        Result<?> result = null;
        WalletAdjust adjust = new WalletAdjust(uid, way, award, Currency.SDC, target, Channel.APP, null /* outFlows */,
                null /* remark */);
        if (null == (result = this.walletApi.adjust(adjust)) || !result.ok())
            throw new BizException(Code.BIZ_ERROR, "挖矿奖励派发失败");
    }

}
