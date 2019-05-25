package net.yitun.ioften.pay.model.conf.gain;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <pre>
 * <b>挖矿配置详细.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月8日 下午5:06:29
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月8日 下午5:06:29 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GainConfDetail implements Serializable {

    @NotNull(message = "B手续费比率无效")
    @Min(value = 0L, message = "B手续费比率")
    @ApiModelProperty(value = "B手续费比率, 比如: 0.1, 默认: 0%")
    protected Float handFee;

    @NotNull(message = "阅读挖矿时限无效")
    @Min(value = 0L, message = "阅读挖矿时限无效")
    @Max(value = 60L, message = "阅读挖矿时限无效")
    @ApiModelProperty(value = "阅读挖矿时限0~60秒, 0:代表关闭阅读挖矿, 默认: 15s")
    protected Long viewTime;

    @NotNull(message = "邀请挖矿基数无效")
    @Min(value = 0L, message = "邀请挖矿基数无效")
    @ApiModelProperty(value = "邀请挖矿基数, 比如：100000000=1￥, 默认: 1￥")
    protected Long inviteCardinal;

    @NotNull(message = "A阅读挖矿基数无效")
    @Size(min = 2, max = 2, message = "A阅读挖矿基数无效")
    @ApiModelProperty(value = "A阅读挖矿基数, 比如：20000000~30000000=0.2~0.3, 默认: 0.2~0.3￥")
    protected Long[] viewCardinals;

    @NotNull(message = "A点赞挖矿基数无效")
    @Size(min = 2, max = 2, message = "A点赞挖矿基数无效")
    @ApiModelProperty(value = "A点赞挖矿基数, 比如：20000000~30000000=0.2~0.3, 默认: 0.4~0.6￥")
    protected Long[] enjoyCardinals;

    @NotNull(message = "A分享挖矿基数无效")
    @Size(min = 2, max = 2, message = "A分享挖矿基数无效")
    @ApiModelProperty(value = "A分享挖矿基数, 比如：20000000~30000000=0.2~0.3, 默认: 0.5~1.0￥")
    protected Long[] shareCardinals;

    @NotNull(message = "C充值系数无效")
    @Size(min = 1, max = 1024, message = "C充值系数无效")
    @ApiModelProperty(value = "C充值系数, 比如: 50000000000->0.9=500￥->0.9")
    protected List<GainConfRatioDetail> gainConfRatios;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
