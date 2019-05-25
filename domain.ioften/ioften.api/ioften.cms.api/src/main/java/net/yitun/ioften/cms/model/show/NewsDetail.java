package net.yitun.ioften.cms.model.show;

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
import net.yitun.ioften.cms.model.article.detail.ArticleIndicatorsDetail;
import net.yitun.ioften.cms.model.article.detail.MediaArticlesDetail;
import net.yitun.ioften.cms.model.article.detail.PicFilesDetail;
import net.yitun.ioften.cms.model.article.detail.TextArticlesDetail;
import net.yitun.ioften.cms.model.category.ColumnClassesDetail;
import net.yitun.ioften.crm.dicts.Level;
import net.yitun.ioften.crm.model.user.UserDetail;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"articleId"})
public class NewsDetail {

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

    @ApiModelProperty(value = "主标题, 标题长度不超过100个字符")
    protected String mainTitle;

    @ApiModelProperty(value = "摘要(简介)")
    protected String sumMary;

    @ApiModelProperty(value = "关键字, 多个逗号分隔, 标签长度为2-200个字符")
    protected String keyWord;

    @ApiModelProperty(value = "投放服豆, 默认:0")
    protected Long beansNum;

    @ApiModelProperty(value = "充值系数")
    protected Float rechargeRatio;

    @ApiModelProperty(value = "投放状态: NONE:未投放; PROCESS:投放中; OVER:已投放")
    protected StockStatus investStatus;

    @ApiModelProperty(value = "文章类型: TEXT:图文; IMAGE:纯图; VIDEO:视频; AUDIO:音频")
    protected Type type;

    @ApiModelProperty(value = "是否热门: YES:热门; NO:普通")
    protected YesNo isHot;

    @ApiModelProperty(value = "是否推荐: YES:推荐; NO:普通")
    protected YesNo isRecommend;

    @ApiModelProperty(value = "是否置顶: YES:置顶; NO:正常")
    protected YesNo isTop;

    @ApiModelProperty(value = "是否需要授权: YES:需要授权; NO:不需授权")
    protected YesNo isAuthorized;

    @ApiModelProperty(value = "统计指标")
    protected ArticleIndicatorsDetail indicators;

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

    @ApiModelProperty(value = "发行时间, 默认当前时间")
    protected Date issueTime;

    @ApiModelProperty(value = "状态, DRAFT:草稿; CHECK:审核中; REFUSE:已拒绝; ISSUE:已发布; WAIT_ISSUE:待发布; OFFLINE:已下线; DELETE:已删除")
    protected ArticleStatus status;

    @ApiModelProperty(value = "文章栏目")
    protected List<ColumnClassesDetail> classes;

    @ApiModelProperty(value = "纯图文章包含的图片文件列表")
    protected List<PicFilesDetail> pics;

    @ApiModelProperty(value = "音视频文章")
    protected MediaArticlesDetail medias;

    @ApiModelProperty(value = "图文文章")
    protected TextArticlesDetail text;

    @ApiModelProperty(value = "作者, 作者名称为2-16个字符")
    protected UserDetail author;

    public NewsDetail addPic(PicFilesDetail pic) {
        if (null == pic)
            return this;

        if (null == this.pics)
            this.pics = new ArrayList<>();

        this.pics.add(pic);

        return this;
    }

    public NewsDetail addClasses(ColumnClassesDetail classes) {
        if (null == classes)
            return this;

        if (null == this.classes)
            this.classes = new ArrayList<>();

        this.classes.add(classes);

        return this;
    }

    public NewsDetail(String mainTitle) {
        super();
        this.mainTitle = mainTitle;
    }

    public NewsDetail(Long articleId, Long usersId, String mainTitle, Long beansNum, Float rechargeRatio, YesNo shareSwitch,
            YesNo readSwitch, YesNo praiseSwitch) {
        this.articleId = articleId;
        this.usersId = usersId;
        this.mainTitle = mainTitle;
        this.beansNum = beansNum;
        this.rechargeRatio = rechargeRatio;
        this.shareSwitch = shareSwitch;
        this.readSwitch = readSwitch;
        this.praiseSwitch = praiseSwitch;
    }


}
