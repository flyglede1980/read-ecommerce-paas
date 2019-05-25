package net.yitun.ioften.crm.model.mesg;

import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.basic.model.Page;
import net.yitun.ioften.crm.dicts.MesgType;

/**
 * <pre>
 * <b>用户 消息分页查询.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月19日 上午10:06:24 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
public class MesgQuery extends Page {

    @ApiModelProperty(value = "用户ID")
    @Min(value = Util.MIN_ID, message = "用户ID无效")
    protected Long uid;

    @ApiModelProperty(value = "类型, SYS:系统; ENJOY:获赞; FOCUS:关注")
    protected MesgType type;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public MesgQuery() {
        // 排序规则, 默认: ID DESC
        super(null, null, null, "t1.mtime desc");
    }

}
