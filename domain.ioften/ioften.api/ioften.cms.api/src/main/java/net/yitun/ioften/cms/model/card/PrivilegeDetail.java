package net.yitun.ioften.cms.model.card;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 特权信息对外业务实体
 * @since 1.0.0
 * @see Data
 * @see ToString
 * @see AllArgsConstructor
 * @see NoArgsConstructor
 * @see EqualsAndHashCode
 * @author Flyglede
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"privilegeId"})
public class PrivilegeDetail implements Serializable {
    @ApiModelProperty(value = "特权编号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
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