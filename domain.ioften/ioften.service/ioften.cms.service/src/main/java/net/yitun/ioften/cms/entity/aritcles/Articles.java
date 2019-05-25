package net.yitun.ioften.cms.entity.aritcles;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.dict.Status;
import net.yitun.basic.dict.YesNo;
import net.yitun.ioften.cms.dicts.ArticleStatus;
import net.yitun.ioften.cms.dicts.StockStatus;
import net.yitun.ioften.cms.dicts.Type;
import net.yitun.ioften.crm.dicts.Level;

/**
 * 文章信息
 *
 * @author Flyglede
 * @see ApiModelProperty
 * @since 1.0.0
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Articles {
    @ApiModelProperty(value = "文章编号")
    protected Long articleId;
    @ApiModelProperty(value = "父级文章(针对系列文章)")
    protected Long parentId;
    @ApiModelProperty(value = "创建者, 用户ID")
    protected Long usersId;
    @ApiModelProperty(value = "主标题, 标题长度不超过100个字符")
    protected String mainTitle;
    @ApiModelProperty(value = "副标题, 标题长度不超过100个字符")
    protected String subTitle;
    @ApiModelProperty(value = "摘要")
    protected String summary;
    @ApiModelProperty(value = "关键字, 多个逗号分隔, 标签长度不超过200个字符")
    protected String keyWord;
    @ApiModelProperty(value = "排序编号, 默认:0")
    protected Integer sortId;
    @ApiModelProperty(value = "投放服豆, 默认:0")
    protected Long beansNum;
    @ApiModelProperty(value = "是否热门: YES:热门; NO:普通")
    protected YesNo isHot;
    @ApiModelProperty(value = "是否推荐: YES:推荐; NO:普通")
    protected YesNo isRecommend;
    @ApiModelProperty(value = "是否启用: ENABLE:已启用; DISABLE:未启用")
    protected Status isEnabled;
    @ApiModelProperty(value = "是否置顶: YES:置顶; NO:正常")
    protected YesNo isTop;
    @ApiModelProperty(value = "是否需要授权: YES:需要授权; NO:不需授权")
    protected YesNo isAuthorized;
    @ApiModelProperty(value = "是否允许评论: YES:允许评论; NO:禁止评论")
    protected YesNo isCommented;
    @ApiModelProperty(value = "分享挖矿开关: YES:开启; NO:关闭")
    protected YesNo shareSwitch;
    @ApiModelProperty(value = "阅读挖矿开关: YES:开启; NO:关闭")
    protected YesNo readSwitch;
    @ApiModelProperty(value = "点赞挖矿开关: YES:开启; NO:关闭")
    protected YesNo praiseSwitch;
    @ApiModelProperty(value = "阅读等级, N, VIP1, VIP2 ...")
    protected Level readLevel;
    @ApiModelProperty(value = "创建时间")
    protected Date ctime;
    @ApiModelProperty(value = "修改时间")
    protected Date mtime;
    @ApiModelProperty(value = "充值系数")
    protected Float rechargeRatio;
    @ApiModelProperty(value = "追投数量")
    protected Long addInvest;
    @ApiModelProperty(value = "投放状态: NONE:未投放; PROCESS:投放中; OVER:已投放")
    protected StockStatus investStatus;
    @ApiModelProperty(value = "发行时间, 默认当前时间")
    protected Date issueTime;
    @ApiModelProperty(value = "状态, DRAFT:草稿; CHECK:审核中; REFUSE:已拒绝; ISSUE:已发布; WAIT_ISSUE:待发布; OFFLINE:已下线; DELETE:已删除")
    protected ArticleStatus issueStatus;
    @ApiModelProperty(value = "审批人ID")
    protected Long verifyId;
    @ApiModelProperty(value = "审批时间")
    protected Date verifyTime;
    @ApiModelProperty(value = "文章类型: TEXT:图文; IMAGE:纯图; VIDEO:音视频")
    protected Type type;
    @ApiModelProperty(value = "申请审核时间")
    protected Date applyTime;
    @ApiModelProperty(value = "操作描述")
    protected String reason;

    public void setType(String type) {
        int t = Integer.parseInt(type);
        if (Type.VIDEO.val() == t)
            this.type = Type.VIDEO;
        else if (Type.TEXT.val() == t)
            this.type = Type.TEXT;
        else if (Type.IMAGE.val() == t)
            this.type = Type.IMAGE;
        else if (Type.AUDIO.val() == t)
            this.type = Type.AUDIO;
    }

    public Articles(Long articleId, YesNo isHot, YesNo isRecommend, YesNo isTop, YesNo isAuthorized, Level readLevel, Date mtime, Date issueTime, ArticleStatus issueStatus) {
        this.articleId = articleId;
        this.isHot = isHot;
        this.isRecommend = isRecommend;
        this.isTop = isTop;
        this.isAuthorized = isAuthorized;
        this.readLevel = readLevel;
        this.mtime = mtime;
        this.issueTime = issueTime;
        this.issueStatus = issueStatus;
    }
}