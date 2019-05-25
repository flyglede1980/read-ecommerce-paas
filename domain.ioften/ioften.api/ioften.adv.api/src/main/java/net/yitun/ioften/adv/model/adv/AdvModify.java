package net.yitun.ioften.adv.model.adv;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.Util;

/**
 * <pre>
 * <b>广告 内容修改.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月5日 下午5:38:46 LH
 *         new file.
 * </pre>
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdvModify extends _AdvOpt {

    @NotNull(message = "ID无效")
    @Min(value = Util.MIN_ID, message = "ID无效")
    @ApiModelProperty(value = "ID", required = true)
    protected Long id;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
