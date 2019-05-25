package net.yitun.ioften.crm.model.identity;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.Min;
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
import net.yitun.ioften.crm.dicts.Type;

/**
 * <pre>
 * <b>认证申请.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月13日 下午3:41:06 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IdentityApply implements Serializable {

    @NotNull(message = "类型无效")
    @ApiModelProperty(value = "类型, N:未知; PE:个人, EN:企业, EW:长见号", required = true)
    protected Type type;

    @NotBlank(message = "实名无效")
    @Size(min = 2, max = 32, message = "实名长度为2~32个字符")
    @ApiModelProperty(value = "实名, 长度为2~32个字符", required = true)
    protected String name;

    @NotNull(message = "手机号无效")
    @Pattern(regexp = Util.REGEX_PHONE, message = "手机号无效")
    @ApiModelProperty(value = "手机号", required = true)
    protected String phone;

    @Size(min = 2, max = 16, message = "城市长度为2~16个字符")
    @ApiModelProperty(value = "城市, 长度为2~16个字符")
    protected String city;

    @NotBlank(message = "证件号无效")
    @Size(min = 8, max = 18, message = "证件号长度为8~18个字符")
    @ApiModelProperty(value = "证件号, 长度为8~18个字符", required = true)
    protected String idcard;

    @NotNull(message = "证件照无效")
    @Size(min = 1, max = 3, message = "证件照无效")
    @ApiModelProperty(value = "证件照, 多个逗号分隔", required = true)
    protected Set<String> evidences;

    @Size(min = 2, max = 16, message = "运营者, 仅企业, 长见号, 长度为2~16个字符")
    @ApiModelProperty(value = "运营者, 仅企业, 长见号, 长度为2~16个字符")
    protected String operator;

    @Min(value = Util.MIN_ID, message = "类目ID无效")
    @ApiModelProperty(value = "类目ID", required = true)
    protected Long categoryId;

    @Size(min = 2, max = 16, message = "类目名称长度为2~16个字符")
    @ApiModelProperty(value = "类目名称, 长度为2~16个字符", required = true)
    protected String categoryName;

    @Min(value = Util.MIN_ID, message = "子类目ID无效")
    @ApiModelProperty(value = "子类目ID", required = true)
    protected Long subCategoryId;

    @Size(min = 2, max = 16, message = "子类目名称长度为2~16个字符")
    @ApiModelProperty(value = "子类目名称, 长度为2~16个字符", required = true)
    protected String subCategoryName;

    @Size(min = 4, max = 6, message = "验证码长度为4~6个字符")
    @ApiModelProperty(value = "验证码, 长度为4~6个字符", required = true)
    protected String captcha;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
