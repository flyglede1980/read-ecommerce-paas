package net.yitun.ioften.cms.dicts;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import net.yitun.basic.dict.Dict;
import net.yitun.basic.dict.Status;

/**
 * <pre>
 * <b>资讯 模式字典.</b>
 * <b>Description:</b>
 * 模式, N:其他; TEXT:文章; IMAGE:图片; VIDEO:音视频
 *
 * <b>Author:</b> XJN
 * <b>Date:</b> 2018-11-12 11:17:00.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月12日 上午11:17:06 XJN
 *         new file.
 * </pre>
 */
@AllArgsConstructor
public enum Type implements Dict {

    /** 文章 */
    TEXT(1222, "文章"),

    /** 图片 */
    IMAGE(2122, "图片"),

    /** 视频 */
    VIDEO(2212, "音视频"),

    /** 音频 */
    AUDIO(2222, "音视频");

    private int val;

    private String label;

    static final Map<Integer, Status> dicts = new HashMap<>();
    static final Map<Integer, String> labels = new LinkedHashMap<>();

    static {
        Status[] array = Status.values();
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

    public static Status valueOf(Integer val) {
        return dicts.get(val);
    }

}
