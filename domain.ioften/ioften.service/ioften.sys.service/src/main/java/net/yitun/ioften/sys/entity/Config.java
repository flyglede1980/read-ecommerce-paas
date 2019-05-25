package net.yitun.ioften.sys.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.sys.dicts.Scope;

/**
 * <pre>
 * <b>系统 配置模型.</b>
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
public class Config implements Serializable {

    @ApiModelProperty(value = "ID")
    protected Long id;

    @ApiModelProperty(value = "代码, about:关于; wxin:微信, app.version:应用版本信息 长度为4~16个字符")
    protected String code;

    @ApiModelProperty(value = "名称, 长度为2~16个字符")
    protected String name;

    // ALL: 全局用户可视; SYS: 系统内部业务; APP:移动端或者前端
    @ApiModelProperty(value = "适用范围, ALL:全局; SYS:系统; APP:APP;")
    protected Scope scope;

    @ApiModelProperty(value = "配置值, 长度为2~65535个字符")
    protected String value;

    @ApiModelProperty(value = "描述, 长度为0~65535个字符")
    protected String remark;

    @ApiModelProperty(value = "修改者")
    protected String modifier;

    @ApiModelProperty(value = "修改者ID, 外键: sys_admin.id")
    protected Long modifierId;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public Config(Long id) {
        super();
        this.id = id;
    }

    public Config(Long id, Scope scope, String value, String modifier, Long modifierId, Date mtime) {
        super();
        this.id = id;
        this.scope = scope;
        this.value = value;
        this.modifier = modifier;
        this.modifierId = modifierId;
        this.mtime = mtime;
    }

}
