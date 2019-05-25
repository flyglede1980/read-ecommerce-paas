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
import net.yitun.basic.Code;
import net.yitun.basic.dict.YesNo;
import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.ioften.cms.BrowseApi;
import net.yitun.ioften.cms.entity.Browse;
import net.yitun.ioften.cms.entity.Favorite;
import net.yitun.ioften.cms.model.browse.BrowseDetail;
import net.yitun.ioften.cms.model.browse.BrowseQuery;
import net.yitun.ioften.cms.service.BrowseService;
import net.yitun.ioften.cms.service.FavoriteService;

@Api(tags = "资讯 浏览记录")
@RestController("cms.BrowseApi")
public class BrowseAction implements BrowseApi {

    @Autowired
    protected BrowseService service;

    @Autowired
    protected FavoriteService favoriteService;

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "浏览信息")
    @ApiImplicitParam(name = "aid", value = "文章ID", required = true)
    public Result<BrowseDetail> state(@PathVariable("aid") Long aid) {
        if (null == aid || IdUtil.MIN > aid)
            return new Result<>(Code.BAD_REQ, "文章ID无效");

        // 获取用的的ID
        Long uid = SecurityUtil.getId();
        Browse browse = this.service.find(uid, aid);
        if (null == browse)
            return new Result<>(Code.BIZ_ERROR, "您存在违规操作, 不能对该资讯挖矿");

        // 获取是否收藏信息
        Favorite favorite = this.favoriteService.find(uid, aid);
        YesNo _favorite = (null == favorite) ? YesNo.NO : YesNo.YES;

        return new Result<>(new BrowseDetail(browse.getId(), browse.getUid(), browse.getAid(), browse.getView(),
                browse.getEnjoy(), _favorite, null /* comment */, browse.getAward(), browse.getViewAward(),
                browse.getEnjoyTime(), browse.getEnjoyAward(), browse.getReward(), browse.getCtime(), browse.getMtime()));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "浏览历史")
    public PageResult<BrowseDetail> query(@Validated BrowseQuery query) {
        query.setUid(SecurityUtil.getId());
        Page<Browse> page = this.service.query(query);
        return new PageResult<>(page.getTotal(), page.getPages(),
                page.stream()
                        .map(browse -> new BrowseDetail(browse.getId(), browse.getUid(), browse.getAid(), browse.getView(),
                                browse.getEnjoy(), null /* favorite */, null /* comment */, browse.getAward(),
                                browse.getViewAward(), browse.getEnjoyTime(), browse.getEnjoyAward(), browse.getReward(),
                                browse.getCtime(), browse.getMtime(), browse.getNews()))
                        .collect(Collectors.toList()));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "删除记录")
    @ApiImplicitParam(name = "id", value = "ID, 限制1~1024个", required = true, allowMultiple = true)
    public Result<?> delete(@RequestParam("id") Set<Long> ids) {
        if (null == ids || ids.isEmpty() || 1024 < ids.size())
            return new Result<>(Code.BAD_REQ, "ID无效");

        Long uid = SecurityUtil.getId();
        return this.service.delete(uid, ids);
    }

}
