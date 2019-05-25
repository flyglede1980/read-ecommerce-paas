package net.yitun.ioften.cms.model.category;

import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.basic.dict.Status;
import net.yitun.basic.model.Page;

/**
 * <pre>
 * <b>分页查询.</b>
 * <b>Description:</b>
 *
 * <b>Author:</b> XJN
 * <b>Date:</b> 2018-11-12 11:17:00.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月12日 上午11:17:06 XJN
 *         new file.
 * </pre>
 */
@Getter
@Setter
@ToString
public class CategoryQuery extends Page {

    @Min(value = Util.MIN_ID, message = "ID无效")
    @ApiModelProperty(value = "ID")
    protected Long id;

    @Size(min = 2, max = 16, message = "类目名称无效")
    @ApiModelProperty(value = "类目名称, 长度为2~16字符")
    protected String name;

    @Min(value = 0, message = "父级ID无效")
    @ApiModelProperty(value = "父级ID, 默认:0")
    protected Long parentId;

    @ApiModelProperty(value = "状态, DISABLE:禁用; ENABLE:正常")
    protected Set<Status> statuss;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public CategoryQuery() {
        super(null, null, null, null);
        this.count = false;
        this.pageSize = 0;
        // 排序规则, 默认: NULL, 防止内部已有的排序
    }

    @Override
    public CategoryQuery setSortBy(String sortBy) {
        this.sortBy = null;
        return this;
    }

    public void setName(String name) {
        this.name = StringUtils.trim(name); // StringUtils.isBlank(name) ? null : name + "%";
    }

}
