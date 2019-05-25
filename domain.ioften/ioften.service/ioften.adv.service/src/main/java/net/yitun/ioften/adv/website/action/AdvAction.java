package net.yitun.ioften.adv.website.action;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.Code;
import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.basic.utils.IdUtil;
import net.yitun.ioften.adv.AdvApi;
import net.yitun.ioften.adv.entity.Adv;
import net.yitun.ioften.adv.model.adv.AdvCreate;
import net.yitun.ioften.adv.model.adv.AdvDetail;
import net.yitun.ioften.adv.model.adv.AdvModify;
import net.yitun.ioften.adv.model.adv.AdvQuery;
import net.yitun.ioften.adv.service.AdvService;

@Api(tags = "广告 内容接口")
@RestController("adv.AdvApi")
@SuppressWarnings("unchecked")
public class AdvAction implements AdvApi {

    @Autowired
    protected AdvService service;

    @Override
    @ApiOperation(value = "广告详细")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    public Result<AdvDetail> detail(@PathVariable("id") Long id) {
        if (null == id || IdUtil.MIN > id)
            return new Result<>(Code.BAD_REQ, "ID无效");

        Adv adv = null;
        if (null == (adv = this.service.get(id)))
            return Result.UNEXIST;

        return new Result<>(new AdvDetail(adv.getId(), adv.getTitle(), adv.getCover(), adv.getSeat(), adv.getAction(),
                adv.getShowBtn(), adv.getActionConf(), adv.getSequence(), adv.getRemark(), adv.getStatus(), adv.getCtime(),
                adv.getMtime()));
    }

    @Override
    @ApiOperation(value = "分页查询")
    @PreAuthorize("hasRole('ADMIN')")
    public PageResult<AdvDetail> query(@Validated AdvQuery query) {
        Page<Adv> page = this.service.query(query);
        return new PageResult<>(page.getTotal(), page.getPages(),
                page.stream()
                        .map(adv -> new AdvDetail(adv.getId(), adv.getTitle(), adv.getCover(), adv.getSeat(), adv.getAction(),
                                adv.getShowBtn(), adv.getActionConf(), adv.getSequence(), adv.getRemark(), adv.getStatus(),
                                adv.getCtime(), adv.getMtime()))
                        .collect(Collectors.toList()));
    }

    @Override
    @ApiOperation(value = "新增广告")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<?> create(@Validated @RequestBody AdvCreate model) {
        return this.service.create(model);
    }

    @Override
    @ApiOperation(value = "修改广告")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<?> modify(@Validated @RequestBody AdvModify model) {
        return this.service.modify(model);
    }

    @Override
    @ApiOperation(value = "删除广告")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParam(name = "id", value = "ID, 限制1~1024个", required = true, allowMultiple = true)
    public Result<?> delete(@RequestParam("id") Set<Long> ids) {
        if (null == ids || ids.isEmpty() || 1024 < ids.size())
            return new Result<>(Code.BAD_REQ, "ID无效");

        return this.service.delete(ids);
    }

}
