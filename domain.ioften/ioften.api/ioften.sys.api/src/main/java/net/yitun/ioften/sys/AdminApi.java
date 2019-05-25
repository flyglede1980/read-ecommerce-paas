package net.yitun.ioften.sys;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.ioften.sys.conf.Conf;
import net.yitun.ioften.sys.model.admin.AdminDetail;
import net.yitun.ioften.sys.model.admin.AdminQuery;
import net.yitun.ioften.sys.model.admin.Logins;

/**
 * <pre>
 * <b>系统 运营管理.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月6日 上午10:07:02 LH
 *         new file.
 * </pre>
 */
public interface AdminApi {

    /**
     * 账号信息
     * 
     * @return Result<AdminDetail> 账号信息
     */
    @GetMapping(value = Conf.ROUTE //
            + "/my", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<AdminDetail> my();

    /**
     * 后台登录
     * 
     * @param model 登录模型
     * @return Result<?> 登录结果
     */
    @PostMapping(value = { "/slogin", Conf.ROUTE //
            + "/slogin" }, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> login(@RequestBody Logins model);

    /**
     * 详细信息
     * 
     * @param id ID
     * @return Result<AdminDetail> 详情信息
     */
    @GetMapping(value = Conf.ROUTE //
            + "/admin/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<AdminDetail> detail(@PathVariable("id") Long id);

    /**
     * 分页查询
     * 
     * @param query 查询参数
     * @return PageResult<AdminDetail> 分页结果
     */
    @GetMapping(value = Conf.ROUTE //
            + "/admins", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<AdminDetail> query(AdminQuery query);

}
