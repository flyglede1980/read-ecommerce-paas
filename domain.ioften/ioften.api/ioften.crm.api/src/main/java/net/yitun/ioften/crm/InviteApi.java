package net.yitun.ioften.crm;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.ioften.crm.conf.Conf;
import net.yitun.ioften.crm.model.invite.InviteDetail;
import net.yitun.ioften.crm.model.invite.InviteLoging;
import net.yitun.ioften.crm.model.invite.InviteMy;
import net.yitun.ioften.crm.model.invite.InviteQuery;
import net.yitun.ioften.crm.model.invite.MyInviteTotalDetail;

/**
 * <pre>
 * <b>用户 邀请接口.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月19日 上午9:41:06 LH
 *         new file.
 * </pre>
 */
public interface InviteApi {

    /**
     * 邀请码
     * 
     * @return String 邀请码
     */
    @GetMapping(value = Conf.ROUTE //
            + "/invite", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<InviteMy> code();

    /**
     * 邀请统计
     * 
     * @return Result<MyInviteTotalDetail> 统计结果
     */
    @GetMapping(value = Conf.ROUTE //
            + "/invite/mytotal", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<MyInviteTotalDetail> myTotal();

    /**
     * 分页查询
     * 
     * @param query 查询参数
     * @return PageResult<InviteDetail> 分页结果
     */
    @GetMapping(value = Conf.ROUTE //
            + "/invites", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<InviteDetail> query(InviteQuery query);

    /**
     * 邀请登记
     * 
     * @param model 登记信息
     * @return Result<?> 登记结果
     */
    @PostMapping(value = Conf.ROUTE //
            + "/invite", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> loging(@RequestBody InviteLoging model);

}
