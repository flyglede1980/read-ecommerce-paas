package net.yitun.ioften.pay.model.flows;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author ：ZH.
 * @Date ：Created in 14:09 2018/12/7 0007 @Description：
 * @Modified By：
 * @Version:
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyFlowsTotalDetail implements Serializable {

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
}
