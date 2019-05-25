package net.yitun.ioften.crm.service;

import com.github.pagehelper.Page;

import net.yitun.basic.model.Result;
import net.yitun.ioften.crm.entity.Identity;
import net.yitun.ioften.crm.entity.User;
import net.yitun.ioften.crm.model.user.PwdModify;
import net.yitun.ioften.crm.model.user.UserModify;
import net.yitun.ioften.crm.model.user.UserQuery;
import net.yitun.ioften.crm.model.user.UserStatusModify;

/**
 * <pre>
 * <b>用户 用户管理服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月12日 下午8:02:37 LH
 *         new file.
 * </pre>
 */
public interface UserService {

    /**
     * 详情信息
     * 
     * @param id ID
     * @return User 详情信息
     */
    User get(Long id);

    /**
     * 锁定用户
     * 
     * @param id ID
     * @return 详情信息
     */
    User lock(Long id);

    /**
     * 通过邀请码查找
     * 
     * @param invite 邀请码
     * @return User 用户信息
     */
    User getByInvite(String invite);

    /**
     * 通过手机号查找
     * 
     * @param phone 手机号
     * @return User 用户信息
     */
    User getByPhone(String phone);

    /**
     * 分页查询
     * 
     * @param query 筛选参数
     * @return Page<User> 分页结果
     */
    Page<User> query(UserQuery query);

    /**
     * 修改密码
     * 
     * @param model 新旧密码
     * @return Result<?> 修改结果
     */
    Result<?> modifyPwd(PwdModify model);

    /**
     * 修改手机号
     * 
     * @param phone 新手机号
     * @return Result<?> 修改结果
     */
    Result<?> modifyPhone(String phone);

    /**
     * 修改支付密码
     * 
     * @param payPwd 支付密码
     * @return Result<?> 修改结果
     */
    Result<?> modifyPayPwd(String payPwd);

    /**
     * 修改邀请码
     *
     * @param invite 邀请码
     * @return Result<?> 修改结果
     */
    Result<?> modifyInvite(String invite);

    /**
     * 修改资料
     *
     * @param model 修改资料
     * @return Result<?> 修改结果
     */
    Result<?> modifyDatum(UserModify model);

    /**
     * 更新认证
     * 
     * @param identity 认证资料
     * @return boolean 更新结果
     */
    boolean modifyIdentity(Identity identity);

    /**
     * 修改状态
     * 
     * @param model 修改状态
     * @return Result<?> 修改结果
     */
    Result<?> modifyStatus(UserStatusModify model);

}
