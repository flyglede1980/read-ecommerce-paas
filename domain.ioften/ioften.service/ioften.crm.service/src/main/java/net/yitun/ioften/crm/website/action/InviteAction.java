package net.yitun.ioften.crm.website.action;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.ioften.crm.InviteApi;
import net.yitun.ioften.crm.entity.Invite;
import net.yitun.ioften.crm.entity.vo.invite.MyInviteTotal;
import net.yitun.ioften.crm.model.invite.InviteDetail;
import net.yitun.ioften.crm.model.invite.InviteLoging;
import net.yitun.ioften.crm.model.invite.InviteMy;
import net.yitun.ioften.crm.model.invite.InviteQuery;
import net.yitun.ioften.crm.model.invite.MyInviteTotalDetail;
import net.yitun.ioften.crm.service.InviteService;

@Api(tags = "用户 邀请接口")
@RestController("crm.InviteApi")
@SuppressWarnings("unchecked")
public class InviteAction implements InviteApi {

    @Autowired
    protected InviteService service;

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "邀请码", notes = "获取自己的邀请码和邀请码二维图片在线地址")
    public Result<InviteMy> code() {
        return this.service.code();
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "邀请统计")
    public Result<MyInviteTotalDetail> myTotal() {
        MyInviteTotal myTotal = this.service.myTotal();
        if (null == myTotal)
            return Result.UNEXIST;

        return new Result<>(new MyInviteTotalDetail(myTotal.getTotal(),
                null == myTotal.getAwardSum() ? 0L : myTotal.getAwardSum(), myTotal.getCtime()));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "分页查询")
    public PageResult<InviteDetail> query(@Validated InviteQuery query) {
        Page<Invite> page = this.service.query(query);
        return new PageResult<>(page.getTotal(), page.getPages(), page.stream()
                .map(invite -> new InviteDetail(invite.getId(), invite.getUid(), invite.getFuid(), invite.getPhone(),
                        invite.getAward(), invite.getStatus(), invite.getCtime(), invite.getMtime(), invite.getInviteUser()))
                .collect(Collectors.toList()));
    }

    @Override
    @ApiOperation(value = "邀请登记")
    public Result<?> loging(@Validated @RequestBody InviteLoging model) {
        return this.service.loging(model);
    }

}
