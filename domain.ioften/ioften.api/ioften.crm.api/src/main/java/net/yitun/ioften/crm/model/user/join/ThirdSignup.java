package net.yitun.ioften.crm.model.user.join;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.Util;

/**
 * <pre>
 * <b>第三方注册.</b>
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
public class ThirdSignup implements Serializable {

    @NotBlank(message = "第三方无效")
    @ApiModelProperty(value = "第三方", required = true)
    protected String thrid;

    @NotBlank(message = "第三方ID无效")
    @ApiModelProperty(value = "第三方ID", required = true)
    protected String openid;

    @NotNull(message = "手机号无效")
    @Pattern(regexp = Util.REGEX_PHONE, message = "手机号无效")
    @ApiModelProperty(value = "手机号", required = true)
    protected String phone;

    @NotBlank(message = "密码无效")
    @Size(min = 6, max = 32, message = "密码长度为6~32个数字或字母")
    @ApiModelProperty(value = "密码, 长度为6~32个数字或字母", required = true)
    protected String passwd;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
