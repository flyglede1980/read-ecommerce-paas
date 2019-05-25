package net.yitun.ioften.cms.model.gain;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.cms.dicts.GainStatus;

/**
 * <pre>
 * <b>资讯 阅读挖矿.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月12日 下午8:21:16
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月12日 下午8:21:16 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GainDetail implements Serializable {

    @ApiModelProperty(value = "挖矿金额, 小数点右移8位并只保留整数部分")
    protected Long award;

    @ApiModelProperty(value = "阅读挖矿时限, 单位: 秒")
    protected Long viewTime;

    @ApiModelProperty(value = "当前挖矿状态, ON:已开启; OFF:已关闭; REDO:重新开始; FAILED:挖矿失败; SUCCESS:挖矿成功")
    protected GainStatus status;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
