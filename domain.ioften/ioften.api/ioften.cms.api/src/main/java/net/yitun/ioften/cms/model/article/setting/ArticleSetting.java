package net.yitun.ioften.cms.model.article.setting;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.basic.dict.YesNo;
import net.yitun.ioften.crm.dicts.Level;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ArticleSetting {

    @ApiModelProperty(value = "文章ID")
    @NotNull(message = "文章ID无效")
    @Min(value = Util.MIN_ID, message = "文章ID无效")
    protected Long articleId;

    @ApiModelProperty(value = "是否热门: YES:热门; NO:普通")
    protected YesNo isHot;

    @ApiModelProperty(value = "是否推荐: YES:推荐; NO:普通")
    protected YesNo isRecommend;

    @ApiModelProperty(value = "是否置顶: YES:置顶; NO:正常")
    protected YesNo isTop;

    @ApiModelProperty(value = "是否需要授权: YES:需要授权; NO:不需授权")
    protected YesNo isAuthorized;

    @ApiModelProperty(value = "阅读等级: 阅读等级, N, VIP1, VIP2 ...")
    protected Level readLevel;

    @ApiModelProperty(value = "发行时间")
    protected Date issueTime;

    @ApiModelProperty(value = "栏目分类")
    protected List<Long> classIds;
}
