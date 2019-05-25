package net.yitun.ioften.cms.support.task;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.cms.dicts.ArticleStatus;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTask {

    @ApiModelProperty(value = "文章ID")
    protected Long articleId;

    @ApiModelProperty(value = "修改状态")
    protected ArticleStatus status;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

}
