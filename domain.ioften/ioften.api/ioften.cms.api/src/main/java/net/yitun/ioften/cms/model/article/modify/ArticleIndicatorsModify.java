package net.yitun.ioften.cms.model.article.modify;

import javax.validation.constraints.Min;
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
public class ArticleIndicatorsModify{

    @NotNull(message = "文章编号无效")
    @Min(value = Util.MIN_ID, message = "文章编号无效")
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

