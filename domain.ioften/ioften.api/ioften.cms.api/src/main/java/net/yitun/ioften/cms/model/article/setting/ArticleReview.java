package net.yitun.ioften.cms.model.article.setting;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.ioften.cms.dicts.ArticleStatus;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ArticleReview {

    @NotNull(message = "文章ID无效")
    @Min(value = Util.MIN_ID, message = "文章ID无效")
    @ApiModelProperty(value = "文章ID", required = true)
    protected Long id;

    @NotNull(message = "文章状态")
    @ApiModelProperty(value = "状态, DRAFT:草稿; CHECK:审核中; REFUSE:已拒绝; ISSUE:已发布; WAIT_ISSUE:待发布; OFFLINE:已下线; DELETE:已删除")
    protected ArticleStatus status;

    @ApiModelProperty(value = "文件大小(KB)")
    protected Integer fileSize;

    @ApiModelProperty(value = "时长(秒数)")
    protected Integer minutes;

    @ApiModelProperty(value = "操作描述(拒绝原因)")
    protected String reason;
}
