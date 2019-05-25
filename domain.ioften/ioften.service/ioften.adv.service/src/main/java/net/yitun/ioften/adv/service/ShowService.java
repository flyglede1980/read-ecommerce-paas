package net.yitun.ioften.adv.service;

import java.util.Collection;

import net.yitun.ioften.adv.model.adv.AdvDetail;

/**
 * <pre>
 * <b>广告 装配服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月5日 下午9:19:03 LH
 *         new file.
 * </pre>
 */
public interface ShowService {

    /**
     * APP 启动页广告
     * 
     * @return AdvDetail
     */
    AdvDetail flash();

    /**
     * 推荐页 banner广告
     * 
     * @return Collection<AdvDetail>
     */
    Collection<AdvDetail> banner();

}
