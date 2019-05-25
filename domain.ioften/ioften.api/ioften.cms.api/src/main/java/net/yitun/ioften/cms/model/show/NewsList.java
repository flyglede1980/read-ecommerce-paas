package net.yitun.ioften.cms.model.show;

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
import net.yitun.ioften.cms.dicts.Type;
import net.yitun.ioften.cms.model.article.detail.CoverPicturesDetail;
import net.yitun.ioften.cms.model.article.detail.MediaArticlesDetail;
import net.yitun.ioften.crm.dicts.Level;
import net.yitun.ioften.crm.model.user.UserDetail;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "articleId" })
public class NewsList {

    @ApiModelProperty(value = "文章编号")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long articleId;

    @ApiModelProperty(value = "创建者")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    protected Long userId;

    @ApiModelProperty(value = "主标题, 标题长度不超过100个字符")
    protected String mainTitle;

    @ApiModelProperty(value = "摘要(简介)")
    protected String sumMary;

    @ApiModelProperty(value = "文章类型: TEXT:图文; IMAGE:纯图; VIDEO:视频; AUDIO:音频")
    protected Type type;

    @ApiModelProperty(value = "阅读等级, N, VIP1, VIP2 ...")
    protected Level readLevel;

    @ApiModelProperty(value = "发行时间, 默认当前时间")
    protected Date issueTime;

    @ApiModelProperty(value = "状态, DRAFT:草稿; CHECK:审核中; REFUSE:已拒绝; ISSUE:已发布; WAIT_ISSUE:待发布; OFFLINE:已下线; DELETE:已删除")
    protected ArticleStatus status;

    @ApiModelProperty(value = "封面图片列表")
    protected List<CoverPicturesDetail> coverPics;

    @ApiModelProperty(value = "音视频文章包含的媒体文件列表")
    protected MediaArticlesDetail medias;

    @ApiModelProperty(value = "投放服豆")
    protected Long beansNum;

    @ApiModelProperty(value = "点赞次数")
    protected Integer praiseNum;

    @ApiModelProperty(value = "评论次数")
    protected Integer commentNum;

    @ApiModelProperty(value = "作者, 作者名称为2-16个字符")
    protected UserDetail author;

    @ApiModelProperty(value = "审核时间")
    protected Date verifyTime;

    @ApiModelProperty(value = "是否置顶: YES:是; NO:否")
    protected YesNo top;

    public NewsList(Long articleId, Long userId, String mainTitle, Type type, Date issueTime,
                    ArticleStatus status, List<CoverPicturesDetail> coverPics, MediaArticlesDetail medias,
                    UserDetail author) {
        this.articleId = articleId;
        this.userId = userId;
        this.mainTitle = mainTitle;
        this.type = type;
        this.issueTime = issueTime;
        this.status = status;
        this.coverPics = coverPics;
        this.medias = medias;
        this.author = author;
    }

    public NewsList(Long articleId, String mainTitle, String sumMary, List<CoverPicturesDetail> coverPics) {
        this.articleId = articleId;
        this.mainTitle = mainTitle;
        this.sumMary = sumMary;
        this.coverPics = coverPics;
    }
}
