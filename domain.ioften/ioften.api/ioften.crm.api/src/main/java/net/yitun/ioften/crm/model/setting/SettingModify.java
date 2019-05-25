package net.yitun.ioften.crm.model.setting;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <pre>
 * <b>用户设置修改.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月5日 下午5:38:28 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SettingModify implements Serializable {

    @NotNull(message = "用户ID无效")
    @ApiModelProperty(value = "用户ID, 外键: crm_user.id", required = true)
    protected Long uid;

    @NotBlank(message = "代码无效")
    @Size(max = 16, message = "代码长度超过16个字符")
    @ApiModelProperty(value = "代码, myTopic:我关注话题; mySetting:我的设置; myCategory:我的分类(多个分类用逗号分隔)", required = true)
    protected String code;

    @ApiModelProperty(value = "配置值")
    protected Object value;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
