package net.yitun.ioften.order.model.cardorder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 会员卡订单信息创建模型
 * @since 1.0.0
 * @see Data
 * @see ToString
 * @see AllArgsConstructor
 * @see NoArgsConstructor
 * @see ApiModelProperty
 * @see JsonFormat
 * @see JSONField
 * @author Flyglede
 * @data 2019-01-11
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CardOrderCreate {
    @ApiModelProperty(value = "账户编号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long userId;
    @ApiModelProperty(value = "卡种编号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    protected Long cardTypeId;
    @ApiModelProperty(value = "数量")
    protected Integer quality;
    @ApiModelProperty(value = "订单金额")
    protected BigDecimal orderMoney;
    @ApiModelProperty(value = "支付渠道:1--WAP(微信服务程序);2--Web页面;3--Android;4--IOS;5--微信小程序;6--代购;7--其它")
    protected Integer payChannel;
}