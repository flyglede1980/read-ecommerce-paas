package net.yitun.ioften.pay.model.wallet;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.ioften.pay.dicts.Channel;
import net.yitun.ioften.pay.dicts.Currency;
import net.yitun.ioften.pay.dicts.Way;

/**
 * <pre>
 * <b>支付 帐户调账.</b>
 * <b>Description:</b>
 *    主要用于单方内部支付入账
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月14日 上午9:52:20
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月14日 上午9:52:20 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WalletAdjust implements Serializable {

    @NotNull(message = "用户ID无效")
    @Min(value = Util.MIN_ID, message = "用户ID无效")
    @ApiModelProperty(value = "用户ID", required = true)
    protected Long uid;

    @NotNull(message = "流水用途无效")
    @ApiModelProperty(value = "流水用途: N:其他; VIEW:阅读; SHARE:分享; ENJOY:点赞; INVITE:邀请; GIVEIN:获得打赏; GIVEOUT:打赏他人; ADVIN:广告收入; AWARDOUT:设置奖励 SHOP:购物; FETCH:提币; ADDED:充值", required = true)
    protected Way way;

    @NotNull(message = "金额无效")
    @ApiModelProperty(value = "金额, 存储时将其小数点往后移8位, 用整数表示, 支持±", required = true)
    protected Long amount;

    @NotNull(message = "币种无效")
    @ApiModelProperty(value = "币种类型, N:其他, RMB, SDC, SDT", required = true)
    protected Currency currency;

    @NotNull(message = "交易对象无效")
    @ApiModelProperty(value = "交易对象, 如文章ID,订单ID, 无交易对象默认:0", required = true)
    protected Long target;

    @NotNull(message = "支付通道无效")
    @ApiModelProperty(value = "支付通道, N:其他; APP:站内; BANK:银联; BLOCK:区块; ALIPAY:支付宝; WXPAY:微信支付", required = true)
    protected Channel channel;

    @ApiModelProperty(value = "外部流水号, 长度: 0~64个字符", required = false)
    protected String outFlows;

    @ApiModelProperty(value = "备注信息, 文章名, 用户名, 商品清单, 长度: 0~64个字符", required = false)
    protected String remark;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
