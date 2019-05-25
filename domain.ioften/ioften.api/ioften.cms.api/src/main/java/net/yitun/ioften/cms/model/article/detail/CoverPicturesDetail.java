package net.yitun.ioften.cms.model.article.detail;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CoverPicturesDetail {

    @ApiModelProperty(value = "图片编号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long pictureId;

    @ApiModelProperty(value = "文章编号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long articleId;

    @ApiModelProperty(value = "图片地址")
    protected String icon;

    @ApiModelProperty(value = "排序编号")
    protected Integer sortId;

}
