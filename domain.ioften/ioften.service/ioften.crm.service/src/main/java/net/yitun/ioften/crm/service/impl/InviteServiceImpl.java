package net.yitun.ioften.crm.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.Code;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.IdUtil;
import net.yitun.ioften.crm.dicts.InviteStatus;
import net.yitun.ioften.crm.entity.Invite;
import net.yitun.ioften.crm.entity.User;
import net.yitun.ioften.crm.entity.vo.invite.InviteQueryVo;
import net.yitun.ioften.crm.entity.vo.invite.MyInviteTotal;
import net.yitun.ioften.crm.model.invite.InviteLoging;
import net.yitun.ioften.crm.model.invite.InviteMy;
import net.yitun.ioften.crm.model.invite.InviteQuery;
import net.yitun.ioften.crm.repository.InviteRepository;
import net.yitun.ioften.crm.service.ConfService;
import net.yitun.ioften.crm.service.InviteService;
import net.yitun.ioften.crm.service.UserService;
import net.yitun.ioften.crm.support.InviteSupport;

@Slf4j
@Service("sys.InviteService")
public class InviteServiceImpl implements InviteService {

    @Autowired
    protected ConfService confService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected InviteRepository repository;

    @Override
    public Result<InviteMy> code() {
        long id = SecurityUtil.getId();
        User user = this.userService.get(id);
        if (null == user) // 账号信息不存在，直接忽略
            return new Result<>(Code.NOT_FOUND, "账号信息不存在");

        String code = null;
        // 如果当前用户还未生生成过邀请
        if (StringUtils.isBlank(code = user.getInvite())) {
            int time = 0, maxTime = 16;
            for (time = 0; time < maxTime; time++) { // 则自动分配(注意防止重复), 尝试16次去重并更新到用户资料上
                code = InviteSupport.genCode(6);
                if (this.userService.modifyInvite(code).ok()) // 邀请码更新成功
                    break;
            }

            if (time > maxTime)
                return new Result<>(Code.BIZ_ERROR, "邀请码生成失败");
        }

        // 是否对要邀请码就行文案格式化
        String nick = user.getNick();
        String content = this.confService.buildInviteContent(nick);

        return new Result<>(new InviteMy(user.getId(), user.getNick(), code, content));
    }

    @Override
    public MyInviteTotal myTotal() {
        long uid = SecurityUtil.getId();
        MyInviteTotal myTotal = this.repository.myTotal(uid, InviteStatus.SIGNUP); // 仅统计被邀请用户已注册状态
        if (null != myTotal)
            myTotal.setCtime(new Date(System.currentTimeMillis()));
        return myTotal;
    }

    @Override
    public Page<Invite> query(InviteQuery query) {
        long fuid = SecurityUtil.getId(); // 发起邀请的用户ID
        InviteQueryVo queryVo = new InviteQueryVo(query.getId(), query.getUid(), fuid, //
                query.getPhone(), query.getInviteNick(), query.getStatus());
        return this.repository.query(queryVo);
    }

    @Override
    @Transactional
    public Result<?> loging(InviteLoging loging) {
        User user = null;
        // 判断邀请码是否有效
        String code = loging.getCode();
        if (null == (user = this.userService.getByInvite(code)))
            return new Result<>(Code.BIZ_ERROR, "邀请码无效");

        // 判断邀请码对应的手机号就是当前登记的手机号
        String phone = loging.getPhone();
        if (user.getPhone().equals(phone))
            return new Result<>(Code.BIZ_ERROR, "不能自己邀请自己");

        // 判断当前邀请手机号是否已是注册用户
        if (null != this.userService.getByPhone(phone))
            return new Result<>(Code.BIZ_ERROR, "该手机号已是注册用户");

        long id = IdUtil.id(), fuid = user.getId(), award = 0L;
        Date nowTime = new Date(System.currentTimeMillis());
        Invite invite = new Invite(id, null /* uid */, fuid, phone, award, InviteStatus.LOGING, nowTime, nowTime);

        this.repository.create(invite); // 持久化邀请登记信息

        if (log.isInfoEnabled())
            log.info("<<< {}.loging() code:{} phone:{}", this.getClass().getName(), code, phone);

        return Result.OK;
    }

}
