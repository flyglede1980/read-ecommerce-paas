package net.yitun.ioften.cms.website.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.Code;
import net.yitun.basic.dict.YesNo;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.ioften.cms.EnjoyApi;
import net.yitun.ioften.cms.model.enjoy.EnjoyDetail;
import net.yitun.ioften.cms.service.EnjoyService;

@Api(tags = "资讯 点赞挖矿")
@RestController("cms.EnjoyApi")
public class EnjoyAction implements EnjoyApi {

    @Autowired
    protected EnjoyService service;

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "资讯点赞")
    @ApiImplicitParam(name = "aid", value = "资讯ID", required = true)
    public Result<EnjoyDetail> enjoy(@PathVariable("aid") Long aid) {
        if (null == aid || IdUtil.MIN > aid)
            return new Result<>(Code.BAD_REQ, "资讯ID无效");

        Long uid = SecurityUtil.getId();
        EnjoyDetail enjoy = this.service.enjoy(uid, aid);

        int code = YesNo.YES == enjoy.getEnjoyed() ? Code.OK : Code.BIZ_ERROR;
        return new Result<>(code, enjoy.getMessage(), enjoy.setMessage(null));
    }

}
