package net.yitun.ioften.cms.entity.aritcles;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 统计指标信息
 * @since 1.0.0
 * @see ApiModelProperty
 * @author Flyglede
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ArticleIndicators {
    @ApiModelProperty(value = "文章编号")
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
}