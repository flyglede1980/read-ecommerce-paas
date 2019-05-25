package net.yitun.ioften.sys.model._tmp;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.basic.dict.Status;

/**
 * <pre>
 * <b>配置修改.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月6日 上午10:05:06 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class _TmpModify implements Serializable {

    @NotBlank(message = "ID无效")
    @Min(value = Util.MIN_ID, message = "ID无效")
    @ApiModelProperty(value = "ID", required = true)
    protected Long id;

    @NotBlank(message = "字符无效")
    @Size(min = 4, max = 128, message = "字符长度为4-128个字符")
    @ApiModelProperty(value = "字符, 长度为4-128个字符", required = true)
    protected String name;

    @NotBlank(message = "状态无效")
    @ApiModelProperty(value = "状态, DISABLE:禁用; ENABLE:正常", required = true)
    protected Status status;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
