package net.yitun.ioften.cms.entity.comment;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.dict.Status;
import net.yitun.ioften.crm.model.user.UserDetail;

/**
 * <pre>
 * <b>资讯 评论模型.</b>
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
@EqualsAndHashCode(of = {"id"})
public class Comment implements Serializable {

    @ApiModelProperty(value = "ID")
    protected Long id;

    @ApiModelProperty(value = "用户ID")
    protected Long uid;

    @ApiModelProperty(value = "层级, 默认:1")
    protected Integer level;

    @ApiModelProperty(value = "资讯ID")
    protected Long articleId;

    @ApiModelProperty(value = "一级评论ID,默认0")
    protected Long rootId;

    @ApiModelProperty(value = "父级评论ID")
    protected Long parentId;

    @ApiModelProperty(value = "评论内容, 评论最多1024个字符")
    protected String content;

    @ApiModelProperty(value = "点赞次数")
    protected Integer enjoys;

    @ApiModelProperty(value = "评论次数")
    protected Integer comments;

    @ApiModelProperty(value = "权重")
    protected  Integer weight;

    @ApiModelProperty(value = "状态, ENABLE:正常; DISABLE:禁用")
    protected Status status;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    @ApiModelProperty(value = "用户信息")
    protected UserDetail user;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public Comment(Long id) {
        super();
        this.id = id;
    }

}
