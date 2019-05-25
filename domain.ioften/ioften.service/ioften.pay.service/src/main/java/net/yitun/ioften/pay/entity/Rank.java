package net.yitun.ioften.pay.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.crm.model.user.UserDetail;

/**
 * <pre>
 * <b>支付 服豆排名.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月14日 下午1:47:49
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月14日 下午1:47:49 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Rank implements Serializable {

    @ApiModelProperty(value = "ID")
    protected Long id;

    @ApiModelProperty(value = "用户ID, 外键: crm_user.id")
    protected Long uid;

    @ApiModelProperty(value = "排名")
    protected Integer no;

    @ApiModelProperty(value = "累计收益服豆数量")
    protected Long amount;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    @ApiModelProperty(value = "用户信息")
    protected UserDetail user;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public Rank(Long id, Long userId, Integer no, Long amount, Date ctime, Date mtime) {
        super();
        this.id = id;
        this.uid = userId;
        this.no = no;
        this.amount = amount;
        this.ctime = ctime;
        this.mtime = mtime;
    }

}
