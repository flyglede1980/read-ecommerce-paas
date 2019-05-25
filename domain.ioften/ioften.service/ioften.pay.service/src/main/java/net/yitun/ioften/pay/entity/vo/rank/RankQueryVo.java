package net.yitun.ioften.pay.entity.vo.rank;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.ioften.pay.entity.Rank;

/**
 * <pre>
 * <b>支付 排名分页查询Vo.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月14日 下午3:25:16
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月14日 下午3:25:16 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
public class RankQueryVo extends Rank {

    @ApiModelProperty(value = "开始时间")
    private Date stime;

    @ApiModelProperty(value = "结束时间")
    private Date etime;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public RankQueryVo(Long id, Integer no, Long userId, Long amount, Date ctime, Date mtime, Date stime) {
        super(id, userId, no, amount, ctime, mtime);
        this.stime = stime;
    }
}
