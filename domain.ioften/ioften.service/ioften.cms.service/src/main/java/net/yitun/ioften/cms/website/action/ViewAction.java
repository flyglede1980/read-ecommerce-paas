package net.yitun.ioften.cms.website.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.Code;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.ioften.cms.ViewApi;
import net.yitun.ioften.cms.dicts.GainStatus;
import net.yitun.ioften.cms.model.gain.GainDetail;
import net.yitun.ioften.cms.service.GainService;
import net.yitun.ioften.cms.service.dto.GainTask;

@Api(tags = "资讯 阅读挖矿")
@RestController("cms.ViewApi")
public class ViewAction implements ViewApi {

    @Autowired
    protected GainService service;

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "可否阅读挖矿", notes = "返回挖矿状态; 如果已到挖矿, 会直接返回SUCCESS状态和挖矿数量")
    @ApiImplicitParam(name = "aid", value = "资讯ID", required = true)
    public Result<GainDetail> view(@PathVariable("aid") Long aid) {
        if (null == aid || IdUtil.MIN > aid)
            return new Result<>(Code.BAD_REQ, "资讯ID无效");

        Long uid = SecurityUtil.getId();
        GainTask task = this.service.view(uid, aid);

        int code = GainStatus.ON == task.getStatus() ? Code.OK : Code.BIZ_ERROR;
        return new Result<>(code, task.getMessage(), new GainDetail(null, task.getViewTime(), task.getStatus()));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "阅读挖矿计算")
    @ApiImplicitParam(name = "aid", value = "资讯ID", required = true)
    public Result<GainDetail> submit(@PathVariable("aid") Long aid) {
        if (null == aid || IdUtil.MIN > aid)
            return new Result<>(Code.BAD_REQ, "资讯ID无效");

        Long uid = SecurityUtil.getId();
        GainTask task = this.service.calcView(uid, aid);

        int code = GainStatus.SUCCESS == task.getStatus() ? Code.OK : Code.BIZ_ERROR;
        return new Result<>(code, task.getMessage(), new GainDetail(task.getAward(), null, task.getStatus()));
    }

}
