package net.yitun.ioften.cms.conf;

/**
 * <pre>
 * <b>CMS 配置.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月8日 下午2:19:00 LH
 *         new file.
 * </pre>
 */
public class Conf {

    /** API 路由 */
    public static final String ROUTE = "/cms";
    // =================================================================

    /** 交换器 */
    public static final String MQ_EXCHANGE = "cms-exchange";

    /** 分享文章被展示消息路由KEY, 消息=分享ID */
    public static final String MQ_ROUTEKEY_SHARE_SHOW = "cms.share.show";
    // =================================================================

}
