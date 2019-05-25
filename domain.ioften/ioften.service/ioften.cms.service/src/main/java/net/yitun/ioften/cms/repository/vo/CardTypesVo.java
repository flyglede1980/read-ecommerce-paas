package net.yitun.ioften.cms.repository.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 卡种信息查询(与会员卡独立)
 * @since 1.0.0
 * @see ApiModelProperty
 * @see AllArgsConstructor
 * @see Data
 * @see NoArgsConstructor
 * @see ToString
 * @author Flyglede
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CardTypesVo {
    @ApiModelProperty(value = "卡种编号")
    protected Long cardTypeId;
    @ApiModelProperty(value = "卡种名称")
    protected String name;
    @ApiModelProperty(value = "卡种单价")
    protected BigDecimal price;
    @ApiModelProperty(value = "有效期,单位为月")
    protected Integer period;
    @ApiModelProperty(value = "启用状态:0--禁用;1--启用")
    protected Integer status;
    @ApiModelProperty(value = "创建时间")
    protected Date cTime;
    @ApiModelProperty(value = "变更时间")
    protected Date uTime;
}
