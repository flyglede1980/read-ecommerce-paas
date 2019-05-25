package net.yitun.ioften.crm.website.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.yitun.ioften.crm.SettingApi;
import net.yitun.ioften.crm.model.setting.SettingDetail;
import net.yitun.ioften.crm.model.setting.SettingModify;
import net.yitun.ioften.crm.service.SettingService;

@Api(tags = "用户 我的设置")
@RestController("crm.SettingApi")
public class SettingAction implements SettingApi {

    @Autowired
    protected SettingService service;

    @Override
    @ApiOperation(value = "获取我的设置")
    public SettingDetail get(Long id, String code) {
        return this.service.get(id, code);
    }

    @Override
    @ApiOperation(value = "更新我的设置")
    public Boolean modify(@Validated @RequestBody SettingModify model) {
        return this.service.modify(model);
    }

}
