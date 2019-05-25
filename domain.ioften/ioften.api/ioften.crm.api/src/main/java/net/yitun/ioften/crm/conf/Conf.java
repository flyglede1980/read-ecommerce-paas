package net.yitun.ioften.crm.conf;

/**
 * <pre>
 * <b>CRM 配置.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月8日 下午2:18:37 LH
 *         new file.
 * </pre>
 */
public class Conf {

    /** API 路由 */
    public static final String ROUTE = "/crm";
    // =================================================================

    /** MQ交换器 */
    public static final String MQ_EXCHANGE = "crm-exchange";

    /** MQ: 用户消息通知, 消息模型: MesgStore */
    public static final String MQ_ROUTEKEY_USER_MESGS = "crm.user.mesg";

    /** MQ: 用户注册成功, 消息格式: [uid, phone] */
    public static final String MQ_ROUTEKEY_USER_SIGNUP = "crm.user.signup";
    // =================================================================

}
