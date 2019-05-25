package net.yitun.ioften.pay.support.task;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.model.Result;
import net.yitun.basic.utils.DateUtil;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.ioften.pay.service.RankService;

/**
 * <pre>
 * <b>支付 服豆排名定时任务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月14日 下午1:40:27
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月14日 下午1:40:27 LH
 *         new file.
 * </pre>
 */
@Slf4j
@Component
public class RankTask {

    @Autowired
    protected RankService service;

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());
    }

    @Scheduled(cron = "1 1 3 * * ?")
    // @Scheduled(cron = "0/10 * * * * ?")
    public void run() {
        if (log.isInfoEnabled())
            log.info(">>> {}.run() rank etl start:{}", this.getClass().getName(), DateUtil.format(DateUtil.DATE_TIME_STAMP));

        Result<Integer> result = null;
        try {
            result = this.service.etlRank();
        } catch (Exception e) {
            log.error("<<< {}.run() rank etl failed, error:{}", this.getClass().getName(), e.toString());
            return;
        }

        if (log.isInfoEnabled())
            log.info("<<< {}.run() rank etl success, result:{}", this.getClass().getName(), JsonUtil.toJson(result));
    }
}
