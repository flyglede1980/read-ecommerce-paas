package net.yitun.ioften.cms.entity.comment;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.cms.model.comment.CommentUser;
import net.yitun.ioften.crm.dicts.Level;
import net.yitun.ioften.crm.dicts.Type;

/**
 * @Author ：ZH.
 * @Date ：Created in 18:55 2018/12/19 0019
 * @Description：评论回复实体
 * @Modified By：
 * @Version: 0.1
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class CommentReply implements Serializable {
    @ApiModelProperty(value = "评论ID")
    protected Long id;

    @ApiModelProperty(value = "评论用户ID")
    protected Long uid;

    @ApiModelProperty(value = "层级")
    protected Integer level;

    @ApiModelProperty(value = "一级评论ID,默认0")
    protected Long rootId;

    @ApiModelProperty(value = "父级ID")
    protected Long parentId;

    @ApiModelProperty(value = "评论内容, 评论最多1024个字符")
    protected String content;

    @ApiModelProperty(value = "点赞次数")
    protected Integer enjoys;

    @ApiModelProperty(value = "评论次数")
    protected Integer comments;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "评论用户昵称, 长度为2~16个字符")
    protected String nick;

    @ApiModelProperty(value = "评论用户类型, N:无, PE:个人, EN:企业, EW:长见号")
    protected Type type;

    @ApiModelProperty(value = "评论用户等级, N, VIP1, VIP2, ....")
    protected Level userLevel;

    @ApiModelProperty(value = "被评论用户信息")
    protected CommentUser commentUser;

    /* SVUID */
    private static final long serialVersionUID = 1L;
}
