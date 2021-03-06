package net.yitun.ioften.cms.entity.aritcles;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserArticleCount{

    @ApiModelProperty(value = "文章总数")
    protected Integer articleTotal;

    @ApiModelProperty(value = "分享总数")
    protected Integer shareTotal;

    @ApiModelProperty(value = "浏览总数")
    protected Integer browseTotal;

    @ApiModelProperty(value = "评论总数")
    protected Integer commentTotal;

    @ApiModelProperty(value = "点赞总数")
    protected Integer praiseTotal;

    @ApiModelProperty(value = "收藏总数")
    protected Integer collectionTotal;

    @ApiModelProperty(value = "打赏总数")
    protected Integer rewardTotal;

    @ApiModelProperty(value = "打赏总收益")
    protected Integer rewardProfitTotal;

}
