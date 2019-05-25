package net.yitun.comon.website.action;

import java.util.Collection;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.Code;
import net.yitun.basic.model.Result;
import net.yitun.basic.utils.SetUtil;
import net.yitun.comon.CobjsApi;
import net.yitun.comon.model.cobj.Cobj;
import net.yitun.comon.model.cobj.CobjDetail;
import net.yitun.comon.model.cobj.CobjModify;
import net.yitun.comon.model.cobj.CobjStream;
import net.yitun.comon.service.CobjsService;

@Api(tags = "通用 云储存储")
@RestController("basic.CobjsApi")
public class CobjsAction implements CobjsApi {

    @Autowired
    protected CobjsService service;

    @Autowired
    protected HttpServletResponse response;

    @Override
    @ApiOperation(value = "获取域名")
    public String dns(String path) {
        if (StringUtils.isBlank(path))
            return null;

        return this.service.dns(path);
    }

    @Override
    @ApiOperation(value = "获取路径")
    public String view(String path) {
        if (StringUtils.isBlank(path))
            return null;

        return this.service.view(path);
    }

    @Override
    @ApiOperation(value = "存储确认")
    public Result<Collection<CobjDetail>> sure(@Validated @RequestBody Cobj... cobjs) {
        if (null == cobjs || 0 == cobjs.length)
            return new Result<>(Code.BAD_REQ, "资源无效");

        return this.sure(SetUtil.asSet(cobjs));
    }

    @Override
    public Result<Collection<CobjDetail>> sure(Collection<Cobj> cobjs) {
        if (null == cobjs || cobjs.isEmpty() || 1024 < cobjs.size())
            return new Result<>(Code.BAD_REQ, "资源无效或数量操过上限");

        return this.service.sure(cobjs);
    }

    @Override
    @ApiOperation(value = "资源显示")
    @ApiImplicitParam(name = "path", value = "资源", required = true)
    public Object show(@RequestParam("path") String path) {
        if (StringUtils.isBlank(path))
            return Result.UNEXIST;

        CobjDetail cobj = null;
        Collection<CobjDetail> collection = null;
        Result<Collection<CobjDetail>> result = this.service.parse(SetUtil.asSet(path));

        if (!result.ok() || null == (collection = result.getData()) || collection.isEmpty()
                || null == (cobj = collection.iterator().next()) || null != cobj.getError())
            return new Result<>(Code.BIZ_ERROR, "无法显示资源");

        return new ModelAndView(new RedirectView(cobj.getNewKey())); // 重定向到资源可视地址
    }

    @Override
    @ApiOperation(value = "资源解析")
    @ApiImplicitParam(name = "path", value = "资源, 限制1~1024个", required = true, allowMultiple = true)
    public Result<Collection<CobjDetail>> parse(@RequestParam("path") String... paths) {
        return this.parse(SetUtil.asLinkedSet(paths));
    }

    @Override
    public Result<Collection<CobjDetail>> parse(Set<String> paths) {
        if (null == paths || paths.isEmpty() || 1024 < paths.size())
            return new Result<>(Code.BAD_REQ, "资源无效或数量操过上限");

        return this.service.parse(paths);
    }

    @Override
    public Result<Collection<CobjDetail>> store(CobjStream... streams) {
        return this.store(SetUtil.asSet(streams));
    }

    @Override
    public Result<Collection<CobjDetail>> store(Collection<CobjStream> streams) {
        if (null == streams || streams.isEmpty() || 1024 < streams.size())
            return new Result<>(Code.BAD_REQ, "资源无效或数量操过上限");

        return this.service.store(streams);
    }

    @Override
    @ApiOperation(value = "资源删除")
    @ApiImplicitParam(name = "path", value = "资源, 限制1~1024个", required = true, allowMultiple = true)
    public Result<Collection<CobjDetail>> delete(@RequestParam("path") String... paths) {
        return this.delete(SetUtil.asSet(paths));
    }

    @Override
    public Result<Collection<CobjDetail>> delete(Collection<String> paths) {
        if (null == paths || paths.isEmpty() || 1024 < paths.size())
            return new Result<>(Code.BAD_REQ, "资源无效或数量操过上限");

        return this.service.delete(paths);
    }

    @Override
    @ApiOperation(value = "资源编辑")
    public Result<Collection<CobjDetail>> modify(CobjModify model) {
        return this.service.modify(model);
    }

}
