package net.yitun.ioften.crm.model.invite;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <pre>
 * <b>我的邀请码.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月19日 下午7:56:46 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InviteMy implements Serializable {

    @ApiModelProperty(value = "用户ID")
    protected Long uid;

    @ApiModelProperty(value = "用户昵称")
    protected String nick;

    @ApiModelProperty(value = "邀请代码")
    protected String code;

    @ApiModelProperty(value = "邀请内容")
    protected String content;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
