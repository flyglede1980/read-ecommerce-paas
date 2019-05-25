package net.yitun.ioften.order.entity.cards;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;

/**
 * 会员卡订单信息
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
public class CardOrders {
    @ApiModelProperty(value = "订单编号")
    protected Long orderId;
    @ApiModelProperty(value = "卡种编号")
    protected Long cardTypeId;
    @ApiModelProperty(value = "订单状态:1--待支付;2--已生效;3--已过期")
    protected Integer status;
}