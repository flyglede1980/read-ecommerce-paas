package net.yitun.comon;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.yitun.basic.model.Result;
import net.yitun.comon.conf.Conf;
import net.yitun.comon.dicts.Type;

/**
 * <pre>
 * <b>通用 验证码.</b>
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
public interface CodeApi {

    /**
     * 图形验证码
     * 
     * @param mark 标记
     */
    @GetMapping(value = Conf.ROUTE //
            + "/code/{mark}", consumes = MediaType.ALL_VALUE, produces = MediaType.ALL_VALUE)
    void img(@PathVariable("mark") String mark);

    /**
     * 短信验证码
     * 
     * @param phone 手机号
     * @param code  图形验证码, 前两次不对此进行校验, 长度为6个字符
     * @return Result<String> 发送结果
     */
    @PostMapping(value = Conf.ROUTE //
            + "/code", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<String> sms(@RequestParam(value = "phone", required = true) String phone,
            @RequestParam(value = "code", required = false) String code);

    /**
     * 验证码匹配
     * 
     * @return type 类型
     * @return key 缓存KEY
     * @param code 待验证的验证码
     * @return Result<?> 是否匹配
     */
//    @PostMapping(value = Conf.ROUTE //
//            + "/code/match", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> match(@RequestParam("type") Type type, @RequestParam("key") String key, @RequestParam("code") String code);

}
