package net.yitun.ioften.cms.entity.aritcles;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 文章栏目关系
 * @since 1.0.0
 * @see ApiModelProperty
 * @author Flyglede
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ArticleColumnClasses {

    @ApiModelProperty(value = "关系编号")
    protected Long relationId;

    @ApiModelProperty(value = "分类编号")
    protected Long classId;

    @ApiModelProperty(value = "文章编号")
    protected Long articleId;

}