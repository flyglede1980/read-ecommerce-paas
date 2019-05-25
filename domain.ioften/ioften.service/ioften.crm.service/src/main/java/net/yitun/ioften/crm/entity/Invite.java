package net.yitun.ioften.crm.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.crm.dicts.InviteStatus;
import net.yitun.ioften.crm.model.user.UserDetail;

/**
 * <pre>
 * <b>用户 邀请模型.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月19日 上午8:53:30 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Invite implements Serializable {

    @ApiModelProperty(value = "ID")
    protected Long id;

    @ApiModelProperty(value = "被邀请用户ID, 外键: crm_user.id")
    protected Long uid;

    @ApiModelProperty(value = "发起邀请用户ID, 外键: crm_user.id")
    protected Long fuid;

    @ApiModelProperty(value = "被邀请手机号, 长度为11位数字")
    protected String phone;

    @ApiModelProperty(value = "挖矿金额")
    protected Long award;

    @ApiModelProperty(value = "邀请状态: LOGING:仅登记; SIGNUP:已注册")
    protected InviteStatus status;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    @ApiModelProperty(value = "被邀请用户信息")
    protected UserDetail inviteUser;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public Invite(Long id) {
        super();
        this.id = id;
    }

    public Invite(Long id, Long uid, Long fuid, String phone, Long award, InviteStatus status, Date ctime, Date mtime) {
        super();
        this.id = id;
        this.uid = uid;
        this.fuid = fuid;
        this.phone = phone;
        this.award = award;
        this.status = status;
        this.ctime = ctime;
        this.mtime = mtime;
    }

}
