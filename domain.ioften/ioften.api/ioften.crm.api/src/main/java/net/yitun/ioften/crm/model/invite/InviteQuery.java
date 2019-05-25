package net.yitun.ioften.crm.model.invite;

import java.util.Collection;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.basic.model.Page;
import net.yitun.ioften.crm.dicts.InviteStatus;

/**
 * <pre>
 * <b>分页查询.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月19日 上午10:06:24 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
public class InviteQuery extends Page {

    @Min(value = Util.MIN_ID, message = "被邀请用户ID无效")
    @ApiModelProperty(value = "被邀请用户ID")
    protected Long id;

    @Min(value = Util.MIN_ID, message = "被邀请用户ID无效")
    @ApiModelProperty(value = "被邀请用户ID")
    protected Long uid;

    @ApiModelProperty(value = "被邀请手机号, 长度为11位数字")
    @Pattern(regexp = Util.REGEX_PHONE, message = "手机号无效")
    protected String phone;

    @Size(max = 16, message = "被邀请用户的昵称长度为2~16个字符")
    @ApiModelProperty(value = "被邀请用户的昵称, 长度为2~16个字符")
    protected String inviteNick;

    @ApiModelProperty(value = "邀请状态: LOGING:仅登记; SIGNUP:已注册")
    protected Collection<InviteStatus> status;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public InviteQuery() {
        // 排序规则, 默认: ID DESC
        super(null, null, null, "t1.id desc");
    }

}
