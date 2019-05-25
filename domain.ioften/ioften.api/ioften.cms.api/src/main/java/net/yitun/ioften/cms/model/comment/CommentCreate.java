package net.yitun.ioften.cms.model.comment;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.Util;

/**
 * <pre>
 * <b>发布评论.</b>
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
public class CommentCreate implements Serializable {

    @ApiModelProperty(value = "父级评论ID",required = false)
    protected Long parentId;

    @ApiModelProperty(value = "资讯ID", required = true)
    @NotNull(message = "资讯ID无效")
    @Min(value = Util.MIN_ID, message = "资讯ID无效")
    protected Long articleId;

    @ApiModelProperty(value = "评论内容, 评论最多1024个字符", required = true)
    @NotBlank(message = "内容不能为空")
    @Size(max = 1024, message = "评论最多1024个字符")
    protected String content;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
