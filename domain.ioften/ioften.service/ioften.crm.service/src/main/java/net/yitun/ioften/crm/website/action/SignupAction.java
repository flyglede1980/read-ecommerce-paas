package net.yitun.ioften.crm.website.action;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.Code;
import net.yitun.basic.model.Result;
import net.yitun.basic.utils.PwdUtil;
import net.yitun.basic.utils.PwdUtil.LEVEL;
import net.yitun.comon.CodeApi;
import net.yitun.comon.dicts.Type;
import net.yitun.ioften.crm.SignupApi;
import net.yitun.ioften.crm.model.user.join.Signup;
import net.yitun.ioften.crm.model.user.join.ThirdSignup;
import net.yitun.ioften.crm.model.user.login.Login;
import net.yitun.ioften.crm.model.user.login.LosePwd;
import net.yitun.ioften.crm.model.user.login.SmsLogin;
import net.yitun.ioften.crm.service.SignupService;

@Api(tags = "用户 注册登录")
@RestController("crm.SignupApi")
public class SignupAction implements SignupApi {

    @Autowired
    protected CodeApi codeApi;

    @Autowired
    protected SignupService service;

    @Autowired
    protected HttpServletResponse response;

    @Override
    @ApiOperation(value = "密码登录", notes = "通过提交手机号、密码进行登录认证，登录成功返回用户信息以及在Http响应头中返回令牌, !!!特别: TIPS_LOSE_PWD: 代表提示用户找回密码, USER_LOCKED:300: 代表用户登录被锁300秒")
    public Result<?> login(@Validated @RequestBody Login model) {
        // 调用内部密码登录认证业务服务
        return this.service.login(model, this.response);
    }

    @Override
    @ApiOperation(value = "短信登录", notes = "通过提交手机号和短信验证码方式登录认证，登录成功返回用户信息以及在Http响应头中返回令牌, !!!特别: NEED_IMG_CODE: 代表发短信需要图形验证码; SMS_LOCKED:600, 代表短信被锁600s")
    public Result<?> loginBySms(@Validated @RequestBody SmsLogin model) {
        Result<?> result = null;
        // 判断登录短信验证码是否不匹配
        String phone = model.getPhone(), captcha = model.getCaptcha();
        if (!(result = this.codeApi.match(Type.SMS, phone, captcha)).ok())
            return new Result<>(Code.BAD_REQ, "短信验证码错误", result.getData());

        // 调用内部短信登录认证业务服务
        return this.service.loginBySms(model, this.response);
    }

    @Override
    @ApiOperation(value = "忘记密码", notes = "通过提交手机号、新密码和短信验证码方式修改密码")
    public Result<?> losePwd(@Validated @RequestBody LosePwd model) {
        // 密码评级是否太简单
        String passwd = model.getPasswd();
        if (LEVEL.EASY == PwdUtil.level(passwd))
            return new Result<>(Code.BIZ_ERROR, "密码太简单");

        Result<?> result = null;
        // 判断登录短信验证码是否不匹配
        String phone = model.getPhone(), captcha = model.getCaptcha();
        if (!(result = this.codeApi.match(Type.SMS, phone, captcha)).ok())
            return new Result<>(Code.BIZ_ERROR, "短信验证码错误", result.getData());

        // 调用内部短信修改业务服务
        return this.service.losePwd(model);
    }

    // ==============================================================================
    @Override
    @ApiOperation(value = "手机注册", notes = "需要先获取短信验证码，然后提交注册时密码强度必须高于(含)中级")
    public Result<?> signup(@Validated @RequestBody Signup model) {
        // 密码评级是否太简单
        String passwd = model.getPasswd();
        if (LEVEL.EASY == PwdUtil.level(passwd))
            return new Result<>(Code.BIZ_ERROR, "密码太简单");

        Result<?> result = null;
        // 判断手机验证码是否与系统不匹配
        String phone = model.getPhone(), captcha = model.getCaptcha();
        if (!(result = this.codeApi.match(Type.SMS, phone, captcha)).ok())
            return new Result<>(Code.BIZ_ERROR, "短信验证码错误", result.getData());

        // 调用内部注册业务服务
        return this.service.signup(model, this.response);
    }

    @Override
    @ApiOperation(value = "第三方注册", notes = "通过提交第三方的授权信息，进行自动注册")
    public Result<?> thirdSignup(@Validated @RequestBody ThirdSignup model) {
        // TODO 第三方登录认证
        return null;
    }

}
