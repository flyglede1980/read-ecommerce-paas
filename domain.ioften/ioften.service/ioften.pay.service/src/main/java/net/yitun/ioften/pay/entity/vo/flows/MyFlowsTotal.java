package net.yitun.ioften.pay.entity.vo.flows;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <pre>
 * <b>我的支付流水合计.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月8日 下午6:15:46
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月8日 下午6:15:46 LH
 *         new file.
 * </pre>
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyFlowsTotal implements Serializable {

    @ApiModelProperty(value = "合计")
    protected Long sum;

    @ApiModelProperty(value = "支出合计")
    protected Long outlay;

    @ApiModelProperty(value = "收益合计")
    protected Long income;

    @ApiModelProperty(value = "统计时间")
    protected Date ctime;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public void setOutlay(Long outlay) {
        this.outlay = outlay;
        if (null != this.outlay && null != this.income)
            this.sum = this.outlay + this.income;
    }

    public void setIncome(Long income) {
        this.income = income;
        if (null != this.outlay && null != this.income)
            this.sum = this.outlay + this.income;
    }

}
