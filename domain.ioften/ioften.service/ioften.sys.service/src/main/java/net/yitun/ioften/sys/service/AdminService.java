package net.yitun.ioften.sys.service;

import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.Page;

import net.yitun.basic.model.Result;
import net.yitun.ioften.sys.entity.Admin;
import net.yitun.ioften.sys.model.admin.AdminQuery;
import net.yitun.ioften.sys.model.admin.Logins;

/**
 * <pre>
 * <b>系统 运营管理服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月6日 上午11:58:08 LH
 *         new file.
 * </pre>
 */
public interface AdminService {

    /**
     * 详情信息
     * 
     * @param id ID
     * @return Admin 详情信息
     */
    Admin get(Long id);

    /**
     * 分页查询
     * 
     * @param query 筛选参数
     * @return Page<Admin> 分页结果
     */
    Page<Admin> query(AdminQuery query);

    /**
     * 后台登录
     * 
     * @param model    登录模型
     * @param response Http响应
     * @return Result<?> 登录结果
     */
    Result<?> login(Logins model, HttpServletResponse response);

}
