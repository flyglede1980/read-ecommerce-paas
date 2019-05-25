package net.yitun.ioften.cms.website.action;

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
import io.swagger.annotations.ApiResponse;
import net.yitun.basic.Code;
import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.ioften.cms.FavoriteApi;
import net.yitun.ioften.cms.entity.Favorite;
import net.yitun.ioften.cms.model.favorite.FavoriteDetail;
import net.yitun.ioften.cms.model.favorite.FavoriteQuery;
import net.yitun.ioften.cms.service.FavoriteService;

@Api(tags = "资讯 收藏接口")
@RestController("cms.FavoriteApi")
public class FavoriteAction implements FavoriteApi {

    @Autowired
    protected FavoriteService service;

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "收藏列表")
    public PageResult<FavoriteDetail> query(@Validated FavoriteQuery query) {
        query.setUid(SecurityUtil.getId());
        Page<Favorite> page = this.service.query(query);
        return new PageResult<>(page.getTotal(), page.getPages(),
                page.stream().map(favorite -> new FavoriteDetail(favorite.getId(), favorite.getUid(), favorite.getAid(),
                        favorite.getCtime(), favorite.getNews())).collect(Collectors.toList()));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "收藏资讯")
    @ApiImplicitParam(name = "aid", value = "资讯ID", required = true)
    @ApiResponse(code = 200, message = "收藏结果: true:已收藏; false:已取消")
    public Result<Boolean> collect(@PathVariable("aid") Long aid) {
        Long uid = SecurityUtil.getId();
        return this.service.collect(uid, aid);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "取消收藏")
    @ApiImplicitParam(name = "id", value = "ID, 限制1~1024个", required = true, allowMultiple = true)
    public Result<?> delete(@RequestParam("id") Set<Long> ids) {
        if (null == ids || ids.isEmpty() || 1024 < ids.size())
            return new Result<>(Code.BAD_REQ, "ID无效");

        Long uid = SecurityUtil.getId();
        return this.service.delete(uid, ids);
    }

}
