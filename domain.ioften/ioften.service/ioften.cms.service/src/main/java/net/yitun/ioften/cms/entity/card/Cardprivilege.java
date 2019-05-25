package net.yitun.ioften.cms.entity.card;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 卡种特权(卡种与特权之间的关系)
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
public class Cardprivilege {
    @ApiModelProperty(value = "卡种与特权关系编号")
    protected Long cardPrivilegeId;
    @ApiModelProperty(value = "卡种编号")
    protected Long cardTypeId;
    @ApiModelProperty(value = "特权编号")
    protected Long privilegeId;
    @ApiModelProperty(value = "是否开启:0--关闭;1--开启")
    protected Integer isSwitch;
    @ApiModelProperty(value = "特权值")
    protected Integer privilegeValue;
}