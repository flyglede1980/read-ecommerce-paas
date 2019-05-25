package net.yitun.ioften.cms.website.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.model.Result;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.ioften.cms.ConfApi;
import net.yitun.ioften.cms.conf.Constant;
import net.yitun.ioften.cms.dicts.Type;
import net.yitun.ioften.cms.model.conf.CmsConfDetail;
import net.yitun.ioften.cms.model.conf.CmsConfModify;
import net.yitun.ioften.sys.ConfigApi;
import net.yitun.ioften.sys.dicts.Scope;
import net.yitun.ioften.sys.model.config.ConfigDetail;
import net.yitun.ioften.sys.model.config.ConfigStore;

@Api(tags = "系统 应用设置")
@RestController("sys.ConfApi")
public class ConfAction implements ConfApi {

    @Autowired
    protected ConfigApi configApi; // 通过标准的系统配置就行统一管理

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "获取资讯配置")
    public Result<CmsConfDetail> cmsConf() {
        CmsConfDetail smsConf = this.cmsConfInfo();
        return new Result<CmsConfDetail>(smsConf);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ApiOperation(value = "修改取资讯配置")
    public Result<?> cmsConfModify(@Validated @RequestBody CmsConfModify model) {
        ConfigStore config //
                = new ConfigStore(Constant.CMS_CONF, "资讯配置", Scope.ALL, model, null);
        return this.configApi.store(config);
    }

    @Override
    public CmsConfDetail cmsConfInfo() {
        ConfigDetail config;
        CmsConfDetail smsConf = null;
        if (null != (config = this.configApi.code(Constant.CMS_CONF)) // 判断配置是否存在
                && null != config.getValue())
            smsConf = JsonUtil.toBean(config.getValue(), CmsConfDetail.class);

        if (null == smsConf) // 如果无任何存档，则直接初始化默认配置
            smsConf = new CmsConfDetail();

        return smsConf;
    }

    @Override
    public int getCmsShowSize(Type type, boolean isHots) {
        if (!isHots)
            return this.cmsConfInfo().getShowSize();

        if (type == Type.IMAGE)
            return this.cmsConfInfo().getImageEndRecSize();

        return this.cmsConfInfo().getArticleEndRecSize();
    }

}
