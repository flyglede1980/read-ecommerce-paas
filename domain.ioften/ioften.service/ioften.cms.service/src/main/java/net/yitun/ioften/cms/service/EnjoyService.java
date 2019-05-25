package net.yitun.ioften.cms.service;

import net.yitun.ioften.cms.model.enjoy.EnjoyDetail;

/**
 * <pre>
 * <b>资讯 点赞服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月23日 下午3:47:06
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月23日 下午3:47:06 LH
 *         new file.
 * </pre>
 */
public interface EnjoyService {

    /**
     * 资讯点赞
     * 
     * @param uid 用户ID
     * @param aid 资讯ID
     * @return EnjoyDetail 点赞结果
     */
    EnjoyDetail enjoy(Long uid, Long aid);

}
