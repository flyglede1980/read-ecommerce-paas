package net.yitun.ioften.adv.entity;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import net.yitun.basic.dict.Status;
import net.yitun.ioften.adv.dicts.Action;
import net.yitun.ioften.adv.dicts.Seat;

/**
 * <pre>
 * <b>广告 内容模型.</b>
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
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = { "id" })
public class Adv implements Serializable {

    @ApiModelProperty(value = "ID")
    protected Long id;

    @ApiModelProperty(value = "标题, 长度: 2~32个字符")
    protected String title;

    @ApiModelProperty(value = "封面图片, 多张逗号分隔, 长度: 0~1024")
    protected String cover;

    @ApiModelProperty(value = "位置, FLASH:闪屏广告, BANNER:资讯推荐页轮播, NEWS_LIST:资讯列表页, IMAGE_END:纯图文章图片查看结束后, ARTICLE_END:图文文章底部, VIDEO_PLAY_OVER:视频播放完毕后, MY_ADV_VIEW:我的页面广告")
    protected Seat seat;

    @ApiModelProperty(value = "动作类型, PAGE:落地页面, NEWS:站内资讯, GOODS:站内商品, APP_DOWN:下载APP")
    protected Action action;

    @ApiModelProperty(value = "是否显示\"查看详情\"按钮")
    protected Boolean showBtn;

    @ApiModelProperty(value = "动作配置, 下载APP->Json格式, 其他为字符串, 长度: 0~2048")
    protected String actionConf;

    @ApiModelProperty(value = "顺序, 范围: 0~9999, 升序排列")
    protected Integer sequence;

    @ApiModelProperty(value = "备注, 长度: 0~255")
    protected String remark;

    @ApiModelProperty(value = "状态, DISABLE:禁用; ENABLE:正常")
    protected Status status;

    @ApiModelProperty(value = "创建时间")
    protected Date ctime;

    @ApiModelProperty(value = "修改时间")
    protected Date mtime;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
