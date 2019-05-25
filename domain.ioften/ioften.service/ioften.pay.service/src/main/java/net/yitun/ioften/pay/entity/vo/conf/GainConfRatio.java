package net.yitun.ioften.pay.entity.vo.conf;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
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
@NoArgsConstructor
public class GainConfRatio implements Serializable {

    @ApiModelProperty(value = "系数, 比如: 0.9")
    protected Float ratio;

    @ApiModelProperty(value = "金额, 比如: 50000000000=500￥")
    protected Long amount;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public GainConfRatio(Long amount, Float ratio) {
        super();
        this.ratio = ratio;
        this.amount = amount;
    }

}
