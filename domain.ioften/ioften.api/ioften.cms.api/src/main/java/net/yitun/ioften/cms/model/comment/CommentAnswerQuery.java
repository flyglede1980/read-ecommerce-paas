package net.yitun.ioften.cms.model.comment;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.model.Page;

/**
 * @Author ：ZH.
 * @Date ：Created in 15:32 2018/12/18 0018
 * @Description：热门回复分页查询
 * @Modified By：
 * @Version: 0.1
 **/
@Setter
@Getter
@ToString
public class CommentAnswerQuery extends Page {

    @ApiModelProperty(value = "一级评论ID",required = true,allowEmptyValue = false)
    @NotNull(message = "一级评论ID不能为空")
    protected Long rootId;

    @ApiModelProperty(value = "用户ID")
    protected Long uid;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
