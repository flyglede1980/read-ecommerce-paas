package net.yitun.ioften.cms.model.article;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.dict.YesNo;
import net.yitun.ioften.cms.dicts.ArticleStatus;

/**
 * <pre>
 * <b>发布资讯.</b>
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
public class ArticleCreate implements Serializable {

    @ApiModelProperty(value = "父级文章(针对系列文章)")
    protected Long parentId;

    @NotBlank(message = "主标题不能为空")
    @Size(min = 4, max = 100, message = "主标题, 长度为4-100个字符")
    @ApiModelProperty(value = "主标题, 标题长度为4-100个字符", required = true)
    protected String mainTitle;

    @Size(min = 4, max = 100, message = "副标题, 长度为4-100个字符")
    @ApiModelProperty(value = "副标题, 标题长度为4-100个字符")
    protected String subTitle;

    @ApiModelProperty(value = "摘要")
    protected String summary;

    @Size(min = 2, max = 200, message = "关键字, 长度为2-200个字符")
    @ApiModelProperty(value = "关键字, 多个逗号分隔, 标签长度为2-200个字符")
    protected String keyWord;

    @ApiModelProperty(value = "排序编号, 默认:0")
    protected Integer sortId;

    @ApiModelProperty(value = "是否允许评论: YES:允许评论; NO:禁止评论")
    protected YesNo isCommented;

    @ApiModelProperty(value = "发行时间, 默认当前时间")
    protected Date issueTime;

    @ApiModelProperty(value = "状态, DRAFT:草稿; CHECK:审核中; REFUSE:已拒绝; ISSUE:已发布; WAIT_ISSUE:待发布; OFFLINE:已下线; DELETE:已删除")
    protected ArticleStatus status;

    @ApiModelProperty(value = "封面图片")
    protected List<CoverPicturesCreate> coverPictures;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
