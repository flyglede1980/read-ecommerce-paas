package net.yitun.ioften.cms.model.article;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CoverPicturesCreate {

    @ApiModelProperty(value = "图片地址", required = true)
    protected String icon;

    @ApiModelProperty(value = "排序编号", required = true)
    protected int sortId;

}
