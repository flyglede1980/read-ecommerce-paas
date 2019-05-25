package net.yitun.ioften.cms.model.article.modify;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.Util;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PicFilesModify {

    @NotNull(message = "文章编号无效")
    @Min(value = Util.MIN_ID, message = "文章编号无效")
    @ApiModelProperty(value = "文章编号", required = true)
    protected Long articleId;

    @NotBlank(message = "链接地址不能为空")
    @ApiModelProperty(value = "链接地址", required = true)
    protected String linkUrl;

    @ApiModelProperty(value = "文件描述")
    protected String description;

    @ApiModelProperty(value = "排序编号")
    protected Integer sortId;

}
