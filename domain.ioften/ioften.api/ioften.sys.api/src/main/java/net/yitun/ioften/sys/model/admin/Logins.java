package net.yitun.ioften.sys.model.admin;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <pre>
 * <b>密码登录.</b>
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
public class Logins implements Serializable {

    @NotBlank(message = "账号无效")
    @Size(min = 2, max = 16, message = "账号长度为2~16个字符")
    @ApiModelProperty(value = "账号, 长度为2~16个字符", required = true)
    protected String account;

    @NotBlank(message = "密码无效")
    @Size(min = 6, max = 32, message = "密码长度为6~32个数字或字母")
    @ApiModelProperty(value = "密码, 长度为6~32个数字或字母", required = true)
    protected String passwd;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
