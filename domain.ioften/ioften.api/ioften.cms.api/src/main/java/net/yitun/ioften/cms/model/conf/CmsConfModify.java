package net.yitun.ioften.cms.model.conf;

import java.io.Serializable;
import java.util.LinkedHashSet;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * <pre>
 * <b>资讯 修改资讯配置.</b>
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
public class CmsConfModify implements Serializable {

    @Min(value = 1, message = "资讯每页个数范围: 1~32 个")
    @Max(value = 32, message = "资讯每页个数范围: 1~32 个")
    @ApiModelProperty(value = "资讯每页个数, 范围: 1~32 个", required = true)
    protected int showSize = 10;

    @Size(min = 0, max = 128, message = "推荐频道栏目数量限制: 0~8 个")
    @ApiModelProperty(value = "推荐频道栏目, 数量限制: 0~128 个", required = true)
    protected LinkedHashSet<TopicItem> recTopics = new LinkedHashSet<>();

    @Size(min = 0, max = 8, message = "初始导航栏目数量限制: 0~8 个")
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

    @Min(value = 0, message = "广告点击资费范围: 0~92233720368")
    @Max(value = Long.MAX_VALUE, message = "广告点击资费范围: 0~92233720368")
    @ApiModelProperty(value = "广告资费: 每点击一次 ? 服豆, 范围: 0~92233720368", required = true)
    protected Long advFeePerClick = 0L;

    @Min(value = 0, message = "广告展现资费范围: 0~92233720368")
    @Max(value = Long.MAX_VALUE, message = "广告展现资费范围: 0~92233720368")
    @ApiModelProperty(value = "广告资费: 每1000次展现 ? 服豆, 范围: 0~10000", required = true)
    protected Long advShowFeePerMille = 0L;

    @ApiModelProperty(value = "广告资费: 按默认资费计费，不可单独更改, true: 默认; false: 关", required = true)
    protected boolean advFeeRuleLock = false;

    @Size(min = 0, max = 128, message = "置顶文章数量限制: 0~8 个")
    @ApiModelProperty(value = "置顶文章, 数量限制: 0~8 个", required = true)
    protected LinkedHashSet<TotopItem> totopNews = new LinkedHashSet<>();

    @Min(value = 3, message = "图文文章末尾推荐文章数数量限制: 3~8 个")
    @Max(value = 8, message = "图文文章末尾推荐文章数数量限制: 3~8 个")
    @ApiModelProperty(value = "图文文章末尾推荐文章数, 数量限制: 3~8 个", required = true)
    protected Integer articleEndRecSize = 3;

    @Min(value = 3, message = "纯图文章末尾推荐文章数数量限制: 3~8 个")
    @Max(value = 8, message = "纯图文章末尾推荐文章数数量限制: 3~8 个")
    @ApiModelProperty(value = "纯图文章末尾推荐文章数, 数量限制: 3~8 个", required = true)
    protected Integer imageEndRecSize = 3;

    /* SVUID */
    private static final long serialVersionUID = 1L;

}
