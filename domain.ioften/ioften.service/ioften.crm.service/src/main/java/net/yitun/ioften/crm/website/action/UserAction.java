package net.yitun.ioften.crm.website.action;

import java.util.Date;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.Code;
import net.yitun.basic.Util;
import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.basic.utils.PwdUtil;
import net.yitun.basic.utils.PwdUtil.LEVEL;
import net.yitun.comon.CodeApi;
import net.yitun.comon.dicts.Type;
import net.yitun.ioften.crm.UserApi;
import net.yitun.ioften.crm.dicts.ThirdSwitch;
import net.yitun.ioften.crm.entity.User;
import net.yitun.ioften.crm.model.user.PwdModify;
import net.yitun.ioften.crm.model.user.PwdPayModify;
import net.yitun.ioften.crm.model.user.UserDetail;
import net.yitun.ioften.crm.model.user.UserModify;
import net.yitun.ioften.crm.model.user.UserPhoneModify;
import net.yitun.ioften.crm.model.user.UserQuery;
import net.yitun.ioften.crm.model.user.UserSafetyDetail;
import net.yitun.ioften.crm.model.user.UserStatusModify;
import net.yitun.ioften.crm.service.UserService;

@Api(tags = "用户 管理接口")
@RestController("crm.UserApi")
@SuppressWarnings("unchecked")
public class UserAction implements UserApi {

    @Autowired
    protected CodeApi codeApi;

