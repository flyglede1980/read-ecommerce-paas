package net.yitun.ioften.cms.model.category;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.basic.dict.YesNo;

/**
 * <pre>
 * <b>类目修改.</b>
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
public class CategoryModify extends _CategoryOpt {

    @NotNull(message = "ID无效")
    @Min(value = Util.MIN_ID, message = "ID无效")
    @ApiModelProperty(value = "ClassId", required = true)
    protected Long classId;

    @NotNull(message = "类目名称无效")
    @Size(min = 2, max = 16, message = "类目名称, 长度为2~16个字符")
    @ApiModelProperty(value = "类目名称, 长度为2~16个字符", required = true)
    protected String name;

    @ApiModelProperty(value = "是否热门: YES:热门; NO:正常")
    protected YesNo isHot;

    @ApiModelProperty(value = "是否推荐: YES:推荐; NO:正常")
    protected YesNo isRecommend;

    @ApiModelProperty(value = "是否置顶: YES:置顶; NO:正常")
    protected YesNo isTop;

    @ApiModelProperty(value = "是否需要授权: NO:不需授权; YES:需要授权")
    protected YesNo isAuthorized;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}