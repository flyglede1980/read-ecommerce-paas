package net.yitun.ioften.crm.model.user;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.dict.Status;

/**
 * <pre>
 * <b>状态设置.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年10月17日 下午4:37:04 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserStatusModify implements Serializable {

    @NotNull(message = "ID清单无效")
    @Size(min = 1, max = 1024, message = "ID清单个数应在1~1024之间")
    @ApiModelProperty(value = "ID清单, 限制在1~1024个之间", required = true)
    protected Set<Long> ids;

    @NotNull(message = "状态无效")
    @ApiModelProperty(value = "状态, DISABLE:禁用; ENABLE:正常", required = true)
    protected Status status;

    @Size(max = 64, message = "备注长度为0~64个字符")
    @ApiModelProperty(value = "备注, 长度为0~64个字符")
    protected String remark;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