    @Autowired
    protected UserService service;

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "个人信息")
    public Result<UserDetail> my() {
        Long uid = SecurityUtil.getId();
        return this.detail(uid);
    }

    @Override
    @ApiOperation(value = "昵称信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true)
    public Result<UserDetail> nick(@PathVariable("id") Long id) {
        User user = null;
        if (null == id || IdUtil.MIN > id //
                || null == (user = this.service.get(id)))
            return Result.UNEXIST;

        return new Result<>(new UserDetail(user.getId(), user.getNick(), user.getLevel(), user.getInvite()));
    }

    @Override
    @ApiOperation(value = "基本信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true)
    public Result<UserDetail> info(@PathVariable("id") Long id) {
        User user = null;
        if (null == id || IdUtil.MIN > id //
                || null == (user = this.service.get(id)))
            return Result.UNEXIST;

        return new Result<>(new UserDetail(user.getId(), user.getNick(), user.getLevel(), user.getIntro(), user.getInvite()));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "安全信息")
    public Result<UserSafetyDetail> safety() {
        Long uid = SecurityUtil.getId();
        User user = this.service.get(uid);

        UserSafetyDetail safety = new UserSafetyDetail(user.getId(), user.getPhone(), StringUtils.isNotBlank(user.getPasswd()),
                StringUtils.isNotBlank(user.getPayPasswd()), ThirdSwitch.UB /* qqChkinSwitch */,
                ThirdSwitch.UB /* wxinChkinSwitch */, ThirdSwitch.UB /* weiboChkinSwitch */,
                ThirdSwitch.UB /* yitunChkinSwitch */);

        return new Result<UserSafetyDetail>(safety);
    }

    @Override
    @ApiOperation(value = "详细信息")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    public Result<UserDetail> detail(@PathVariable("id") Long id) {
        if (null == id || IdUtil.MIN > id)
            return new Result<>(Code.BAD_REQ, "ID无效");

        User user = null;
        if (null == (user = this.service.get(id)))
            return Result.UNEXIST;

        return new Result<>(new UserDetail(user.getId(), user.getNick(), user.getName(), user.getPhone(), user.getSex(),
                user.getType(), user.getLevel(), user.getCity(), user.getBirthday(), user.getIdcard(), user.getOperator(),
                user.getIntro(), user.getInvite(), user.getDevice(), user.getCategoryId(), user.getCategoryName(),
                user.getSubCategoryId(), user.getSubCategoryName(), user.getRemark(), user.getStatus(), user.getCtime(),
                user.getMtime()));
    }

    @Override
    @ApiOperation(value = "用户信息")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParam(name = "phone", value = "手机号", required = true, allowMultiple = true)
    public Result<UserDetail> getByPhone(@PathVariable("phone") String phone) {
        if (null == phone //
                || !Util.matche(phone, Util.REGEX_PHONE))
            return Result.UNEXIST;

        User user = null;
        if (null == (user = this.service.getByPhone(phone)))
            return Result.UNEXIST;

        return new Result<>(new UserDetail(user.getId(), user.getNick(), user.getName(), user.getPhone(), user.getSex(),
                user.getType(), user.getLevel(), user.getCity(), user.getBirthday(), user.getIdcard(), user.getOperator(),
                user.getIntro(), user.getInvite(), user.getDevice(), user.getCategoryId(), user.getCategoryName(),
                user.getSubCategoryId(), user.getSubCategoryName(), user.getRemark(), user.getStatus(), user.getCtime(),
                user.getMtime()));
    }

    @Override
    @ApiOperation(value = "分页查询")
    @PreAuthorize("hasRole('ADMIN')")
    public PageResult<UserDetail> query(@Validated UserQuery query) {
        Page<User> page = this.service.query(query);
        return new PageResult<>(page.getTotal(), page.getPages(),
                page.stream().map(user -> new UserDetail(user.getId(), user.getNick(), user.getName(), user.getPhone(),
                        user.getSex(), user.getType(), user.getLevel(), user.getCity(), user.getBirthday(), user.getIdcard(),
                        user.getOperator(), user.getIntro(), user.getInvite(), user.getDevice(), user.getCategoryId(),
                        user.getCategoryName(), user.getSubCategoryId(), user.getSubCategoryName(), user.getRemark(),
                        user.getStatus(), user.getCtime(), user.getMtime())).collect(Collectors.toList()));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "修改密码")
    public Result<?> modifyPwd(@Validated @RequestBody PwdModify model) {
        // 密码评级是否太简单
        String newPasswd = model.getNewPasswd();
        if (LEVEL.EASY == PwdUtil.level(newPasswd))
            return new Result<>(Code.BIZ_ERROR, "新密码太简单");

        // 新旧密码不相同方可修改
        if (StringUtils.equals(model.getPasswd(), newPasswd))
            return new Result<>(Code.BAD_REQ, "新旧密码不能相同");

        // 调用内部服务处理修改密码
        return this.service.modifyPwd(model);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "修改新手机号", notes = "需要提供新手机号的短信验证码")
    public Result<?> modifyPhone(@Validated @RequestBody UserPhoneModify model) {
        Result<?> result = null;
        String phone = model.getPhone(), captcha = model.getCaptcha();
        if (!(result = this.codeApi.match(Type.SMS, phone, captcha)).ok())
            return new Result<>(Code.BIZ_ERROR, "短信验证码错误", result.getData());

        return this.service.modifyPhone(phone);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "修改支付密码", notes = "需要提供登录对应账号的手机短信验证码")
    public Result<?> modifyPayPwd(@Validated @RequestBody PwdPayModify model) {
        Result<?> result = null;
        Long uid = SecurityUtil.getId();
        User user = this.service.get(uid);
        if (null == (result = this.codeApi.match(Type.SMS, //
                String.valueOf(user.getPhone()), model.getCaptcha())) //
                || !result.ok())
            return new Result<>(Code.BIZ_ERROR, "短信验证码错误", result.getData());

        String payPasswd = model.getPayPasswd();
        return this.service.modifyPayPwd(payPasswd);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "修改资料")
    public Result<?> modifyDatum(@Validated @RequestBody UserModify model) {
        // 判断生日是否无效
        if (null != model.getBirthday() //
                && new Date(System.currentTimeMillis()).before(model.getBirthday()))
            return new Result<>(Code.BAD_REQ, "出生日期无效");

        // 调用内部服务处理修改资料
        return this.service.modifyDatum(model);
    }

    @Override
    @ApiOperation(value = "启禁用户")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<?> modifyStatus(@Validated @RequestBody UserStatusModify model) {
        // 调用内部服务处理启用禁用用户
        return this.service.modifyStatus(model);
    }
}
