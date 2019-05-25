package net.yitun.ioften.cms.model.category;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <pre>
 * <b>添加类目.</b>
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
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class _CategoryOpt implements Serializable {

    @NotNull(message = "父级ID无效")
    @Min(value = 0, message = "父级ID无效")
    @ApiModelProperty(value = "父级ID, 默认:0", required = true)
    protected Long parentId;

    @Min(value = 0, message = "顺序无效")
    @Max(value = 10000, message = "顺序无效")
    @ApiModelProperty(value = "顺序, 范围为0~9999, 默认: 9999", required = true)
    protected Integer sortId = 9999;

    @Size(min = 0, max = 200, message = "分类描述长度为0~200个字符")
    @ApiModelProperty(value = "分类描述, 长度为200个字符")
    protected String description;

    @ApiModelProperty(value = "分类图标")
    protected String icon;


    /* SVUID */
    private static final long serialVersionUID = 1L;

    public void setDescription(String description) {
        this.description = StringUtils.trimToNull(description);
    }

    public void setSortId(Integer sortId) {
        this.sortId = (null == sortId || 0 > sortId) ? this.sortId : sortId;
    }
}