package net.yitun.ioften.pay.entity.vo.flows;

import java.util.Date;
import java.util.Set;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.ioften.pay.dicts.Way;
import net.yitun.ioften.pay.entity.Flows;

/**
 * <pre>
 * <b>支付 流水分页查询Vo.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月6日 上午10:36:32 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
public class FlowsQueryVo extends Flows {

    @ApiModelProperty(value = "开始日期")
    protected Date stime;

    @ApiModelProperty(value = "结束日期")
    protected Date etime;

    @ApiModelProperty(value = "流水用途: N:其他; VIEW:阅读; SHARE:分享; ENJOY:点赞; INVITE:邀请; GIVEIN:获得打赏; GIVEOUT:打赏他人; ADVIN:广告收入; AWARDOUT:设置奖励 SHOP:购物; FETCH:提币; ADDED:充值")
    protected Set<Way> ways;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public FlowsQueryVo(Long id, Long uid, Date stime, Date etime, Set<Way> ways) {
        super(id, uid, null /* way */, null /* amount */, null /* currency */, null /* target */, null /* channel */,
                null /* outFlows */, null /* newGive */, null /* newIncome */, null /* newBalance */, null /* remark */,
                null /* status */, null /* ctime */, null /* mtime */);
        this.stime = stime;
        this.etime = etime;
        this.ways = ways;
    }

}
