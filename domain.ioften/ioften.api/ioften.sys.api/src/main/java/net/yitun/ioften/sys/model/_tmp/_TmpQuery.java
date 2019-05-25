package net.yitun.ioften.sys.model._tmp;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.dict.Status;
import net.yitun.basic.model.Page;

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
public class _TmpQuery extends Page {

    @ApiModelProperty(value = "ID")
    protected Long id;

    @ApiModelProperty(value = "字符, 长度为4-128个字符")
    protected String name;

    @ApiModelProperty(value = "修改者")
    protected String modifier;

    @ApiModelProperty(value = "状态, DISABLE:禁用; ENABLE:正常")
    protected Set<Status> statuss;

    @ApiModelProperty(value = "开始日期")
    protected Date stime;

    @ApiModelProperty(value = "结束日期")
    protected Date etime;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public _TmpQuery() {
        // 排序规则, 默认: ID DESC
        super(null, null, null, "t1.id desc");
    }

    public void setName(String name) {
        this.name = StringUtils.trimToNull(name);
    }

    public void setModifier(String modifier) {
        this.modifier = StringUtils.trimToNull(modifier);
    }

}
