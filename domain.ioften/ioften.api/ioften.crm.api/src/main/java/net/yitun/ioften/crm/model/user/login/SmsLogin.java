package net.yitun.ioften.crm.model.user.login;

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
 * <b>短信登录.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月10日 下午4:03:07 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SmsLogin implements Serializable {

    @NotNull(message = "手机号无效")
    @Pattern(regexp = Util.REGEX_PHONE, message = "手机号无效")
    @ApiModelProperty(value = "手机号", required = true)
    protected String phone;

    @NotBlank(message = "验证码无效")
    @Pattern(regexp = "^\\d{4,6}$", message = "验证码长度为4~6个字符")
    @ApiModelProperty(value = "验证码, 长度为4~6个字符", required = true)
    protected String captcha;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
