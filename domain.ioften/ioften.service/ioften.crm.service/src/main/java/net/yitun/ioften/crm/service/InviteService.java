package net.yitun.ioften.crm.service;

import com.github.pagehelper.Page;

import net.yitun.basic.model.Result;
import net.yitun.ioften.crm.entity.Invite;
import net.yitun.ioften.crm.entity.vo.invite.MyInviteTotal;
import net.yitun.ioften.crm.model.invite.InviteLoging;
import net.yitun.ioften.crm.model.invite.InviteMy;
import net.yitun.ioften.crm.model.invite.InviteQuery;

/**
 * <pre>
 * <b>用户 邀请服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月19日 上午9:16:36 LH
 *         new file.
 * </pre>
 */
public interface InviteService {

    /**
     * 邀请码
     * 
     * @return String
     */
    Result<InviteMy> code();

    /**
     * 邀请统计
     * 
     * @return MyInviteTotal 统计结果
     */
    MyInviteTotal myTotal();

    /**
     * 分页查询
     * 
     * @param query 筛选参数
     * @return Page<Invite> 分页结果
     */
    Page<Invite> query(InviteQuery query);

    /**
     * 邀请登记
     * 
     * @param loging 邀请登记
     * @return Result<?> 邀请结果
     */
    Result<?> loging(InviteLoging loging);

}
