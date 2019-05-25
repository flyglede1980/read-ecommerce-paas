package net.yitun.ioften.cms.model.article;

import java.util.Set;

import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.basic.model.Page;
import net.yitun.ioften.cms.dicts.ArticleStatus;
import net.yitun.ioften.cms.dicts.Type;

@Setter
@Getter
@ToString
public class ArticleReviewQuery extends Page {

    @ApiModelProperty(value = "手机号")
    @Pattern(regexp = Util.REGEX_PHONE, message = "手机号无效")
    protected String phone;

    @ApiModelProperty(value = "用户ID集合")
    protected Set<Long> uid;

    @ApiModelProperty(value = "文章状态, DRAFT:草稿; CHECK:审核中; REFUSE:已拒绝; ISSUE:已发布; WAIT_ISSUE:待发布; OFFLINE:已下线; DELETE:已删除")
    protected Set<ArticleStatus> articleStatus;

    @ApiModelProperty(value = "文章类型: TEXT:图文; IMAGE:纯图; VIDEO:音视频")
    protected Type type;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public ArticleReviewQuery() {
        // 审核界面排序规则, 默认: 申请时间 DESC
        super(null, null, null, "applyTime desc");
    }

}
