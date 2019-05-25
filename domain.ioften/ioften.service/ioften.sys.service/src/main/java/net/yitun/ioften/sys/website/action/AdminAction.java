package net.yitun.ioften.sys.website.action;

import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

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
import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.ioften.sys.AdminApi;
import net.yitun.ioften.sys.entity.Admin;
import net.yitun.ioften.sys.model.admin.AdminDetail;
import net.yitun.ioften.sys.model.admin.AdminQuery;
import net.yitun.ioften.sys.model.admin.Logins;
import net.yitun.ioften.sys.service.AdminService;

@Api(tags = "系统 运营管理")
@RestController("sys.AdminApi")
@SuppressWarnings("unchecked")
public class AdminAction implements AdminApi {

    @Autowired
    protected AdminService service;

    @Autowired
    protected HttpServletResponse response;

    @Override
    @ApiOperation(value = "账号信息")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<AdminDetail> my() {
        Long id = SecurityUtil.getId();
        return this.detail(id);
    }

    @Override
    @ApiOperation(value = "后台登录")
    public Result<?> login(@Validated @RequestBody Logins model) {
        return this.service.login(model, this.response);
    }

    @Override
    @ApiOperation(value = "详情信息")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    public Result<AdminDetail> detail(@PathVariable("id") Long id) {
        Admin admin = null;
        if (null == (admin = this.service.get(id)))
            return Result.UNEXIST;

        return new Result<>(new AdminDetail(admin.getId(), admin.getName(), admin.getAccount(), admin.getRoles(),
                admin.getPhone(), admin.getEmail(), admin.getStatus(), admin.getCtime(), admin.getMtime()));
    }

    @Override
    @ApiOperation(value = "分页查询")
    @PreAuthorize("hasRole('ADMIN')")
    public PageResult<AdminDetail> query(@Validated AdminQuery query) {
        Page<Admin> page = this.service.query(query);
        return new PageResult<>(page.getTotal(), page.getPages(),
                page.stream()
                        .map(admin -> new AdminDetail(admin.getId(), admin.getName(), admin.getAccount(), admin.getRoles(),
                                admin.getPhone(), admin.getEmail(), admin.getStatus(), admin.getCtime(), admin.getMtime()))
                        .collect(Collectors.toList()));
    }

}
