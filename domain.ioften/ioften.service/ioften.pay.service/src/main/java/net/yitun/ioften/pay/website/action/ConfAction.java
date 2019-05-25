package net.yitun.ioften.pay.website.action;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.Code;
import net.yitun.basic.model.Result;
import net.yitun.ioften.pay.ConfApi;
import net.yitun.ioften.pay.entity.vo.conf.GainConf;
import net.yitun.ioften.pay.model.conf.gain.GainConfDetail;
import net.yitun.ioften.pay.model.conf.gain.GainConfModify;
import net.yitun.ioften.pay.model.conf.gain.GainConfRatioDetail;
import net.yitun.ioften.pay.service.ConfService;

@Api(tags = "支付 配置接口")
@RestController("pay.ConfApi")
public class ConfAction implements ConfApi {

    @Autowired
    protected ConfService service;

    @Override
    @ApiOperation(value = "可否支付")
    public Result<Boolean> can() {
        boolean can = this.service.can();
        return new Result<>(can);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "查看挖矿配置")
    public Result<GainConfDetail> gainConf() {
        GainConfDetail gainConf = this.gainConfInfo();
        return new Result<>(gainConf);
    }

    @Override
    public GainConfDetail gainConfInfo() {
        GainConf gainConf = this.service.gainConf();
        return new GainConfDetail(gainConf.getHandFee() //
                , gainConf.getViewTime(), gainConf.getInviteCardinal(), gainConf.getViewCardinals(),
                gainConf.getEnjoyCardinals(), gainConf.getShareCardinals(),
                null == gainConf.getGainConfRatios() ? null
                        : gainConf.getGainConfRatios().stream()
                                .map(decimal -> new GainConfRatioDetail(decimal.getAmount(), decimal.getRatio()))
                                .collect(Collectors.toList()));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "修改挖矿配置")
    public Result<?> gainConfModify(@Validated @RequestBody GainConfModify model) {
        for (Long value : model.getEnjoyCardinals())
            if (-1L >= value)
                return new Result<>(Code.BAD_REQ, "A点赞挖矿基数无效");

        for (Long value : model.getViewCardinals())
            if (-1L >= value)
                return new Result<>(Code.BAD_REQ, "A阅读挖矿基数无效");

        for (Long value : model.getShareCardinals())
            if (-1L >= value)
                return new Result<>(Code.BAD_REQ, "A分享挖矿基数无效");

        for (GainConfRatioDetail ratio : model.getGainConfRatios())
            if (null == ratio || null == ratio.getAmount() //
                    || -1L >= ratio.getAmount() || null == ratio.getRatio() || -1 >= ratio.getRatio())
                return new Result<>(Code.BAD_REQ, "C充值系数无效");

        return this.service.gainConfModify(model);
    }

    @Override
    @ApiOperation(value = "获取文章挖矿充值系数")
    @PreAuthorize("hasAnyRole('USER_EW', 'ADMIN')")
    @ApiImplicitParam(name = "amount", value = "充值金额", required = true)
    public Result<Float> gainConfRatio(@RequestParam("amount") Long amount) {
        if (null == amount)
            return new Result<>(Code.BAD_REQ, "充值金额无效");

        Float ratio = this.service.gainConfRechargeRatio(amount);
        return new Result<>(ratio);
    }

}
