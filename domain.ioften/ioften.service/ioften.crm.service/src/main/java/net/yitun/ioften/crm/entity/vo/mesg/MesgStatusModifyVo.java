package net.yitun.ioften.crm.entity.vo.mesg;

import java.util.Date;
import java.util.Set;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.dict.YesNo;

/**
 * <pre>
 * <b>用户 消息状态修改Vo.</b>
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
public class MesgStatusModifyVo {

    @ApiModelProperty(value = "用户ID, 外键: crm_user.id")
    protected Long uid;

    @ApiModelProperty(value = "ID清单, 限制在1~1024个之间", required = true)
    protected Set<Long> ids;

    @ApiModelProperty(value = "状态, NO:未读; YES:已读", required = true)
    protected YesNo status;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

}
