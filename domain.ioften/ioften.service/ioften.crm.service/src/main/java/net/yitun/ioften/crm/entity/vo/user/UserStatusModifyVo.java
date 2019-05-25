package net.yitun.ioften.crm.entity.vo.user;

import java.util.Date;
import java.util.Set;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.dict.Status;

/**
 * <pre>
 * <b>用户 用户状态修改Vo.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月24日 下午11:08:04 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserStatusModifyVo {

    @ApiModelProperty(value = "ID清单, 限制在1~1024个之间", required = true)
    protected Set<Long> ids;

    @ApiModelProperty(value = "状态, DISABLE:禁用; ENABLE:正常", required = true)
    protected Status status;

    @ApiModelProperty(value = "备注, 长度为0~64个字符")
    protected String remark;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

}
