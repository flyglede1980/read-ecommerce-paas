package net.yitun.ioften.cms.website.action;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.Code;
import net.yitun.basic.model.Result;
import net.yitun.basic.utils.IdUtil;
import net.yitun.ioften.cms.CategoryApi;
import net.yitun.ioften.cms.entity.Category;
import net.yitun.ioften.cms.model.category.CategoryCreate;
import net.yitun.ioften.cms.model.category.CategoryDetail;
import net.yitun.ioften.cms.model.category.CategoryModify;
import net.yitun.ioften.cms.service.CategoryService;

@Api(tags = "资讯 类目接口")
@SuppressWarnings("unchecked")
@RestController("cms.CategoryApi")
public class CategoryAction implements CategoryApi {

    @Autowired
    protected CategoryService service;

    @Override
    @ApiOperation(value = "类目树")
    @ApiImplicitParam(name = "parentId", value = "类目父ID; 默认:0")
    public Result<List<CategoryDetail>> treeView(@RequestParam(value = "parentId", required = false) Long parentId) {
        return this.service.treeView(parentId);
    }

    @Override
    @ApiOperation(value = "详细信息")
//    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParam(name = "id", value = "ID, 类目的ID", required = true)
    public Result<CategoryDetail> detail(@PathVariable("id") Long id) {
        if (null == id || IdUtil.MIN > id)
            return new Result<>(Code.BAD_REQ, "ID无效");

        Category category = null;
        if (null == (category = this.service.detail(id)))
            return Result.UNEXIST;

        return new Result<>(new CategoryDetail(category.getClassId(), category.getUsersId(), category.getParentId(),
                category.getCode(), category.getName(), category.getLevel(), category.getRelation(), category.getIcon(),
                category.getDescription(), category.getSortId(), category.getIsEnabled(), category.getIsHot(),
                category.getIsRecommend(), category.getIsTop(), category.getIsAuthorized(), category.getCDate(),
                category.getMDate(), null, null));
    }

    @Override
    @ApiOperation(value = "根据父ID查子类目")
    @ApiImplicitParam(name = "parentId", value = "类目的父级ID", required = true)
    public Result<List<CategoryDetail>> list(@PathVariable("parentId") Long parentId) {
        if (null == parentId)
            return new Result<>(Code.BAD_REQ, "ID无效");
        return this.service.getCategoryList(parentId);
    }

    @Override
    @ApiOperation(value = "添加类目")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<?> create(@Validated @RequestBody CategoryCreate model) {
        // 判断类目名称是否有效
        Set<String> names = model.getNames();
        for (String name : names)
            if (StringUtils.isBlank(name))
                return new Result<>(Code.BAD_REQ, "类目名称无效");

        Long parentId = null;
        // 判断parentId不为0时，是否有效
        if (null == (parentId = model.getParentId()) //
                || 0L > parentId || (parentId > 0L && parentId < IdUtil.MIN))
            return new Result<>(Code.BAD_REQ, "父级类目ID无效");

        return this.service.create(model);
    }

    @Override
    @ApiOperation(value = "修改类目")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<?> modify(@Validated @RequestBody CategoryModify model) {
        if (null != model.getParentId() //
                && model.getClassId().equals(model.getParentId()))
            return new Result<>(Code.BAD_REQ, "父级类目ID无效");

        return this.service.edit(model);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "删除类目", notes = "会联动删除子类目")
    @ApiImplicitParam(name = "id", value = "ID, 限制1~1024个", required = true, allowMultiple = true)
    public Result<?> delete(@RequestParam("id") Set<Long> ids) {
        if (null == ids || ids.isEmpty() || 1024 < ids.size())
            return new Result<>(Code.BAD_REQ, "ID无效");

        return this.service.delete(ids);
    }

}
