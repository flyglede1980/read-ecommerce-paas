package net.yitun.ioften.cms.entity.aritcles;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 文章列表中文章业务信息定义
 * @since 1.0.0
 * @author Flyglede
 * @see Articles
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ArticlesBO extends Articles {
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