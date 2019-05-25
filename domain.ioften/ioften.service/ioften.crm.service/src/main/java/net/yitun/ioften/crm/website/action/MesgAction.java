package net.yitun.ioften.crm.website.action;

import java.util.Set;
import java.util.stream.Collectors;

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
import net.yitun.basic.utils.IdUtil;
import net.yitun.ioften.crm.MesgApi;
import net.yitun.ioften.crm.entity.Mesg;
import net.yitun.ioften.crm.model.mesg.MesgDetail;
import net.yitun.ioften.crm.model.mesg.MesgQuery;
import net.yitun.ioften.crm.model.mesg.MesgStore;
import net.yitun.ioften.crm.model.mesg.MyMesgTotal;
import net.yitun.ioften.crm.service.MesgService;

@Api(tags = "用户 消息接口")
@RestController("crm.MesgApi")
@SuppressWarnings("unchecked")
public class MesgAction implements MesgApi {

    @Autowired
    protected MesgService service;

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    public Result<MesgDetail> detail(@PathVariable("id") Long id) {
        if (null == id || IdUtil.MIN > id)
            return new Result<>(Code.BAD_REQ, "ID无效");

        Mesg mesg = null;
        if (null == (mesg = this.service.get(id)))
            return Result.UNEXIST;

        return new Result<>(new MesgDetail(mesg.getId(), mesg.getUid(), mesg.getType(), mesg.getActor(), mesg.getTarget(),
                mesg.getContent(), mesg.getTimes(), mesg.getStatus(), mesg.getCtime(), mesg.getMtime(), mesg.getActorUser(),
                mesg.getTargetRefer()));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "分页查询")
    public PageResult<MesgDetail> query(@Validated MesgQuery query) {
        query.setUid(SecurityUtil.getId());
        Page<Mesg> page = this.service.query(query);
        return new PageResult<>(page.getTotal(), page.getPages(),
                page.stream()
                        .map(mesg -> new MesgDetail(mesg.getId(), mesg.getUid(), mesg.getType(), mesg.getActor(),
                                mesg.getTarget(), mesg.getContent(), mesg.getTimes(), mesg.getStatus(), mesg.getCtime(),
                                mesg.getMtime(), mesg.getActorUser(), mesg.getTargetRefer()))
                        .collect(Collectors.toList()));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public Result<MyMesgTotal> myTotal() {
        Long uid = SecurityUtil.getId();
        MyMesgTotal total = this.service.myTotal(uid);
        return new Result<>(total);
    }

    @Override
    @ApiOperation(value = "存储消息")
    public Result<Boolean> store(@Validated @RequestBody MesgStore model) {
        boolean result = this.service.store(model);
        return new Result<>(result);
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "标记查看")
    @ApiImplicitParam(name = "id", value = "ID, 限制1~1024个", required = true, allowMultiple = true)
    public Result<Boolean> viewed(@RequestParam("id") Set<Long> ids) {
        if (null == ids || ids.isEmpty() || 1024 < ids.size())
            return new Result<>(Code.BAD_REQ, "ID无效");

        boolean result = this.service.viewed(ids);
        return new Result<>(result);
    }

}
