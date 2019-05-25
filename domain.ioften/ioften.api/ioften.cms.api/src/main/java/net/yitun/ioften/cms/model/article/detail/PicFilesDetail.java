package net.yitun.ioften.cms.model.article.detail;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"fileId"})
public class PicFilesDetail implements Serializable {

    @ApiModelProperty(value = "文件编号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long fileId;

    @ApiModelProperty(value = "文章编号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long articleId;

    @ApiModelProperty(value = "链接地址")
    protected String linkUrl;

    @ApiModelProperty(value = "文件描述")
    protected String description;

    @ApiModelProperty(value = "排序编号")
    protected Integer sortId;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public PicFilesDetail(Long fileId) {
        super();
        this.fileId = fileId;
    }

}
