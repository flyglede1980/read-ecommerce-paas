package net.yitun.ioften.cms.website.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.Code;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.ioften.cms.ShareApi;
import net.yitun.ioften.cms.dicts.ShareWay;
import net.yitun.ioften.cms.model.share.ShareDetail;
import net.yitun.ioften.cms.service.ShareService;

@Api(tags = "资讯 分享接口")
@RestController("cms.ShareApi")
@SuppressWarnings("unchecked")
public class ShareAction implements ShareApi {

    @Autowired
    protected ShareService service;

    @Override
    @ApiOperation(value = "生成分享代码")
    @ApiImplicitParam(name = "aid", value = "资讯ID", required = true)
    public Result<ShareDetail> gen(@PathVariable("aid") Long aid) {
        if (null == aid || IdUtil.MIN > aid)
            return new Result<>(Code.BAD_REQ, "资讯ID无效");

        Long uid = SecurityUtil.getId();
        uid = null == uid ? 0L : uid; // 如果当前未登录，则默认未系统推荐 uid=0

        ShareDetail share = null;
        ShareWay way = ShareWay.N;
        if (null == (share = this.service.gen(uid, aid, way)))
            return Result.UNEXIST;

        return new Result<>(share);
    }

    @Override
    @ApiOperation(value = "展示分享信息")
    @ApiImplicitParam(name = "id", value = "分享代码", required = true)
    public Result<ShareDetail> show(@PathVariable("id") Long id) {
        if (null == id || IdUtil.MIN > id)
            return new Result<>(Code.BAD_REQ, "分享代码无效");

        ShareDetail share = null;
        if (null == (share = this.service.show(id)))
            return Result.UNEXIST;

        return new Result<>(share);
    }

}
