package net.yitun.ioften.crm.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.Code;
import net.yitun.basic.exp.BizException;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.comon.CobjsApi;
import net.yitun.comon.model.cobj.Cobj;
import net.yitun.ioften.crm.dicts.IdentityStatus;
import net.yitun.ioften.crm.dicts.Type;
import net.yitun.ioften.crm.entity.Identity;
import net.yitun.ioften.crm.entity.User;
import net.yitun.ioften.crm.entity.vo.IdentityQueryVo;
import net.yitun.ioften.crm.model.identity.IdentityApply;
import net.yitun.ioften.crm.model.identity.IdentityQuery;
import net.yitun.ioften.crm.model.identity.IdentityReview;
import net.yitun.ioften.crm.repository.IdentityRepository;
import net.yitun.ioften.crm.service.IdentityService;
import net.yitun.ioften.crm.service.UserService;

@Slf4j
@Service("sys.IdentityService")
public class IdentityServiceImpl implements IdentityService {

    @Autowired
    protected CobjsApi cobjsApi;

    @Autowired
    protected UserService userService;

    @Autowired
    protected IdentityRepository repository;

    @Override
    public Identity get(Long id) {
        Identity identity = this.repository.get(id);

        if (null != identity // 当用户提取认证详细信息时，屏蔽浏览非自己的认证信息
                && SecurityUtil.hasAnyRole("USER") && !SecurityUtil.getId().equals(identity.getUid()))
            return null;

        return identity;
    }

    @Override
    public Identity getByUid(Long uid) {
        return this.repository.findByUid(uid);
    }

    @Override
    public Page<Identity> query(IdentityQuery query) {
        IdentityQueryVo queryVo = new IdentityQueryVo(query.getId(), query.getUid(), query.getName(), query.getPhone(),
                query.getType(), query.getStatus());
        return this.repository.query(queryVo);
    }

    @Override
    @Transactional
    public Result<?> apply(IdentityApply apply) {
        Type type = apply.getType();
        long id = IdUtil.id(), uid = SecurityUtil.getId();
        Date nowTime = new Date(System.currentTimeMillis());

        String newEvidence = null;
        List<Cobj> cobjs = new ArrayList<>(3);
        Set<String> newEvidences = new LinkedHashSet<>(3);
        String prefix = "my/" + uid + "/identity/" + System.currentTimeMillis() + "_"; // 存储路径模板 my/uid/identity/tims_0
        for (String evidence : apply.getEvidences()) {
            newEvidences.add(newEvidence = prefix + cobjs.size());
            cobjs.add(new Cobj(evidence, newEvidence));
        }

        Identity identity = new Identity(id, uid, apply.getType(), apply.getName(), apply.getPhone(), apply.getCity(),
                apply.getIdcard(), StringUtils.join(newEvidences, ","), apply.getOperator(), apply.getCategoryId(),
                apply.getCategoryName(), apply.getSubCategoryId(), apply.getSubCategoryName(), null /* audit */,
                null /* auditId */, null /* auditTime */, null /* auditRemark */, IdentityStatus.NEW, nowTime, nowTime);

        Type _type = null;
        // 判断用户已认证的类型是否高于当前认证审批级别
        User user = this.userService.get(uid);
        if (0 > type.compareTo(_type = user.getType()))
            return new Result<>(Code.BIZ_ERROR, "不能降级认证");

        // 持久化新的认证申请记录
        if (type == _type //
                || !this.repository.create(identity))
            return new Result<>(Code.BIZ_ERROR, "重复新申请认证");

        // 保存上传的认证证件照片
        Result<?> result = null;
        if (null == (result = this.cobjsApi.sure(cobjs)) || !result.ok()) {
            if (log.isInfoEnabled())
                log.info("<<< {}.apply() sure cobj faild, {}", this.getClass().getName(), JsonUtil.toJson(result));
            throw new BizException(Code.BIZ_ERROR, "证件照片保存失败");
        }

        if (log.isInfoEnabled())
            log.info("<<< {}.apply() uid:{}, id:{}", this.getClass().getName(), uid, id);

        return Result.OK;
    }

    @Override
    @Transactional
    public Result<?> review(IdentityReview review) {
        Identity identity = null;
        long id = review.getId();
        if (null == (identity = this.repository.lock(id)))
            return Result.UNEXIST;

        else if (IdentityStatus.NEW != identity.getStatus())
            return new Result<>(Code.BIZ_ERROR, "该认证非申请状态");

        String modifier = SecurityUtil.getName();
        IdentityStatus status = review.getStatus();
        Date nowTime = new Date(System.currentTimeMillis());

        // 附加认证的信息
        identity.setMtime(nowTime);
        identity.setStatus(status);
        identity.setAuditId(SecurityUtil.getId());
        identity.setAudit(modifier);
        identity.setAuditRemark(review.getAuditRemark());
        identity.setAuditTime(nowTime);

        Long uid = identity.getUid();
        // 判断用户已认证的类型是否高于当前认证审批级别
        User user = this.userService.get(uid);
        if (0 > identity.getType().compareTo(user.getType()))
            return new Result<>(Code.BIZ_ERROR, "不能审批降级认证");

        // 持久化新的认证申请记录
        if (!this.repository.modify(identity))
            return new Result<>(Code.BIZ_ERROR, "审批处理保存失败");

        // 审批通过的同时调整用户的认证资料
        if (status == IdentityStatus.APPROVE //
                && !this.userService.modifyIdentity(identity))
            throw new BizException(Code.BIZ_ERROR, "用户认证资料更新失败");

        // TODO 站内消息下行告知用户认证审核结果

        if (log.isInfoEnabled())
            log.info("<<< {}.review() id:{}, status:{}, audit:{}", this.getClass().getName(), id, status, modifier);

        return Result.OK;
    }

}
