package net.yitun.ioften.crm.website.action;

import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
import net.yitun.basic.utils.extra.IdCardUtil;
import net.yitun.comon.CodeApi;
import net.yitun.ioften.crm.IdentityApi;
import net.yitun.ioften.crm.dicts.IdentityStatus;
import net.yitun.ioften.crm.dicts.Type;
import net.yitun.ioften.crm.entity.Identity;
import net.yitun.ioften.crm.model.identity.IdentityApply;
import net.yitun.ioften.crm.model.identity.IdentityDetail;
import net.yitun.ioften.crm.model.identity.IdentityQuery;
import net.yitun.ioften.crm.model.identity.IdentityReview;
import net.yitun.ioften.crm.service.IdentityService;

@Api(tags = "用户 认证接口")
@RestController("crm.IdentityApi")
@SuppressWarnings("unchecked")
public class IdentityAction implements IdentityApi {

    @Autowired
    protected CodeApi codeApi;

    @Autowired
    protected IdentityService service;

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "简要信息")
    public Result<IdentityDetail> info() {
        long uid = SecurityUtil.getId();

        Identity identity = null;
        if (null == (identity = this.service.getByUid(uid)))
            return new Result<>(Code.NOT_FOUND, "您还未提交过认证");

        return new Result<>(new IdentityDetail(identity.getId(), identity.getUid(), identity.getType(),
                identity.getName(), identity.getPhone(), identity.getCity(), identity.getIdcard(),
                identity.getEvidences(), identity.getOperator(), identity.getCategoryId(), identity.getCategoryName(),
                identity.getSubCategoryId(), identity.getSubCategoryName(), identity.getAudit(), identity.getAuditId(),
                identity.getAuditTime(), identity.getAuditRemark(), identity.getStatus(), identity.getCtime(),
                identity.getMtime()));
    }

    @Override
    @ApiOperation(value = "详细信息")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @ApiImplicitParam(name = "id", value = "ID", required = true)
    public Result<IdentityDetail> detail(@PathVariable("id") Long id) {
        if (null == id || IdUtil.MIN > id)
            return new Result<>(Code.BAD_REQ, "ID无效");

        Identity identity = null;
        if (null == (identity = this.service.get(id)))
            return Result.UNEXIST;

        return new Result<>(new IdentityDetail(identity.getId(), identity.getUid(), identity.getType(),
                identity.getName(), identity.getPhone(), identity.getCity(), identity.getIdcard(),
                identity.getEvidences(), identity.getOperator(), identity.getCategoryId(), identity.getCategoryName(),
                identity.getSubCategoryId(), identity.getSubCategoryName(), identity.getAudit(), identity.getAuditId(),
                identity.getAuditTime(), identity.getAuditRemark(), identity.getStatus(), identity.getCtime(),
                identity.getMtime()));
    }

    @Override
    @ApiOperation(value = "分页查询")
    @PreAuthorize("hasRole('ADMIN')")
    public PageResult<IdentityDetail> query(@Validated IdentityQuery query) {
        Page<Identity> page = this.service.query(query);
        return new PageResult<>(page.getTotal(), page.getPages(),
                page.stream().map(identity -> new IdentityDetail(identity.getId(), identity.getUid(),
                        identity.getType(), identity.getName(), identity.getPhone(), identity.getCity(),
                        identity.getIdcard(), identity.getEvidences(), identity.getOperator(), identity.getCategoryId(),
                        identity.getCategoryName(), identity.getSubCategoryId(), identity.getSubCategoryName(),
                        identity.getAudit(), identity.getAuditId(), identity.getAuditTime(), identity.getAuditRemark(),
                        identity.getStatus(), identity.getCtime(), identity.getMtime())).collect(Collectors.toList()));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "认证申请")
    public Result<?> apply(@Validated @RequestBody IdentityApply apply) {
        // 判断认证信息是否完备
        Type type = apply.getType();
        if (type == Type.PE) { // 个人认证
            if (3 != apply.getEvidences().size()) // 必须三张证件照
                return new Result<>(Code.BAD_REQ, "证件照无效");

            if (!IdCardUtil.validate(apply.getIdcard()))
                return new Result<>(Code.BAD_REQ, "证件号无效");

        } else if (type == Type.EN || type == Type.EW) { // 企业，长见类认证
            if (1 != apply.getEvidences().size()) // 仅一张证件照
                return new Result<>(Code.BAD_REQ, "证件照无效");

            if (StringUtils.isBlank(apply.getCity()) //
                    || 1 >= apply.getCity().length() || 16 < apply.getCity().length())
                return new Result<>(Code.BAD_REQ, "城市无效");

            if (StringUtils.isBlank(apply.getOperator()) //
                    || 1 >= apply.getOperator().length() || 16 < apply.getOperator().length())
                return new Result<>(Code.BAD_REQ, "运营者无效");

            String phone = apply.getPhone(), captcha = apply.getCaptcha(); // 验证验证码
            if (!this.codeApi.match(net.yitun.comon.dicts.Type.SMS, phone, captcha).ok())
                return new Result<>(Code.BAD_REQ, "短信验证码错误");

            if (Type.EW == apply.getType() //
                    && (null == apply.getCategoryId() || IdUtil.MIN >= apply.getCategoryId() //
                            || null == apply.getSubCategoryId() || IdUtil.MIN >= apply.getSubCategoryId()))
                return new Result<>(Code.BAD_REQ, "类目无效");

        } else
            return new Result<>(Code.BAD_REQ, "认证类型无效");

        // 调用内部服务处理申请业务
        return this.service.apply(apply);
    }

    @Override
    @ApiOperation(value = "认证审批")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<?> review(@Validated @RequestBody IdentityReview review) {
        // 判断审批意见是否有效
        if (IdentityStatus.REFUSE != review.getStatus() //
                && IdentityStatus.APPROVE != review.getStatus())
            return new Result<>(Code.BAD_REQ, "审批意见无效");

        // 拒绝时，需要输入拒绝原因
        if (IdentityStatus.REFUSE == review.getStatus() //
                && StringUtils.isBlank(review.getAuditRemark()))
            return new Result<>(Code.BAD_REQ, "拒绝原因无效");

        // 调用内部服务处理审核业务
        return this.service.review(review);
    }

}
