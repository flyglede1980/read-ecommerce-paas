package net.yitun.ioften.cms.service;

import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.dicts.ShareWay;
import net.yitun.ioften.cms.entity.Share;
import net.yitun.ioften.cms.model.share.ShareDetail;

/**
 * <pre>
 * <b>资讯 资讯分享服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月19日 上午10:14:51
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月19日 上午10:14:51 LH
 *         new file.
 * </pre>
 */
public interface ShareService {

    /**
     * 详细信息
     * 
     * @param id 分享ID
     * @return Share 分享信息
     */
    Share get(Long id);

    /**
     * 生成分享
     * 
     * @param uid 用户ID
     * @param aid 文章ID
     * @param way 分享方式
     * @return ShareDetail 分享信息
     */
    ShareDetail gen(Long uid, Long aid, ShareWay way);

    /**
     * 展示信息
     * 
     * @param id 分享ID
     * @return ShareDetail 分享信息
     */
    ShareDetail show(Long id);

    /**
     * 计算分享挖矿以及累加展示次数
     * 
     * @param id 分享ID
     * @return Result<Share> 处理结果
     */
    Result<Share> calcGain(Long id);

}
