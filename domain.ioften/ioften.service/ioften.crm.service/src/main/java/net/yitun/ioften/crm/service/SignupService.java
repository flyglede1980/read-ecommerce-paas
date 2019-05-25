package net.yitun.ioften.crm.service;

import javax.servlet.http.HttpServletResponse;

import net.yitun.basic.model.Result;
import net.yitun.ioften.crm.model.user.join.Signup;
import net.yitun.ioften.crm.model.user.login.Login;
import net.yitun.ioften.crm.model.user.login.LosePwd;
import net.yitun.ioften.crm.model.user.login.SmsLogin;

/**
 * <pre>
 * <b>用户 注册登录服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月10日 下午5:29:46 LH
 *         new file.
 * </pre>
 */
public interface SignupService {

    /**
     * 密码登录
     * 
     * @param model 登录模型
     * @return Result<?> 登录结果
     */
    Result<?> login(Login model, HttpServletResponse response);

    /**
     * 短信登录
     * 
     * @param model 登录模型
     * @return Result<?> 登录结果
     */
    Result<?> loginBySms(SmsLogin model, HttpServletResponse response);

    /**
     * 忘记密码
     *
     * @param model 修改模型
     * @return Result<?> 修改结果
     */
    Result<?> losePwd(LosePwd model);

    // ==============================================================================
    /**
     * 手机注册
     * 
     * @param model    注册模型
     * @param response Http响应
     * @return Result<?> 注册结果
     */
    Result<?> signup(Signup model, HttpServletResponse response);

}
