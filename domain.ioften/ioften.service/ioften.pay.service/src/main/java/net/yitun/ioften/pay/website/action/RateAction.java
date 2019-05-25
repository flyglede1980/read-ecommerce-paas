package net.yitun.ioften.pay.website.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.model.Result;
import net.yitun.ioften.pay.RateApi;
import net.yitun.ioften.pay.service.RateService;

@Api(tags = "支付 汇率接口")
@RestController("pay.RateApi")
public class RateAction implements RateApi {

    @Autowired
    protected RateService service;

    @Override
    @ApiOperation(value = "当前汇率")
    public Long now() {
        return this.service.now();
    }

    @Override
    @ApiOperation(value = "刷新汇率")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<?> refresh() {
        return this.service.refresh();
    }

}
