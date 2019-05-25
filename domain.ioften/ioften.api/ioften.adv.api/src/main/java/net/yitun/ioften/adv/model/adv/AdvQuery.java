package net.yitun.ioften.adv.model.adv;

import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.Util;
import net.yitun.basic.dict.Status;
import net.yitun.basic.model.Page;
import net.yitun.ioften.adv.dicts.Action;
import net.yitun.ioften.adv.dicts.Seat;

/**
 * <pre>
 * <b>广告 内容分页查询.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月6日 上午10:36:32 LH
 *         new file.
 * </pre>
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
public class AdvQuery extends Page {

    @ApiModelProperty(value = "ID")
    @Min(value = Util.MIN_ID, message = "ID无效")
    protected Long id;

    @ApiModelProperty(value = "标题, 长度: 2~32个字符")
    @Size(min = 2, max = 32, message = "标题长度为2~32个字符")
    protected String title;

    @ApiModelProperty(value = "是否显示\"查看详情\"按钮")
    protected Boolean showBtn;

    @ApiModelProperty(value = "位置, FLASH:闪屏广告, BANNER:资讯推荐页轮播, NEWS_LIST:资讯列表页, IMAGE_END:纯图文章图片查看结束后, ARTICLE_END:图文文章底部, VIDEO_PLAY_OVER:视频播放完毕后, MY_ADV_VIEW:我的页面广告")
    protected Set<Seat> seat;

    @ApiModelProperty(value = "动作类型, PAGE:落地页面, NEWS:站内资讯, GOODS:站内商品, APP_DOWN:下载APP")
    protected Set<Action> action;

    @ApiModelProperty(value = "状态, DISABLE:禁用; ENABLE:正常")
    protected Set<Status> status;

    public AdvQuery() {
        // 排序规则, 默认: ID DESC
        super(null, null, null, "t1.id desc");
    }

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
