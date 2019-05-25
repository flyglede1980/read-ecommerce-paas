package net.yitun.ioften.cms.model.show;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.dict.YesNo;
import net.yitun.ioften.crm.model.user.UserDetail;

/**
 * <pre>
 * <b>用户信息.</b>
 * <b>Description:</b>
 *
 * <b>Author:</b> XJN
 * <b>Date:</b> 2018-11-12 11:17:00.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月12日 上午11:17:06 XJN
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserTotalDetail implements Serializable {

    @ApiModelProperty(value = "用户信息")
    protected UserDetail user;

    @ApiModelProperty(value = "资讯总数")
    protected Integer articleTotal;

    @ApiModelProperty(value = "总关注数")
    protected Integer careTotal;

    @ApiModelProperty(value = "总粉丝数")
    protected Integer fansTotal;

    @ApiModelProperty(value = "总获赏数")
    protected Long rewardTotal;

    @ApiModelProperty(value = "是否关注: YES:已关注; NO:未关注")
    protected YesNo care;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
