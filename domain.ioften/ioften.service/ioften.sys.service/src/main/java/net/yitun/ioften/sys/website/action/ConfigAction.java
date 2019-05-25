package net.yitun.ioften.sys.website.action;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
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
import net.yitun.basic.security.SecurityUtil;
import net.yitun.ioften.Roles;
import net.yitun.ioften.sys.ConfigApi;
import net.yitun.ioften.sys.dicts.Scope;
import net.yitun.ioften.sys.entity.Config;
import net.yitun.ioften.sys.model.config.ConfigDetail;
import net.yitun.ioften.sys.model.config.ConfigQuery;
import net.yitun.ioften.sys.model.config.ConfigStore;
import net.yitun.ioften.sys.service.ConfigService;

@Api(tags = "系统 配置管理")
@RestController("sys.ConfigApi")
@SuppressWarnings("unchecked")
public class ConfigAction implements ConfigApi {

    @Autowired
    protected ConfigService service;

    @Override
    @ApiOperation(value = "简要信息")
    @ApiImplicitParam(name = "code", value = "代码, app.conf: 应用配置; app.about: 应用关于; app.version: 应用版本", required = true)
    public Result<ConfigDetail> info(@PathVariable("code") String code) {
        if (null == (code = StringUtils.trimToNull(code)))
            return new Result<>(Code.BAD_REQ, "代码无效");

        Scope scope = null;
        Config config = null;
        if (null == (config = this.service.getByCode(code)) // 判断有可能DB、缓存都不存在该Code的数据
                || null == (scope = config.getScope()))
            return Result.UNEXIST;

        // 判断配置的SCOPE对应当前用户的权限是否可现
        else if ((scope != Scope.ALL) //
                || (scope == Scope.SYS && !SecurityUtil.hasAnyRole(Roles.ADMIN.name())))
            return new Result<>(Code.FORBIDDEN, "无权获取该配置");

        return new Result<ConfigDetail>(new ConfigDetail(config.getValue(), config.getMtime()));
    }

    @Override
    @ApiOperation(value = "详细信息")
    @ApiImplicitParam(name = "code", value = "代码, app.conf: 应用配置; app.about: 应用关于; app.version: 应用版本", required = true)
    public ConfigDetail code(@PathVariable("code") String code) {
        Config config = this.service.getByCode(code);

        return null == config ? null // 缺失配置信息
                : new ConfigDetail(config.getId(), config.getCode(), config.getName(), config.getScope(), config.getValue(),
                        config.getRemark(), config.getModifier(), config.getModifierId(), config.getCtime(), config.getMtime());
    }

    @Override
    @ApiOperation(value = "分页查询")
    @PreAuthorize("hasRole('ADMIN')")
    public PageResult<ConfigDetail> query(@Validated ConfigQuery query) {
        Page<Config> page = this.service.query(query);
        return new PageResult<>(page.getTotal(), page.getPages(),
                page.stream()
                        .map(config -> new ConfigDetail(config.getId(), config.getCode(), config.getName(), config.getScope(),
                                config.getValue(), config.getRemark(), config.getModifier(), config.getModifierId(),
                                config.getCtime(), config.getMtime()))
                        .collect(Collectors.toList()));
    }

    @Override
    @ApiOperation(value = "修改配置")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<?> store(@Validated @RequestBody ConfigStore model) {
        return this.service.store(model);
    }

    @Override
    @ApiOperation(value = "删除配置")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParam(name = "id", value = "ID, 限制1~1024个", required = true, allowMultiple = true)
    public Result<Integer> delete(@RequestParam("id") Set<Long> ids) {
        if (null == ids || ids.isEmpty() || 1024 < ids.size())
            return new Result<>(Code.BAD_REQ, "ID无效");

        return this.service.delete(ids);
    }

}
