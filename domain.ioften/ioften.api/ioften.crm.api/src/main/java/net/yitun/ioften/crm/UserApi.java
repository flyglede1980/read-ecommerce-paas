package net.yitun.ioften.crm;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.ioften.crm.conf.Conf;
import net.yitun.ioften.crm.model.user.PwdModify;
import net.yitun.ioften.crm.model.user.PwdPayModify;
import net.yitun.ioften.crm.model.user.UserDetail;
import net.yitun.ioften.crm.model.user.UserModify;
import net.yitun.ioften.crm.model.user.UserPhoneModify;
import net.yitun.ioften.crm.model.user.UserQuery;
import net.yitun.ioften.crm.model.user.UserSafetyDetail;
import net.yitun.ioften.crm.model.user.UserStatusModify;

/**
 * <pre>
 * <b>用户 用户接口.</b>
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
public interface UserApi {

    /**
     * 我的信息
     *
     * @return Result<UserDetail> 我的信息
     */
    @GetMapping(value = Conf.ROUTE //
            + "/my", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<UserDetail> my();

    /**
     * 昵称信息<br/>
     * 含Uid、昵称、等级、邀请码
     * 
     * @param id ID
     * @return Result<UserDetail> 基本信息
     */
    Result<UserDetail> nick(@PathVariable("id") Long id);

    /**
     * 基本信息<br/>
     * 含Uid、昵称、等级、邀请码、简介
     * 
     * @param id ID
     * @return Result<UserDetail> 基本信息
     */
    Result<UserDetail> info(@PathVariable("id") Long id);

    /**
     * 安全信息
     *
     * @return Result<UserSafetyDetail> 安全信息
     */
    @GetMapping(value = Conf.ROUTE //
            + "/my/safety", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<UserSafetyDetail> safety();

    /**
     * 详细信息
     *
     * @param id ID
     * @return Result<UserDetail> 详细信息
     */
    @GetMapping(value = Conf.ROUTE //
            + "/user/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<UserDetail> detail(@PathVariable("id") Long id);

    /**
     * 用户信息
     *
     * @param phone 手机号
     * @return Result<UserDetail> 个人信息
     */
    @GetMapping(value = Conf.ROUTE //
            + "/user/byphone/{phone}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<UserDetail> getByPhone(@PathVariable("phone") String phone);

    /**
     * 分页查询
     * 
     * @param query 查询参数
     * @return PageResult<UserDetail> 分页结果
     */
    @GetMapping(value = Conf.ROUTE //
            + "/users", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<UserDetail> query(UserQuery query);

    /**
     * 修改密码
     *
     * @param model 新旧密码
     * @return Result<?> 修改结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/user/pwd", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> modifyPwd(@RequestBody PwdModify model);

    /**
     * 修改新手机号
     *
     * @param model 新手机号
     * @return Result<?> 修改结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/user/phone", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> modifyPhone(@RequestBody UserPhoneModify model);

    /**
     * 修改支付密码
     *
     * @param model 新支付密码
     * @return Result<?> 修改结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/user/paypwd", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> modifyPayPwd(@RequestBody PwdPayModify model);

    /**
     * 修改资料
     *
     * @param model 修改资料
     * @return Result<?> 修改结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/user/datum", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> modifyDatum(@RequestBody UserModify model);

    /**
     * 启禁用户
     * 
     * @param model 状态设置
     * @return Result<?> 启禁结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/user/status", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> modifyStatus(@Validated @RequestBody UserStatusModify model);

}
