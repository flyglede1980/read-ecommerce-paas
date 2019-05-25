package net.yitun.ioften.cms.entity.aritcles;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.ioften.cms.dicts.CoverNum;
/**
 * 图文文章信息
 * @since 1.0.0
 * @see ApiModelProperty
 * @author Flyglede
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TextArticles {

    @ApiModelProperty(value = "文章编号")
    protected Long articleId;

    @ApiModelProperty(value = "文章详情")
    protected String details;

    @ApiModelProperty(value = "封面图数量: SIMPLE:单图; THREE:三图; AUTO:自动")
    protected CoverNum coverNum;

}