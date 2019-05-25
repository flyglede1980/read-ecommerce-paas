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
 * @Description：最新评论分页查询
 * @Modified By：
 * @Version: 0.1
 **/
@Setter
@Getter
@ToString
public class CommentNewQuery extends Page {

    @ApiModelProperty(value = "咨询ID",required = true,allowEmptyValue = false)
    @NotNull(message = "咨询ID不能为空")
    protected Long articleId;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public CommentNewQuery() {
        // 排序规则, 默认: ID DESC
        super(null, null, null, "t1.ctime desc");
    }
}
