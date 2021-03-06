package net.yitun.ioften.sys.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.dict.Status;

/**
 * <pre>
 * <b>系统 管理员模型.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月6日 上午10:05:06 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Admin implements Serializable {

    @ApiModelProperty(value = "ID")
    protected Long id;

    @ApiModelProperty(value = "姓名")
    protected String name;

    @ApiModelProperty(value = "账号")
    protected String account;

    @ApiModelProperty(value = "密码")
    protected String passwd;

    @ApiModelProperty(value = "角色, 多个用逗号分隔")
    protected String roles;

    @ApiModelProperty(value = "手机号")
    protected String phone;

    @ApiModelProperty(value = "邮箱")
    protected String email;

    @ApiModelProperty(value = "状态, DISABLE:禁用; ENABLE:正常")
    protected Status status;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public Admin(Long id) {
        super();
        this.id = id;
    }

    public Admin(Long id, String passwd, Date mtime) {
        super();
        this.id = id;
        this.passwd = passwd;
        this.mtime = mtime;
    }

}
