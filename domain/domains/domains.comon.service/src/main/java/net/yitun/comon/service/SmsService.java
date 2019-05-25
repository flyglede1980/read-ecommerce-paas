package net.yitun.comon.service;

import java.util.List;
import java.util.Set;

import net.yitun.basic.model.Result;
import net.yitun.comon.model.sms.SmsSended;

/**
 * <pre>
 * <b>短信服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月24日 下午11:41:11 LH
 *         new file.
 * </pre>
 */
public interface SmsService {

    /**
     * 批量发送短信
     * 
     * @param phones  多个手机号
     * @param content 短信内容
     * @return Result<List<SmsSended>> 发送结果
     */
    Result<List<SmsSended>> send(Set<String> phones, String content);

}
