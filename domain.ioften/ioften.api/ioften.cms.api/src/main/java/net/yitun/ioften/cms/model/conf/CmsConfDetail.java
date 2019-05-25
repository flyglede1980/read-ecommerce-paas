package net.yitun.ioften.cms.model.conf;

import java.io.Serializable;
import java.util.LinkedHashSet;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <pre>
 * <b>资讯 资讯配置信息.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月4日 上午11:57:23 LH
 *         new file.
 * </pre>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CmsConfDetail implements Serializable {

    @ApiModelProperty(value = "资讯每页个数, 范围: 1~32 个", required = true)
    protected int showSize = 10;

    @ApiModelProperty(value = "推荐频道栏目, 数量限制: 0~128 个", required = true)
    protected LinkedHashSet<TopicItem> recTopics = new LinkedHashSet<>();

    @ApiModelProperty(value = "初始导航栏目, 数量限制: 0~18 个", required = true)
    protected LinkedHashSet<TopicItem> initTopics = new LinkedHashSet<>();

    @ApiModelProperty(value = "广告开关: 闪屏广告(APP启动), true: 开; false: 关", required = true)
    protected boolean advStartupSwitch = false;

    @ApiModelProperty(value = "广告开关: 推荐频道轮播, true: 开; false: 关", required = true)
    protected boolean advRecBannerSwitch = false;

    @ApiModelProperty(value = "广告开关: 图文文章页末尾处, true: 开; false: 关", required = true)
    protected boolean advArticleEndSwitch = false;

    @ApiModelProperty(value = "广告开关: 纯图文章页末尾处, true: 开; false: 关", required = true)
    protected boolean advImageEndSwitch = false;

    @ApiModelProperty(value = "广告开关: 视频文章播放完毕后, true: 开; false: 关", required = true)
    protected boolean advVideoPlayOverSwitch = false;

    @ApiModelProperty(value = "广告开关: 推荐频道资讯列表中, true: 开; false: 关", required = true)
    protected boolean advRecNewsListSwitch = false;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    @ApiModelProperty(value = "广告资费: 每点击一次 ? 服豆, 范围: 0~10000", required = true)
    protected Long advFeePerClick = 0L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JSONField(serializeUsing = ToStringSerializer.class)
    @ApiModelProperty(value = "广告资费: 每1000次展现 ? 服豆, 范围: 0~10000", required = true)
    protected Long advShowFeePerMille = 0L;

    @ApiModelProperty(value = "广告资费: 按默认资费计费，不可单独更改, true: 默认; false: 关", required = true)
    protected boolean advFeeRuleLock = false;

    @ApiModelProperty(value = "置顶文章, 数量限制: 0~8 个", required = true)
    protected LinkedHashSet<TotopItem> totopNews = new LinkedHashSet<>();

    @ApiModelProperty(value = "图文文章末尾推荐文章数, 数量限制: 3~8 个", required = true)
    protected Integer articleEndRecSize = 3;

    @ApiModelProperty(value = "纯图文章末尾推荐文章数, 数量限制: 3~8 个", required = true)
    protected Integer imageEndRecSize = 3;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
