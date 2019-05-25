package net.yitun.ioften.adv.website.action;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.model.Result;
import net.yitun.ioften.adv.ShowApi;
import net.yitun.ioften.adv.model.adv.AdvDetail;
import net.yitun.ioften.adv.service.ShowService;

@Api(tags = "广告 显示接口")
@RestController("adv.ShowApi")
@SuppressWarnings("unchecked")
public class ShowAction implements ShowApi {

    @Autowired
    protected ShowService service;

    @Override
    @ApiOperation(value = "APP 闪屏广告")
    public Result<AdvDetail> flash() {
        AdvDetail adv = null;
        if (null == (adv = this.service.flash()))
            return Result.UNEXIST;

        return new Result<>(adv);
    }

    @Override
    @ApiOperation(value = "推荐页 Banner")
    public Result<Collection<AdvDetail>> banner() {
        Collection<AdvDetail> advs = null;
        if (null == (advs = this.service.banner()) //
                || advs.isEmpty())
            return Result.UNEXIST;

        return new Result<>(advs);
    }

}
