package net.yitun.ioften.cms.model.enjoy;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import net.yitun.basic.dict.YesNo;
import net.yitun.ioften.cms.model.gain.GainDetail;

/**
 * <pre>
 * <b>资讯 点赞信息.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月23日 下午5:19:58
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月23日 下午5:19:58 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EnjoyDetail implements Serializable {

    @ApiModelProperty(value = "点赞状态, YES:已点赞；NO:未点赞")
    protected YesNo enjoyed;

    @ApiModelProperty(value = "点赞消息")
    protected String message;

    @ApiModelProperty(value = "挖矿结果")
    protected GainDetail gainInfo;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
