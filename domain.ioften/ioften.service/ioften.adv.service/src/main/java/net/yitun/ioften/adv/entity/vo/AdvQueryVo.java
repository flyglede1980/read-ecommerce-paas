package net.yitun.ioften.adv.entity.vo;

import java.util.Collection;
import java.util.Set;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.yitun.basic.dict.Status;
import net.yitun.ioften.adv.dicts.Action;
import net.yitun.ioften.adv.dicts.Seat;
import net.yitun.ioften.adv.entity.Adv;

/**
 * <pre>
 * <b>广告 内容查询Vo.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月6日 下午1:55:54 LH
 *         new file.
 * </pre>
 */
@Getter
@Setter
@ToString
public class AdvQueryVo extends Adv {

    @ApiModelProperty(value = "位置, BANNER:资讯推荐页轮播, FLASH:闪屏广告, NEWS_LIST:资讯列表页, IMAGE_END:纯图文章图片查看结束后, ARTICLE_END:图文文章底部, VIDEO_PLAY_OVER:视频播放完毕后, MY_ADV_VIEW:我的页面广告")
    protected Collection<Seat> seats;

    @ApiModelProperty(value = "动作类型, PAGE:落地页面, NEWS:站内资讯, GOODS:站内商品, APP_DOWN:下载APP")
    protected Collection<Action> actions;

    @ApiModelProperty(value = "状态, DISABLE:禁用; ENABLE:正常")
    protected Collection<Status> statuss;

    /* SVUID */
    private static final long serialVersionUID = 1L;

    public AdvQueryVo(Long id, String title, Boolean showBtn, Set<Seat> seats, Set<Action> actions, Set<Status> status) {
        super(id, title, null /* cover */, null /* seat */, null /* action */, showBtn, null /* actionConf */,
                null /* sequence */, null /* remark */, null /* status */, null/* ctime */, null/* mtime */);
        this.seats = seats;
        this.actions = actions;
        this.statuss = status;
    }

}
