package net.yitun.ioften.pay.entity.vo.rank;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <pre>
 * <b>支付 我的排名.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月14日 下午2:07:37
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月14日 下午2:07:37 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyRankVo implements Serializable {

    @ApiModelProperty(value = "我的排行, -1:代表100名之外")
    protected Integer no = -1;

    @ApiModelProperty(value = "参与排名总人数")
    protected Integer total = 0;

    @ApiModelProperty(value = "排行比率")
    protected Float ratio = 0.0F;

    @ApiModelProperty(value = "更新时间")
    protected Date mtime = new Date(System.currentTimeMillis());

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public void setNo(Integer no) {
        this.no = no;
        this.setTotal(this.total);
    }

    public void setTotal(Integer total) {
        this.total = total;
        if (null != this.no && null != this.total && 0 < this.total)
            this.ratio = new BigDecimal(this.no) // 计算当前排名占比
                    .divide(new BigDecimal(this.total), 2, RoundingMode.HALF_UP).floatValue();

        if (null != this.no && 100 < this.no)
            this.no = -1;
    }

}
