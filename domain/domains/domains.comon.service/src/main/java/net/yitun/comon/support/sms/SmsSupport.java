package net.yitun.comon.support.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <b>Sms投送支撑.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月9日 下午8:29:42 LH
 *         new file.
 * </pre>
 */
@Slf4j
@Component("sms.SmsSupport")
public class SmsSupport implements SmsGateway {

    @Autowired
    @Qualifier("sms.YunpianGateway") // 短信投送网关
    protected SmsGateway gateway;

    /**
     * 投送短信
     * 
     * @param phone   手机号
     * @param content 短信内容
     * @return String 失败错误消息
     */
    public String send(String phone, String content) {
        if (log.isDebugEnabled())
            log.debug(">>> {}.send() by {} {}: {}" //
                    , this.getClass().getName(), this.gateway.type(), phone, content);

        try {
            return this.gateway.send(phone, content);

        } catch (Exception e) {
            log.error("<<< {}.send() by {} {}" //
                    , this.getClass().getName(), this.gateway.type(), e.toString());
            return e.getMessage();
        }
    }

}