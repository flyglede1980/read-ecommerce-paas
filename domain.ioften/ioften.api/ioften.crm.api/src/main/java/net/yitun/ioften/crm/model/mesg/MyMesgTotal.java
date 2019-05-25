package net.yitun.ioften.crm.model.mesg;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <pre>
 * <b>用户 消息统计.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月17日 下午8:34:01
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月17日 下午8:34:01 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MyMesgTotal implements Serializable {

    @ApiModelProperty(value = "有新消息")
    protected boolean hasNew;

    @ApiModelProperty(value = "新消息条数")
    protected Integer newTotal;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
