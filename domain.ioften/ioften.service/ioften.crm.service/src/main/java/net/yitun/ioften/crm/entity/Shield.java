package net.yitun.ioften.crm.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.crm.model.user.UserDetail;

/**
 * <pre>
 * <b>用户 拉黑模型.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月17日 下午2:25:26 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Shield implements Serializable {

    @ApiModelProperty(value = "ID")
    protected Long id;

    @ApiModelProperty(value = "用户ID, 外键: crm_user.id")
    protected Long uid;

    @ApiModelProperty(value = "对方ID, 外键: crm_user.id")
    protected Long side;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    @ApiModelProperty(value = "对方信息")
    protected UserDetail sideUser;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public Shield(Long id) {
        super();
        this.id = id;
    }

    public Shield(Long uid, Long side) {
        super();
        this.uid = uid;
        this.side = side;
    }

    public Shield(Long id, Date mtime) {
        super();
        this.id = id;
        this.mtime = mtime;
    }

    public Shield(Long id, Long uid, Long side, Date ctime, Date mtime) {
        super();
        this.id = id;
        this.uid = uid;
        this.side = side;
        this.ctime = ctime;
        this.mtime = mtime;
    }

}
