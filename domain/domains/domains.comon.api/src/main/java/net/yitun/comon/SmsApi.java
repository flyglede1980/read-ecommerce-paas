package net.yitun.comon;

import java.util.Collection;
import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import net.yitun.basic.model.Result;
import net.yitun.comon.model.sms.SmsSended;

/**
 * <pre>
 * <b>通用 短信发送.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月8日 下午7:49:51 LH
 *         new file.
 * </pre>
 */
public interface SmsApi {

    /**
     * 短信发送
     *
     * @param content 短信内容, 长度为8~256个字符
     * @param phones  手机号, 限制一次1~1024个号码
     * @return Result<List<SmsSended>> 发送结果
     */
//    @PostMapping(value = Conf.ROUTE //
//            + "/sms", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<List<SmsSended>> send(@RequestParam("content") String content, @RequestParam("phone") String... phones);

    /**
     * 短信发送
     *
     * @param content 短信内容, 长度为8~256个字符
     * @param phones  手机号, 限制一次1~1024个号码
     * @return Result<List<SmsSended>> 发送结果
     */
//    @PostMapping(value = Conf.ROUTE //
//            + "/smss", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<List<SmsSended>> send(@RequestParam("content") String content, @RequestParam("phone") Collection<String> phones);

}
