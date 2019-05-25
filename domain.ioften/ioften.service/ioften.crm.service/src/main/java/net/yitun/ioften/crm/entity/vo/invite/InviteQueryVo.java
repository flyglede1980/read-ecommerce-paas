package net.yitun.ioften.crm.entity.vo.invite;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.utils.SetUtil;
import net.yitun.ioften.crm.dicts.InviteStatus;
import net.yitun.ioften.crm.entity.Invite;

/**
 * <pre>
 * <b>用户 邀请查询Vo.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月19日 上午8:59:24 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
public class InviteQueryVo extends Invite {

    @ApiModelProperty(value = "被邀请用户的昵称, 长度为2~16个字符")
    protected String inviteNick;

    @ApiModelProperty(value = "邀请状态: LOGING:仅登记; SIGNUP:已注册")
    protected Collection<InviteStatus> statuss;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public InviteQueryVo(Long id, Long uid, Long fuid, String phone, String inviteNick, Collection<InviteStatus> statuss) {
        super(id, uid, fuid, phone, null, null, null, null);
        this.setPhone(phone);
        this.setInviteNick(inviteNick);
        this.statuss = null != statuss ? statuss : SetUtil.asSet(InviteStatus.SIGNUP);
    }

    public void setInviteNick(String inviteNick) {
        this.inviteNick = StringUtils.trimToNull(inviteNick);
        if (null != this.inviteNick)
            this.inviteNick += "%";
    }

}
