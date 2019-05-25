package net.yitun.ioften.cms.entity.vo.browse;

import java.util.Date;
import java.util.Set;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import net.yitun.basic.dict.Status;

/**
 * <pre>
 * <b>批量删除Vo.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月6日 下午7:33:25 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
public class DeleteVo {

    @ApiModelProperty(value = "用户ID")
    protected Long uid;

    @ApiModelProperty(value = "ID清单")
    protected Set<Long> ids;

    @ApiModelProperty(value = "状态")
    protected Status status;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

}
