package net.yitun.ioften.cms.entity.aritcles;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 音频视频文章信息
 * @since 1.0.0
 * @see ApiModelProperty
 * @author Flyglede
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MediaArticles{

    @ApiModelProperty(value = "文章编号")
    protected Long articleId;

    @ApiModelProperty(value = "链接地址")
    protected String linkUrl;

    @ApiModelProperty(value = "文件大小(KB)")
    protected Integer fileSize;

    @ApiModelProperty(value = "时长(分钟数)")
    protected Integer minutes;

    @ApiModelProperty(value = "文件描述")
    protected String description;

}