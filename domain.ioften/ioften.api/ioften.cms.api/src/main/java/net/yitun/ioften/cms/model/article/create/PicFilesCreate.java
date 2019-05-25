package net.yitun.ioften.cms.model.article.create;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PicFilesCreate {

    @NotBlank(message = "链接地址不能为空")
    @ApiModelProperty(value = "链接地址", required = true)
    protected String linkUrl;

    @ApiModelProperty(value = "文件描述")
    protected String description;

    @ApiModelProperty(value = "排序编号")
    protected Integer sortId;

}
