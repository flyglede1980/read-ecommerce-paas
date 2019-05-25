package net.yitun.ioften.cms.model.article.detail;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.cms.dicts.CoverNum;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TextArticlesDetail {

    @ApiModelProperty(value = "文章编号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long articleId;

    @ApiModelProperty(value = "文章详情")
    protected String details;

    @ApiModelProperty(value = "封面图数量: SIMPLE:单图; THREE:三图; AUTO:自动")
    protected CoverNum coverNum;

}
