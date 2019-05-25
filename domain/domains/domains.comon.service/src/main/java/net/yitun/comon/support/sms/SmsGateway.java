package net.yitun.comon.support.sms;

/**
 * <pre>
 * <b>短信网关.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月9日 下午8:25:33 LH
 *         new file.
 * </pre>
 */
public interface SmsGateway {

    /**
     * 类型
     * 
     * @return String
     */
    default String type() {
        return null;
    };

    /**
     * 投递短信
     * 
     * @param phone   手机号
     * @param content 短信内容
     * @return String 失败错误消息
     */
    String send(String phone, String content);

}