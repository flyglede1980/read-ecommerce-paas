package net.yitun.ioften.pay.model.flows;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.ioften.pay.dicts.Way;

/**
 * <pre>
 * <b>支付 流水合计查询.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月9日 下午7:57:35
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月9日 下午7:57:35 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyFlowsTotalQuery implements Serializable {

    @Min(value = Util.MIN_ID, message = "UID无效")
    @ApiModelProperty(value = "UID, 用户ID，也是钱包ID")
    protected Long uid;

    @ApiModelProperty(value = "流水用途: N:其他; VIEW:阅读; SHARE:分享; ENJOY:点赞; INVITE:邀请; GIVEIN:获得打赏; GIVEOUT:打赏他人; ADVIN:广告收入; AWARDOUT:设置奖励 SHOP:购物; FETCH:提币; ADDED:充值")
    protected Set<Way> way;

    @NotBlank(message = "年月无效")
    @Size(min = 7, max = 7, message = "年月无效")
    @ApiModelProperty(value = "年月, 格式: yyyy-MM", example = "2018-12", required = true)
    // @Pattern(regexp = "^d{4}-((0([1-9]))|(1(0|1|2)))$",message = "年月无效")
    protected String month;

    @ApiModelProperty(value = "开始日期")
    protected Date stime;

    @ApiModelProperty(value = "结束日期")
    protected Date etime;

    /** 是否启用缓存 */
    private boolean cache = false;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
