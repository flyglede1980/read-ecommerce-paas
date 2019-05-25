package net.yitun.ioften.cms.repository.vo;

import java.util.Date;
import java.util.Set;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.dict.Status;
import net.yitun.ioften.cms.dicts.ArticleStatus;
import net.yitun.ioften.cms.dicts.StockStatus;

/**
 * <pre>
 * <b>资讯 文章查询Vo.</b>
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
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ArticlesQueryVo{

    @ApiModelProperty(value = "文章类型: N:其他(未知); TEXT:图文; IMAGE:纯图; VIDEO:音视频")
    protected String type;

    @ApiModelProperty(value = "界面输入文章ID")
    protected Set<Long> articleIds;

    @ApiModelProperty(value = "类目ID集合")
    protected Set<Long> classIds;

    @ApiModelProperty(value = "推荐排除文章ID")
    protected Long aid;

    @ApiModelProperty(value = "用户ID")
    protected Set<Long> uids;

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

    @ApiModelProperty(value = "是否启用: ENABLE:正常; DISABLE:禁用")
    protected Status enable;

}
