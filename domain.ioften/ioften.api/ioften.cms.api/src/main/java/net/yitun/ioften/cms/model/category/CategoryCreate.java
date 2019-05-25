package net.yitun.ioften.cms.model.category;

import java.util.LinkedHashSet;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryCreate extends _CategoryOpt {

    @NotNull(message = "类目名称无效")
    @Size(min = 1, max = 1024, message = "类目名称, 批量个数限制为1~1024个")
    @ApiModelProperty(value = "类目名称, 长度为2~16个字符", required = true)
    protected LinkedHashSet<String> names;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
