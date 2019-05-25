package net.yitun.ioften.cms.model.card;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

import java.util.List;

/**
 * 卡种信息创建模型
 * @since 1.0.0
 * @see Data
 * @see ToString
 * @see AllArgsConstructor
 * @see NoArgsConstructor
 * @see NotBlank
 * @see Size
 * @see ApiModelProperty
 * @author Flyglede
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CardTypeCreate  implements Serializable {
    @NotBlank(message = "卡种名称不能为空")
    @Size(min = 2, max = 50, message = "卡种名称, 长度为2-50个字符")
    @ApiModelProperty(value = "卡种名称, 长度为2-50个字符", required = true)
    protected String name;
    @NotNull(message = "卡种单价不能为空并且只能为数值(整数部分不超过10位,小数部分不超过2位)")
    @Digits(integer = 10, fraction = 2)
    @ApiModelProperty(value = "卡种单价,长度不超过15个字符", required = true)
    protected BigDecimal price;
    @ApiModelProperty(value = "卡种图标,必须上传图片", required = true)
    protected String cardIcon;
    @ApiModelProperty(value = "特权描述图标,必须上传图片", required = true)
    protected String privilegeIcon;
    @ApiModelProperty(value = "身份图标,必须上传图片", required = true)
    protected String identityIcon;
    @ApiModelProperty(value = "有效期,单位为月,必须为大于0的整数", required = true)
    protected Integer period;
    @ApiModelProperty(value = "会员卡特权列表")
    protected List<CardPrivilegeCreate> lstCardPrivilege;
}