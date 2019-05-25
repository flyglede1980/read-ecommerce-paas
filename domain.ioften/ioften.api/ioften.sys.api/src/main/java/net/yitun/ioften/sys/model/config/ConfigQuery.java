package net.yitun.ioften.sys.model.config;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.basic.model.Page;
import net.yitun.ioften.sys.dicts.Scope;

/**
 * <pre>
 * <b>分页查询.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年10月17日 下午4:36:45 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
public class ConfigQuery extends Page {

    @Min(value = Util.MIN_ID, message = "ID无效")
    @ApiModelProperty(value = "ID")
    protected Long id;

    @Size(min = 2, max = 16, message = "代码长度为2~16个字符")
    @ApiModelProperty(value = "代码, about:关于; wxin:微信, app.version:应用版本信息 长度为2~16个字符")
    protected String code;

    @Size(min = 2, max = 16, message = "名称长度为2~16个字符")
    @ApiModelProperty(value = "名称, 长度为2~16个字符", required = true)
    protected String name;

    @ApiModelProperty(value = "适用范围, ALL:全局; SYS:系统; APP:APP;")
    protected Set<Scope> scope;

    @Size(min = 2, max = 16, message = "修改者度为2~16个字符")
    @ApiModelProperty(value = "修改者")
    protected String modifier;

    @Min(value = 0L, message = "修改者ID无效")
    @ApiModelProperty(value = "修改者ID")
    protected Long modifierId;

    @ApiModelProperty(value = "开始日期")
    protected Date stime;

    @ApiModelProperty(value = "结束日期")
    protected Date etime;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public ConfigQuery() {
        // 排序规则, 默认: ID DESC
        super(null, null, null, "t1.id desc");
    }

    public void setCode(String code) {
        this.code = StringUtils.trimToNull(code);
    }

    public void setModifier(String modifier) {
        this.modifier = StringUtils.trimToNull(modifier);
    }

}
