package net.yitun.ioften.crm.model.user;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
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
 * <b>修改手机号.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年10月17日 下午4:37:04 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserPhoneModify implements Serializable {

    @NotNull(message = "新手机号无效")
    @ApiModelProperty(value = "手机号", required = true)
    @Pattern(regexp = Util.REGEX_PHONE, message = "手机号无效")
    protected String phone;

    @NotBlank(message = "短信验证码无效")
    @Pattern(regexp = "^\\d{4}$", message = "短信验证码长度为4个数字")
    @ApiModelProperty(value = "短信验证码, 长度为4个数字", required = true)
    protected String captcha;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
