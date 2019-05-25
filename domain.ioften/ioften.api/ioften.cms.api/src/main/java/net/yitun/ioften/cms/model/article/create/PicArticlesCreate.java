package net.yitun.ioften.cms.model.article.create;

import java.util.List;

import javax.validation.constraints.NotEmpty;

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
public class PicArticlesCreate extends ArticleCreate {

    @ApiModelProperty(value = "封面图数量: SIMPLE: 单图; THREE:三图; AUTO:自动")
    protected CoverNum coverNum;

    @NotEmpty(message = "图片文件不能为空")
    @ApiModelProperty(value = "图片文件", required = true)
    protected List<PicFilesCreate> picFiles;

    /* SVUID */
    private static final long serialVersionUID = 1L;
}
