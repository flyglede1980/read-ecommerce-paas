package net.yitun.ioften.crm;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.yitun.basic.model.Result;
import net.yitun.ioften.crm.model.user.join.Signup;
import net.yitun.ioften.crm.model.user.join.ThirdSignup;
import net.yitun.ioften.crm.model.user.login.Login;
import net.yitun.ioften.crm.model.user.login.LosePwd;
import net.yitun.ioften.crm.model.user.login.SmsLogin;

/**
 * <pre>
 * <b>用户 注册登录.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月10日 下午4:13:01 LH
 *         new file.
 * </pre>
 */
public interface SignupApi {

    /**
     * 密码登录
     *
     * @param model 登录模型
     * @return Result<?> 登录结果
     */
    @PostMapping(value = "/login" //
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> login(@RequestBody Login model);

    /**
     * 短信登录
     *
     * @param model 登录模型
     * @return Result<?> 登录结果
     */
    @PostMapping(value = "/login/bysms" //
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> loginBySms(@RequestBody SmsLogin model);

    /**
     * 忘记密码
     *
     * @param model 修改模型
     * @return Result<?> 修改结果
     */
    @PutMapping(value = "/login/losepwd" //
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> losePwd(@RequestBody LosePwd model);

    // ==============================================================================
    /**
     * 手机注册
     *
     * @param model 注册模型
     * @return Result<?> 注册结果
     */
    @PostMapping(value = "/signup" //
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> signup(@RequestBody Signup model);

    /**
     * 第三方注册
     *
     * @param model 注册模型
     * @return Result<?> 注册结果
     */
    @PostMapping(value = "/signup/bythird" //
            , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> thirdSignup(@RequestBody ThirdSignup model);

}
