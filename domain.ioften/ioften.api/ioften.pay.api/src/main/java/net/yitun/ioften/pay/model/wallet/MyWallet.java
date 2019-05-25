package net.yitun.ioften.pay.model.wallet;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.crm.dicts.Level;

/**
 * <pre>
 * <b>支付 账户总览.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月10日 下午5:01:24
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月10日 下午5:01:24 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyWallet implements Serializable {

    @ApiModelProperty(value = "服豆合计")
    protected Long sum;

    @ApiModelProperty(value = "服豆合计现金")
    protected Long sumCash;

    @ApiModelProperty(value = "累计服豆")
    protected Long totalIncome;

    @ApiModelProperty(value = "累计服豆现金")
    protected Long totalIncomeCash;

    @ApiModelProperty(value = "昨日服豆收益")
    protected Long yesterdayIncome;

    @ApiModelProperty(value = "昨日服豆收益现金")
    protected Long yesterdayIncomeCash;

    @ApiModelProperty(value = "当前等级")
    protected Level level;

    @ApiModelProperty(value = "当前等级倍数")
    protected Float levelMultiple;

    @ApiModelProperty(value = "下一级等级, 已是最好级时与当前等级相同")
    protected Level nextLevel;

    @ApiModelProperty(value = "下一等级倍数, 已是最好级时与当前等级倍数相同")
    protected Float nextLevelMultiple;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
