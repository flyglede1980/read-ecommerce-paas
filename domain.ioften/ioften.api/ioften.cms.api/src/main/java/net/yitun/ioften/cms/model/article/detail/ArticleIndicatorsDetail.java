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
@EqualsAndHashCode(of = {"articleId"})
public class ArticleIndicatorsDetail implements Serializable {

    @ApiModelProperty(value = "文章编号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long articleId;

    @ApiModelProperty(value = "分享数")
    protected Integer shareNum;

    @ApiModelProperty(value = "浏览数")
    protected Integer browseNum;

    @ApiModelProperty(value = "评论数")
    protected Integer commentNum;

    @ApiModelProperty(value = "点赞数")
    protected Integer praiseNum;

    @ApiModelProperty(value = "收藏数")
    protected Integer collectionNum;

    @ApiModelProperty(value = "打赏数")
    protected Integer reward;

    @ApiModelProperty(value = "打赏收益")
    protected Integer rewardProfit;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public ArticleIndicatorsDetail(Long articleId) {
        super();
        this.articleId = articleId;
    }

}

