package net.yitun.ioften.cms.model.article.create;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.yitun.ioften.cms.dicts.Type;
import net.yitun.ioften.cms.model.article.ArticleCreate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MediaArticlesCreate extends ArticleCreate {

    @NotNull(message = "类型不能为空")
    @ApiModelProperty(value = "文章类型: VIDEO:视频; AUDIO:音频")
    protected Type type;

    @NotBlank(message = "链接地址不能为空")
    @ApiModelProperty(value = "链接地址", required = true)
    protected String linkUrl;

    @ApiModelProperty(value = "文件描述")
    protected String description;

    @ApiModelProperty(value = "排序编号")
    protected Integer sortId;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
