package net.yitun.ioften.crm.service;

import com.github.pagehelper.Page;

import net.yitun.basic.model.Result;
import net.yitun.ioften.crm.entity.Identity;
import net.yitun.ioften.crm.model.identity.IdentityApply;
import net.yitun.ioften.crm.model.identity.IdentityQuery;
import net.yitun.ioften.crm.model.identity.IdentityReview;

/**
 * <pre>
 * <b>用户 认证服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月13日 下午4:12:48 LH
 *         new file.
 * </pre>
 */
public interface IdentityService {

    /**
     * 详情信息
     * 
     * @param id ID
     * @return Identity 详情信息
     */
    Identity get(Long id);

    /**
     * 详情信息
     * 
     * @param id 用户ID
     * @return Identity 详情信息
     */
    Identity getByUid(Long uid);

    /**
     * 分页查询
     * 
     * @param query 筛选参数
     * @return Page<Identity> 分页结果
     */
    Page<Identity> query(IdentityQuery query);

    /**
     * 申请认证
     * 
     * @param apply 认证申请
     * @return Result<?> 申请结果
     */
    Result<?> apply(IdentityApply apply);

    /**
     * 申请审批
     * 
     * @param apply 认证申请
     * @return Result<?> 审批结果
     */
    Result<?> review(IdentityReview review);

}
