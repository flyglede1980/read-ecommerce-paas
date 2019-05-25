package net.yitun.ioften.adv.model.adv;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.yitun.ioften.adv.dicts.Seat;

/**
 * <pre>
 * <b>广告 内容新增.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月5日 下午5:38:46 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AdvCreate extends _AdvOpt {

    @NotNull(message = "位置无效")
    @ApiModelProperty(value = "位置, FLASH:闪屏广告, BANNER:资讯推荐页轮播, NEWS_LIST:资讯列表页, IMAGE_END:纯图文章图片查看结束后, ARTICLE_END:图文文章底部, VIDEO_PLAY_OVER:视频播放完毕后, MY_ADV_VIEW:我的页面广告", required = true)
    protected Seat seat;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
