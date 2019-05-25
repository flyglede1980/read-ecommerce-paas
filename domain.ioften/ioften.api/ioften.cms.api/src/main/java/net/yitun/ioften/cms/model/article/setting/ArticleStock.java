package net.yitun.ioften.cms.model.article.setting;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.basic.dict.YesNo;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ArticleStock {

    @ApiModelProperty(value = "文章ID")
    @NotNull(message = "文章ID无效")
    @Min(value = Util.MIN_ID, message = "文章ID无效")
    protected Long articleId;

    @ApiModelProperty(value = "投放服豆")
    @Min(value = 0, message = "服豆数量不正确")
    protected long stock;

    @ApiModelProperty(value = "点赞挖坑, NO:关; YES:开")
    protected YesNo praiseSwitch;

    @ApiModelProperty(value = "分享挖坑, NO:关; YES:开")
    protected YesNo shareSwitch;

    @ApiModelProperty(value = "阅读挖坑, NO:关; YES:开")
    protected YesNo readSwitch;

}
