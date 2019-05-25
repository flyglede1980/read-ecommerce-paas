package net.yitun.ioften.adv.dicts;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import net.yitun.basic.dict.Dict;

/**
 * <pre>
 * <b>广告 内容显示位置.</b>
 * <b>Description:</b>
 *    位置, FLASH:闪屏广告, BANNER:资讯推荐页轮播, NEWS_LIST:资讯列表页, IMAGE_END:纯图文章图片查看结束后, ARTICLE_END:图文文章底部, VIDEO_PLAY_OVER:视频播放完毕后, MY_ADV_VIEW:我的页面广告
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月6日 下午5:53:16 LH
 *         new file.
 * </pre>
 */
@AllArgsConstructor
public enum Seat implements Dict {

    /** 未知 */
    N(0, "未知"),

    /** 闪屏广告 */
    FLASH(1, "闪屏广告"),

    /** 资讯推荐页轮播 */
    BANNER(3, "资讯推荐页轮播"),

    /** 资讯列表页 */
    NEWS_LIST(5, "资讯列表页"),

    /** 纯图文章图片查看结束后 */
    IMAGE_END(7, "纯图文章图片查看结束后"),

    /** 图文文章底部 */
    ARTICLE_END(9, " 图文文章底部"),

    /** 视频播放完毕后 */
    VIDEO_PLAY_OVER(13, "视频播放完毕后"),

    /** 我的页面广告 */
    MY_ADV_VIEW(15, "我的页面广告");

    private int val;

    private String label;

    static final Map<Integer, Seat> dicts = new HashMap<>();
    static final Map<Integer, String> labels = new LinkedHashMap<>();

    static {
        Seat[] array = Seat.values();
        for (int i = 0; i < array.length; i++) {
            dicts.put(array[i].val(), array[i]);
            labels.put(array[i].val(), array[i].label());
        }
    }

    @Override
    public int val() {
        return val;
    }

    @Override
    public String label() {
        return label;
    }

    @Override
    public Map<Integer, ? extends Dict> dicts() {
        return dicts;
    }

    public static Map<Integer, String> labels() {
        Map<Integer, String> _dicts = new LinkedHashMap<>(labels.size());
        _dicts.putAll(labels);
        return _dicts;
    }

    public static Seat valueOf(Integer val) {
        return dicts.get(val);
    }

}
