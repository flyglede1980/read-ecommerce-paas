package net.yitun.ioften.sys.model.config;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.sys.dicts.Scope;

/**
 * <pre>
 * <b>配置修改.</b>
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
public class ConfigStore implements Serializable {

    @NotBlank(message = "代码无效")
    @Size(min = 2, max = 16, message = "代码长度为2~16个字符")
    @ApiModelProperty(value = "代码, about:关于; wxin:微信, app.version:应用版本信息 长度为2~16个字符", required = true)
    protected String code;

    @NotBlank(message = "名称无效")
    @Size(min = 2, max = 16, message = "名称长度为2~16个字符")
    @ApiModelProperty(value = "名称, 长度为2~16个字符", required = true)
    protected String name;

    @NotNull(message = "适用范围无效")
    @ApiModelProperty(value = "适用范围, ALL:全局; SYS:系统; APP:APP;", required = true)
    protected Scope scope;

    @NotBlank(message = "配置值无效")
    @Size(min = 2, max = 65535, message = "配置值长度为2~65535个字符")
    @ApiModelProperty(value = "配置值, 长度为2~65535个字符", required = true)
    protected String value;

    @Size(min = 0, max = 65535, message = "描述长度为0~65535个字符")
    @ApiModelProperty(value = "描述, 长度为0~65535个字符")
    protected String remark;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public ConfigStore(String code, Scope scope, Object value) {
        super();
        this.code = code;
        this.scope = scope;
        this.setValue(value);
    }

    public ConfigStore(String code, Scope scope, String value) {
        super();
        this.code = code;
        this.scope = scope;
        this.value = value;
    }

    public ConfigStore(String code, String name, Scope scope, Object value, String remark) {
        super();
        this.code = code;
        this.name = name;
        this.scope = scope;
        this.setValue(value);
        this.remark = remark;
    }

    public ConfigStore setValue(String value) {
        this.value = value;
        return this;
    }

    public ConfigStore setValue(Object value) {
        if (null != value)
            this.value = JSON.toJSONString(value);
        return this;
    }
}
