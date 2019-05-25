package net.yitun.ioften.pay.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.dict.Status;

/**
 * <pre>
 * <b>支付 账户记录.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月10日 下午5:24:07
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月10日 下午5:24:07 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Wallet implements Serializable {

    @ApiModelProperty(value = "ID, 外键: crm_user.id")
    protected Long id;

    @ApiModelProperty(value = "积分赠送部分")
    protected Long give;

    @ApiModelProperty(value = "积分收益部分")
    protected Long income;

    @ApiModelProperty(value = "积分预存部分")
    protected Long balance;

    @ApiModelProperty(value = "累计积分收益, 范围: 0~1844 6744 0737.09551615")
    protected Long totalIncome;

    @ApiModelProperty(value = "累计RMB充值, 范围: 0~1844 6744 0737.09551615")
    protected Long totalAddedRmb;

    @ApiModelProperty(value = "贡献值")
    protected Long contribution;

    @ApiModelProperty(value = "区块地址")
    protected String blockAddr;

    @ApiModelProperty(value = "状态, 禁用; 正常")
    protected Status status;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public Wallet(Long id, Long income, Date mtime) {
        super();
        this.id = id;
        this.income = income;
        this.mtime = mtime;
    }

    public Wallet(Long id, Long give, Long income, Long balance, Long totalIncome, Date mtime) {
        super();
        this.id = id;
        this.give = give;
        this.income = income;
        this.balance = balance;
        this.totalIncome = totalIncome;
        this.mtime = mtime;
    }

}
