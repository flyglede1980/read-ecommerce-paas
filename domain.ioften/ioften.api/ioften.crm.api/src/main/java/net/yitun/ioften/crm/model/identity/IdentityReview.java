package net.yitun.ioften.crm.model.identity;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.ioften.crm.dicts.IdentityStatus;

/**
 * <pre>
 * <b>认证审核.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月13日 下午3:41:06 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class IdentityReview implements Serializable {

    @NotNull(message = "ID无效")
    @Min(value = Util.MIN_ID, message = "ID无效")
    @ApiModelProperty(value = "ID", required = true)
    protected Long id;

    @Size(min = 0, max = 255, message = "审批备注长度为0~255个字符")
    @ApiModelProperty(value = "审批备注, 长度为0~255个字符")
    protected String auditRemark;

    @NotNull(message = "审核状态无效")
    @ApiModelProperty(value = "审核状态, NEW:新申请, REVIEW:认证中; REFUSE:拒绝; APPROVE:已认证")
    protected IdentityStatus status;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
