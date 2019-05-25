package net.yitun.comon;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.yitun.basic.model.Result;
import net.yitun.basic.utils.PwdUtil.LEVEL;
import net.yitun.comon.conf.Conf;

/**
 * <pre>
 * <b>通用 辅助检查.</b>
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
public interface CheckApi {

    /**
     * 密码强度
     * 
     * @param passwd 密码
     * @return Result<LEVEL> 简单: EASY, 中等: MIDIUM, 强壮: STRONG, 非常强壮: VERY_STRONG, 超级强壮: EXTREMELY_STRONG
     */
    @PostMapping(value = Conf.ROUTE //
            + "/check/pwd", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<LEVEL> checkPwd(@RequestParam("passwd") String passwd);

    /**
     * 是否已登录
     *
     * @return Result<Boolean> 是否登录
     */
    @GetMapping(value = Conf.ROUTE //
            + "/check/islogin", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<Boolean> checkIsLogin();

}
