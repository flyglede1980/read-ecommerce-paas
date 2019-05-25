package net.yitun.ioften.adv.dicts;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import net.yitun.basic.dict.Dict;

/**
 * <pre>
 * <b>广告 内容触发动作类型.</b>
 * <b>Description:</b>
 *    动作类型, PAGE:落地页面, NEWS:站内资讯, GOODS:站内商品, APP_DOWN:下载APP
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
public enum Action implements Dict {

    /** 未知 */
    N(0, "未知"),

    /** 落地页面 */
    PAGE(1, "落地页面"),

    /** 站内资讯 */
    NEWS(3, "站内资讯"),

    /** 站内商品 */
    GOODS(5, "站内商品"),

    /** 下载APP */ // {'name':'SDT钱包','pageUrl':'http://cjdev.net/adv.html','apkDownUrl':'http://cjdev.net/sdt_1.0.apk','iosDownUrl':'http://cjdev.net/ios'}
    APP_DOWN(7, "下载APP");

    private int val;

    private String label;

    static final Map<Integer, Action> dicts = new HashMap<>();
    static final Map<Integer, String> labels = new LinkedHashMap<>();

    static {
        Action[] array = Action.values();
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

    public static Action valueOf(Integer val) {
        return dicts.get(val);
    }

}
