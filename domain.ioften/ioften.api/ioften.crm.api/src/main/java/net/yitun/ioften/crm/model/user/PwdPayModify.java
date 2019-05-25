package net.yitun.ioften.crm.model.user;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <pre>
 * <b>修改支付密码.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年10月17日 下午4:36:45 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PwdPayModify implements Serializable {

    @NotBlank(message = "短信验证码无效")
    @Pattern(regexp = "^\\d{4}$", message = "短信验证码长度为4个数字")
    @ApiModelProperty(value = "短信验证码, 长度为4个数字", required = true)
    protected String captcha;

    @NotBlank(message = "新支付密码无效")
    @Size(min = 6, max = 32, message = "新支付新密码长度为6~32个数字或字母")
    @ApiModelProperty(value = "新支付新密码, 长度为6~32个数字或字母", required = true)
    protected String payPasswd;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
