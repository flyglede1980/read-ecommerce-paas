package net.yitun.ioften.pay.model.rank;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <pre>
 * <b>支付 我的服豆排名.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月14日 上午11:39:56
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月14日 上午11:39:56 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyRank implements Serializable {

    @ApiModelProperty(value = "我的排行, -1:代表100名之外")
    protected Integer no = -1;

    @ApiModelProperty(value = "排行比率")
    protected Float ratio = 0.0F;

    @ApiModelProperty(value = "更新时间")
    protected Date mtime;

    /* SVUID */
    private static final long serialVersionUID = 1L;
}
