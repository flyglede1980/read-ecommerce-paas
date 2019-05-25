package net.yitun.ioften.cms.model.article.create;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.yitun.ioften.cms.dicts.CoverNum;
import net.yitun.ioften.cms.model.article.ArticleCreate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TextArticlesCreate extends ArticleCreate {

    @NotBlank(message = "文章详情不能为空")
    @ApiModelProperty(value = "文章详情", required = true)
    protected String details;

    @ApiModelProperty(value = "封面图数量: SIMPLE: 单图; THREE:三图; AUTO:自动")
    protected CoverNum coverNum;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
