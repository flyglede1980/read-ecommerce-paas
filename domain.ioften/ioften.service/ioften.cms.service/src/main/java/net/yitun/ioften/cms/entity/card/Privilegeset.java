package net.yitun.ioften.cms.entity.card;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 特权设置信息(与会员卡独立)
 * @since 1.0.0
 * @see ApiModelProperty
 * @see AllArgsConstructor
 * @see Data
 * @see NoArgsConstructor
 * @see ToString
 * @author Flyglede
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Privilegeset {
    @ApiModelProperty(value = "特权编号")
    protected Long privilegeId;
    @ApiModelProperty(value = "特权项")
    protected String name;
    @ApiModelProperty(value = "特权代码")
    protected String code;
    @ApiModelProperty(value = "特权类型:1--数值设置;2--开关")
    protected Integer type;
    @ApiModelProperty(value = "系数")
    protected BigDecimal coefficient;
    @ApiModelProperty(value = "计量单位:1--服豆/天;2--倍;3--次/天;4--个/人;5--个/元")
    protected String unit;
    @ApiModelProperty(value = "提示信息")
    protected String tips;
}