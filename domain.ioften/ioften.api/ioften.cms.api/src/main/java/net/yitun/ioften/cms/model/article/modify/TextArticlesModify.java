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
import net.yitun.ioften.cms.dicts.CoverNum;
import net.yitun.ioften.cms.model.article.ArticleCreate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TextArticlesModify extends ArticleCreate {

    @NotNull(message = "文章编号无效")
    @Min(value = Util.MIN_ID, message = "文章编号无效")
    @ApiModelProperty(value = "文章编号")
    protected Long articleId;

    @NotBlank(message = "文章内容不能为空")
    @ApiModelProperty(value = "文章详情")
    protected String details;

    @ApiModelProperty(value = "封面图数量: SIMPLE:单图; THREE:三图; AUTO:自动")
    protected CoverNum coverNum;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
