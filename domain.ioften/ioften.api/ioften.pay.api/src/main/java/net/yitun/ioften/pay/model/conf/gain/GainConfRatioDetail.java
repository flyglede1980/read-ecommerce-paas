package net.yitun.ioften.pay.model.conf.gain;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <pre>
 * <b>挖矿充值系数.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月8日 下午4:37:33
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月8日 下午4:37:33 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GainConfRatioDetail {

    @NotNull(message = "C系数无效")
    @Min(value = 0L, message = "C系数无效")
    @ApiModelProperty(value = "系数, 比如: 0.9")
    protected Float ratio;

    @NotNull(message = "C充值金额无效")
    @Min(value = 0L, message = "C充值金额无效")
    @ApiModelProperty(value = "充值金额, 比如: 50000000000=500￥")
    protected Long amount;

    public GainConfRatioDetail(Long amount, Float ratio) {
        super();
        this.ratio = ratio;
        this.amount = amount;
    }

}
