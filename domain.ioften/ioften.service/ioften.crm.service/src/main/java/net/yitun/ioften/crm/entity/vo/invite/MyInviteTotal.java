package net.yitun.ioften.crm.entity.vo.invite;

import java.util.Date;
import java.util.Map;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.crm.dicts.InviteStatus;

/**
 * <pre>
 * <b>用户 邀请统计模型.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月19日 上午9:21:31 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyInviteTotal {

    @ApiModelProperty(value = "累计邀请人数")
    protected Integer total;

    @ApiModelProperty(value = "累计邀请奖励")
    protected Long awardSum;

    @ApiModelProperty(value = "邀请用户参与状态统计")
    protected Map<InviteStatus, Long> statuss;

    @ApiModelProperty(value = "统计时间")
    protected Date ctime;

}
