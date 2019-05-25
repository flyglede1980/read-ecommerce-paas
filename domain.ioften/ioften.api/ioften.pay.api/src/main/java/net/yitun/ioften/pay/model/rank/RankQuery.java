package net.yitun.ioften.pay.model.rank;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.model.Page;

/**
 * <pre>
 * <b>支付 服豆排名分页查询.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月14日 上午11:40:38
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月14日 上午11:40:38 LH
 *         new file.
 * </pre>
 */
@Getter
@Setter
@ToString
public class RankQuery extends Page {

    @ApiModelProperty(value = "开始时间")
    private Date stime;

    @ApiModelProperty(value = "结束时间")
    private Date etime;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public RankQuery() {
        super(null, 3, null, "t1.no asc");
    }

}
