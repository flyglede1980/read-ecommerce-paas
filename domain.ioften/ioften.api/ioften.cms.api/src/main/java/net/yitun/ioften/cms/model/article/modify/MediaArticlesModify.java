package net.yitun.ioften.cms.model.article.modify;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.ioften.cms.dicts.Type;
import net.yitun.ioften.cms.model.article.ArticleCreate;

/**
 * <pre>
 * <b>修改资讯.</b>
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
public class MediaArticlesModify extends ArticleCreate {

    @NotNull(message = "文章编号无效")
    @Min(value = Util.MIN_ID, message = "文章编号无效")
    @ApiModelProperty(value = "文章编号", required = true)
    protected Long articleId;

    @NotBlank(message = "链接地址不能为空")
    @ApiModelProperty(value = "链接地址", required = true)
    protected String linkUrl;

    @NotNull(message = "文章类型不能为空")
    @ApiModelProperty(value = "文章类型: VIDEO:视频; AUDIO:音频")
    protected Type type;

    @ApiModelProperty(value = "文件描述")
    protected String description;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
