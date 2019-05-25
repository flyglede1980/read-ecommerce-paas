package net.yitun.ioften.crm.model.invite;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.Util;

/**
 * <pre>
 * <b>邀请登记.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月28日 上午11:48:24 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InviteLoging implements Serializable {

    @NotEmpty(message = "邀请码无效")
    @Pattern(regexp = "^\\w{6}$", message = "邀请码无效")
    @ApiModelProperty(value = "邀请码, 长度为6个数字、字符组成", required = true)
    protected String code;

    @NotNull(message = "邀请手机号无效")
    @Pattern(regexp = Util.REGEX_PHONE, message = "手机号无效")
    @ApiModelProperty(value = "邀请手机号", required = true)
    protected String phone;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
