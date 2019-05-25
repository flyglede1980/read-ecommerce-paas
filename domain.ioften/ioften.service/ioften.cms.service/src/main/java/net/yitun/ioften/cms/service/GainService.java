package net.yitun.ioften.cms.service;

import net.yitun.ioften.cms.model.show.NewsDetail;
import net.yitun.ioften.cms.service.dto.GainTask;
import net.yitun.ioften.pay.dicts.Way;

/**
 * <pre>
 * <b>资讯 挖矿服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月12日 上午11:49:14
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月12日 上午11:49:14 LH
 *         new file.
 * </pre>
 */
public interface GainService {

    /**
     * 阅读挖矿开始
     * 
     * @param uid 用户ID
     * @param aid 文章ID
     * @return GainTask 阅读挖矿状态
     */
    GainTask view(Long uid, Long aid);

    /**
     * 计算阅读挖矿
     * 
     * @param uid 用户ID
     * @param aid 文章ID
     * @return GainTask 阅读挖矿结果
     */
    GainTask calcView(Long uid, Long aid);

    /**
     * 计算挖矿奖励
     * 
     * @param uid 用户ID
     * @param aid 资讯ID
     * @param way 挖矿方式
     * @return GainTask 挖矿任务
     */
    GainTask calcAward(Long uid, Long aid, Way way, NewsDetail news);

    /**
     * 派发挖矿奖励
     * 
     * @param uid    用户ID
     * @param aid    资讯ID
     * @param way    挖矿方式
     * @param award  奖励
     * @param target 奖励来源
     */
    void dispatch(long uid, long aid, Way way, long award, long target);
}
