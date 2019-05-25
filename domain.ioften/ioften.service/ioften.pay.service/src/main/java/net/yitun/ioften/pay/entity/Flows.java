package net.yitun.ioften.pay.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.dict.Status;
import net.yitun.ioften.pay.dicts.Channel;
import net.yitun.ioften.pay.dicts.Currency;
import net.yitun.ioften.pay.dicts.Way;

/**
 * <pre>
 * <b>支付 流水记录.</b>
 * <b>Description:</b>
 *
 * <b>Author:</b> ZH
 * <b>Date:</b> 2018-12-07 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月5日 下午5:38:46 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Flows implements Serializable {

    @ApiModelProperty(value = "ID")
    protected Long id;

    @ApiModelProperty(value = "UID")
    protected Long uid;

    @ApiModelProperty(value = "流水用途: N:其他; VIEW:阅读; SHARE:分享; ENJOY:点赞; INVITE:邀请; GIVEIN:获得打赏; GIVEOUT:打赏他人; ADVIN:广告收入; AWARDOUT:设置奖励 SHOP:购物; FETCH:提币; ADDED:充值")
    protected Way way;

    @ApiModelProperty(value = "金额, 存储时将其小数点往后移8位, 用整数表示, 支持±")
    protected Long amount;

    @ApiModelProperty(value = "币种类型, N:其他, RMB, SDC, SDT")
    protected Currency currency;

    @ApiModelProperty(value = "交易对象, 如文章ID,订单ID, 无交易对象默认:0")
    protected Long target;

    @ApiModelProperty(value = "支付通道, N:其他; APP:站内; BANK:银联; BLOCK:区块; ALIPAY:支付宝; WXPAY:微信支付")
    protected Channel channel;

    @ApiModelProperty(value = "外部流水号")
    protected String outFlows;

    @ApiModelProperty(value = "积分赠送部分结余")
    protected Long newGive;

    @ApiModelProperty(value = "积分收益部分结余")
    protected Long newIncome;

    @ApiModelProperty(value = "积分预存部分结余")
    protected Long newBalance;

    @ApiModelProperty(value = "备注信息, 文章名, 用户名, 商品清单")
    protected String remark;

    @ApiModelProperty(value = "状态, 交易中; 交易失败; 交易取消; 交易成功")
    protected Status status;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
