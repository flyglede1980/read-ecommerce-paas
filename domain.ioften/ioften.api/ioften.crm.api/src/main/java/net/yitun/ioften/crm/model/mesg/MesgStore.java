package net.yitun.ioften.crm.model.mesg;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.crm.dicts.MesgType;

/**
 * <pre>
 * <b>用户 存储消息.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月17日 下午1:29:38
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月17日 下午1:29:38 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MesgStore implements Serializable {

    @ApiModelProperty(value = "用户ID, 外键: crm_user.id", required = true)
    protected Long uid;

    @ApiModelProperty(value = "类型, SYS:系统; ENJOY:获赞; FOCUS:关注", required = true)
    protected MesgType type;

    @ApiModelProperty(value = "参与者, 0:系统; 其他为用户ID, 外键: crm_user.id", required = true)
    protected Long actor;

    @ApiModelProperty(value = "关联对象, 如:0:未知,文章ID,订单ID,评论ID,用户ID", required = true)
    protected Long target;

    @ApiModelProperty(value = "内容")
    protected String content;

    @ApiModelProperty(value = "拓展数据")
    protected Integer times;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
