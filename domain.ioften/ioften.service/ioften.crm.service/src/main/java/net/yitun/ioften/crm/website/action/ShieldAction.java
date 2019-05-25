package net.yitun.ioften.crm.website.action;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.Code;
import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.ioften.crm.ShieldApi;
import net.yitun.ioften.crm.entity.Shield;
import net.yitun.ioften.crm.model.shield.ShieldDetail;
import net.yitun.ioften.crm.model.shield.ShieldQuery;
import net.yitun.ioften.crm.service.ShieldService;

@Api(tags = "用户 拉黑接口")
@RestController("crm.ShieldApi")
public class ShieldAction implements ShieldApi {

    @Autowired
    protected ShieldService service;

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "分页查询")
    public PageResult<ShieldDetail> query(@Validated ShieldQuery query) {
        Page<Shield> page = this.service.query(query);
        return new PageResult<>(page.getTotal(), page.getPages(),
                page.stream().map(shield -> new ShieldDetail(shield.getId(), shield.getUid(), shield.getSide(),
                        shield.getCtime(), shield.getMtime(), shield.getSideUser())).collect(Collectors.toList()));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "标记拉黑")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, allowMultiple = true)
    public Result<?> stamp(@PathVariable("id") Long id) {
        if (null == id || IdUtil.MIN > id)
            return new Result<>(Code.BAD_REQ, "对方ID无效");

        if (id.equals(SecurityUtil.getId()))
            return new Result<>(Code.BAD_REQ, "自己不能拉黑自己");

        return this.service.stamp(id);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "解除拉黑")
    @ApiImplicitParam(name = "id", value = "ID, 限制1~1024个", required = true, allowMultiple = true)
    public Result<?> delete(@RequestParam("id") Set<Long> ids) {
        if (null == ids || ids.isEmpty() || 1024 < ids.size())
            return new Result<>(Code.BAD_REQ, "ID无效");

        Long uid = SecurityUtil.getId();
        return this.service.delete(uid, ids);
    }

}
