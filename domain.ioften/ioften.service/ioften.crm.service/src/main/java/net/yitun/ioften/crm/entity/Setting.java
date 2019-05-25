package net.yitun.ioften.crm.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <pre>
 * <b>用户 我的设置模型.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月6日 下午1:38:37 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Setting implements Serializable {

    @ApiModelProperty(value = "ID")
    protected Long id;

    @ApiModelProperty(value = "用户ID, 外键: crm_user.id")
    protected Long uid;

    @ApiModelProperty(value = "代码, mySetting:我的设置; myCategory:我的分类(多个分类用逗号分隔)")
    protected String code;

    @ApiModelProperty(value = "配置值")
    protected String value;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public Setting(Long id) {
        super();
        this.id = id;
    }

}
