package net.yitun.basic.model.vo;

import java.util.Date;
import java.util.Set;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

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
@Accessors(chain = true)
public class DeleteVo {

    @ApiModelProperty(value = "ID")
    protected Long id;

    @ApiModelProperty(value = "ID清单")
    protected Set<Long> ids;

    @ApiModelProperty(value = "用户ID")
    protected Long uid;

    @ApiModelProperty(value = "修改者")
    protected String modifier;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    public DeleteVo(Long id, Date mtime) {
        this(id, null, null, mtime);
    }

    public DeleteVo(Long id, Long uid, Date mtime) {
        this(id, uid, null, mtime);
    }

    public DeleteVo(Long id, String modifier, Date mtime) {
        this(id, null, modifier, mtime);
    }

    public DeleteVo(Long id, Long uid, String modifier, Date mtime) {
        super();
        this.id = id;
        this.uid = uid;
        this.mtime = mtime;
        this.modifier = modifier;
    }

    public DeleteVo(Set<Long> ids, Date mtime) {
        this(ids, null, null, mtime);
    }

    public DeleteVo(Set<Long> ids, Long uid, Date mtime) {
        this(ids, uid, null, mtime);
    }

    public DeleteVo(Set<Long> ids, String modifier, Date mtime) {
        this(ids, null, modifier, mtime);
    }

    public DeleteVo(Set<Long> ids, Long uid, String modifier, Date mtime) {
        super();
        this.ids = ids;
        this.uid = uid;
        this.mtime = mtime;
        this.modifier = modifier;
    }

}
