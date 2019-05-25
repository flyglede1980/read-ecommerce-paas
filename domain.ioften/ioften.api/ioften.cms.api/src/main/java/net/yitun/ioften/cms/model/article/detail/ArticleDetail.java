package net.yitun.ioften.cms.model.article.detail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.dict.YesNo;
import net.yitun.ioften.cms.dicts.ArticleStatus;
import net.yitun.ioften.cms.dicts.StockStatus;
import net.yitun.ioften.cms.dicts.Type;
import net.yitun.ioften.cms.model.category.ColumnClassesDetail;
import net.yitun.ioften.crm.dicts.Level;
import net.yitun.ioften.crm.model.user.UserDetail;

/**
 * <pre>
 * <b>资讯详细(后台).</b>
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
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"articleId"})
public class ArticleDetail implements Serializable {

    @ApiModelProperty(value = "文章编号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long articleId;

    @ApiModelProperty(value = "父级文章(针对系列文章)")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long parentId;

    @ApiModelProperty(value = "创建者, 用户ID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long usersId;

    @ApiModelProperty(value = "发布者帐号")
    protected Long phone;

    @ApiModelProperty(value = "作者信息")
    protected UserDetail author;

    @ApiModelProperty(value = "主标题, 标题长度不超过100个字符")
    protected String mainTitle;

    @ApiModelProperty(value = "副标题, 标题长度不超过100个字符")
    protected String subTitle;

    @ApiModelProperty(value = "摘要")
    protected String summary;

    @ApiModelProperty(value = "关键字, 多个逗号分隔, 标签长度为2-200个字符")
    protected String keyWord;

    @ApiModelProperty(value = "排序编号, 默认:0")
    protected Integer sortId;

    @ApiModelProperty(value = "投放服豆, 默认:0")
    protected Long beansNum;

    @ApiModelProperty(value = "是否热门: YES:热门; NO:普通")
    protected YesNo isHot;

    @ApiModelProperty(value = "是否推荐: YES:推荐; NO:普通")
    protected YesNo isRecommend;

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
    protected ArticleStatus status;

    @ApiModelProperty(value = "审批人ID")
    protected Long verifyId;

    @ApiModelProperty(value = "审批时间")
    protected Date verifyTime;

    @ApiModelProperty(value = "文章栏目")
    protected List<ColumnClassesDetail> classes;

    @ApiModelProperty(value = "统计指标")
    protected ArticleIndicatorsDetail indicators;

    @ApiModelProperty(value = "封面图片列表")
    protected List<CoverPicturesDetail> covers;

    @ApiModelProperty(value = "纯图文章封面图片数量")
    protected PicArticlesDetail picCoverNum;

    @ApiModelProperty(value = "纯图文章包含的图片文件列表")
    protected List<PicFilesDetail> pics;

    @ApiModelProperty(value = "图文文章")
    protected TextArticlesDetail text;

    @ApiModelProperty(value = "音视频文章")
    protected MediaArticlesDetail medias;

    @ApiModelProperty(value = "文章类型: TEXT:图文; IMAGE:纯图; VIDEO:视频; AUDIO:音频")
    protected Type type;

    @ApiModelProperty(value = "申请审核时间")
    protected Date applyTime;

    @ApiModelProperty(value = "操作描述")
    protected String reason;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public ArticleDetail addPic(PicFilesDetail pic) {
        if (null == pic)
            return this;

        if (null == this.pics)
            this.pics = new ArrayList<>();

        this.pics.add(pic);

        return this;
    }

    public ArticleDetail addClasses(ColumnClassesDetail classes) {
        if (null == classes)
            return this;

        if (null == this.classes)
            this.classes = new ArrayList<>();

        this.classes.add(classes);

        return this;
    }

    public ArticleDetail(Long articleId) {
        super();
        this.articleId = articleId;
    }

    public ArticleDetail(Long articleId, Long parentId, Long usersId, UserDetail author, String mainTitle, String keyWord, Integer sortId, Long beansNum, YesNo isCommented, YesNo shareSwitch, YesNo readSwitch, YesNo praiseSwitch, Level readLevel, Date ctime, Date mtime, StockStatus investStatus, Date issueTime, ArticleStatus status, Long verifyId, Date verifyTime, Type type, Date applyTime, String reason) {
        this.articleId = articleId;
        this.parentId = parentId;
        this.usersId = usersId;
        this.author = author;
        this.mainTitle = mainTitle;
        this.keyWord = keyWord;
        this.sortId = sortId;
        this.beansNum = beansNum;
        this.isCommented = isCommented;
        this.shareSwitch = shareSwitch;
        this.readSwitch = readSwitch;
        this.praiseSwitch = praiseSwitch;
        this.readLevel = readLevel;
        this.ctime = ctime;
        this.mtime = mtime;
        this.investStatus = investStatus;
        this.issueTime = issueTime;
        this.status = status;
        this.verifyId = verifyId;
        this.verifyTime = verifyTime;
        this.type = type;
        this.applyTime = applyTime;
        this.reason = reason;
    }

    public ArticleDetail(Long articleId, Long parentId, UserDetail author,String mainTitle, Long beansNum, YesNo shareSwitch, YesNo readSwitch, YesNo praiseSwitch, Level readLevel, Date ctime, Date mtime, StockStatus investStatus, Date issueTime, ArticleStatus status, Date verifyTime, Type type) {
        this.articleId = articleId;
        this.parentId = parentId;
        this.author = author;
        this.mainTitle = mainTitle;
        this.beansNum = beansNum;
        this.shareSwitch = shareSwitch;
        this.readSwitch = readSwitch;
        this.praiseSwitch = praiseSwitch;
        this.readLevel = readLevel;
        this.ctime = ctime;
        this.mtime = mtime;
        this.investStatus = investStatus;
        this.issueTime = issueTime;
        this.status = status;
        this.verifyTime = verifyTime;
        this.type = type;
    }

    public ArticleDetail(Long articleId, Long parentId, Long usersId, UserDetail author, String mainTitle, Date issueTime, ArticleStatus status, Type type, Date applyTime) {
        this.articleId = articleId;
        this.parentId = parentId;
        this.usersId = usersId;
        this.author = author;
        this.mainTitle = mainTitle;
        this.issueTime = issueTime;
        this.status = status;
        this.type = type;
        this.applyTime = applyTime;
    }
}