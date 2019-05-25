package net.yitun.ioften.order.entity.cards;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单基本信息
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
public class Orders {
    @ApiModelProperty(value = "订单编号")
    protected Long orderId;
    @ApiModelProperty(value = "购买人编号")
    protected Long userId;
    @ApiModelProperty(value = "联系人姓名")
    protected String contactPerson;
    @ApiModelProperty(value = "联系人电话")
    protected String telephone;
    @ApiModelProperty(value = "订单号")
    protected String orderNo;
    @ApiModelProperty(value = "数量")
    protected Integer quality;
    @ApiModelProperty(value = "订单金额")
    protected BigDecimal orderMoney;
    @ApiModelProperty(value = "成交金额")
    protected BigDecimal dealMoney;
    @ApiModelProperty(value = "下单渠道:1--线上支付;2--线下刷卡;3--线下现金")
    protected Integer orderChannel;
    @ApiModelProperty(value = "支付渠道:1--WAP(微信服务程序);2--Web页面;3--Android;4--IOS;5--微信小程序;6--代购;7--其它")
    protected Integer payChannel;
    @ApiModelProperty(value = "支付方式:1--微信支付;2--支付宝支付;3--银联支付;4--余额支付")
    protected Integer payMethod;
    @ApiModelProperty(value = "支付币种:1--SDT;2--SDC;3--RMB")
    protected Integer currency;
    @ApiModelProperty(value = "汇率")
    protected BigDecimal rate;
    @ApiModelProperty(value = "外部订单号")
    protected String foreignNo;
    @ApiModelProperty(value = "备注说明")
    protected String remark;
    @ApiModelProperty(value = "下单时间")
    protected Date orderTime;
    @ApiModelProperty(value = "创建时间")
    protected Date cTime;
    @ApiModelProperty(value = "修改时间")
    protected Date uTime;
    @ApiModelProperty(value = "订单二维码")
    protected String dimensionCode;
    @ApiModelProperty(value = "订单类型:1--会员卡订单;2--服务订单;3--商品订单;4--课程订单")
    protected Integer type;
    @ApiModelProperty(value = "是否有效:0--无效;1--有效")
    protected Integer isValid;
}
