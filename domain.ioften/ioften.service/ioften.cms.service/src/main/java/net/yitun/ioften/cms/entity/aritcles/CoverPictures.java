package net.yitun.ioften.cms.entity.aritcles;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.dict.Status;
/**
 * 封面图片信息
 * @since 1.0.0
 * @see ApiModelProperty
 * @author Flyglede
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CoverPictures {

    @ApiModelProperty(value = "图片编号")
    protected Long pictureId;

    @ApiModelProperty(value = "文章编号")
    protected Long articleId;

    @ApiModelProperty(value = "图片地址")
    protected String icon;

    @ApiModelProperty(value = "排序编号")
    protected Integer sortId;

    @ApiModelProperty(value = "是否启用: ENABLE:已启用; DISABLE:未启用")
    protected Status isEnabled;

}