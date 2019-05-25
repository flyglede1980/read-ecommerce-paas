package net.yitun.ioften.cms.model.article;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.Min;

import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.basic.model.Page;
import net.yitun.basic.utils.DateUtil;
import net.yitun.ioften.cms.dicts.ArticleStatus;
import net.yitun.ioften.cms.dicts.StockStatus;
import net.yitun.ioften.cms.dicts.Type;

/**
 * <pre>
 * <b>分页查询.</b>
 * <b>Description:</b>
 *
 * <b>Author:</b> XJN
 * <b>Date:</b> 2018-11-12 11:17:00.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月12日 上午11:17:06 XJN
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
public class ArticleQuery extends Page {

    @ApiModelProperty(value = "文章类型: TEXT:图文; IMAGE:纯图; VIDEO:音视频")
    protected Type type;

    @ApiModelProperty(value = "界面输入文章ID")
    @Min(value = Util.MIN_ID, message = "文章ID无效")
    protected Long articleId;

    @ApiModelProperty(value = "分类ID")
    protected Set<Long> classIds;

    @ApiModelProperty(value = "推荐排除文章ID")
    protected Long aid;

    @ApiModelProperty(value = "用户ID集合")
    protected Set<Long> uid;

    @ApiModelProperty(value = "文章标题")
    protected String mainTitle;

    @ApiModelProperty(value = "投放追投, 未投放; 投放中; 投放过")
    protected Set<StockStatus> stockStatus;

    @ApiModelProperty(value = "文章状态, DRAFT:草稿; CHECK:审核中; REFUSE:已拒绝; ISSUE:已发布; WAIT_ISSUE:待发布; OFFLINE:已下线; DELETE:已删除")
    protected Set<ArticleStatus> status;

    @ApiModelProperty(value = "开始时间")
    protected Date stime;

    @ApiModelProperty(value = "结束时间")
    protected Date etime;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public ArticleQuery() {
        // 排序规则, 默认: ID DESC
        super(null, null, null, "articleid desc");
    }

    public void setStime(Date stime) {
        stime = DateUtil.dayOfStartTime(stime);
        this.stime = stime;
    }

    public void setEtime(Date etime) {
        etime = DateUtil.dayOfEndTime(etime);
        this.etime = etime;
    }

    public void setTitle(String title) {
        this.mainTitle = StringUtils.trimToNull(title + "%");
    }

}
