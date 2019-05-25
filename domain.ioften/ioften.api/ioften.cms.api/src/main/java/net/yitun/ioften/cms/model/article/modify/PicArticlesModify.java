package net.yitun.ioften.cms.model.article.modify;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
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
import net.yitun.ioften.cms.model.article.create.PicFilesCreate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PicArticlesModify extends ArticleCreate {

    @NotNull(message = "文章编号不能为空")
    @Min(value = Util.MIN_ID, message = "ID无效")
    @ApiModelProperty(value = "文章编号", required = true)
    protected Long articleId;

    @ApiModelProperty(value = "封面图数量: SIMPLE: 单图; THREE:三图; AUTO:自动")
    protected CoverNum coverNum;

    @NotEmpty(message = "图片文件不能为空")
    @ApiModelProperty(value = "图片文件", required = true)
    protected List<PicFilesCreate> picFiles;

    /* SVUID */
    private static final long serialVersionUID = 1L;
}
