package net.yitun.ioften.crm.dicts;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import net.yitun.basic.dict.Dict;

/**
 * <pre>
 * <b>第三方.</b>
 * <b>Description:</b>
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
public enum Third implements Dict {

    /** QQ */
    QQ(1, "QQ"),

    /** 微信 */
    WXIN(3, "微信"),

    /** 微博 */
    WEIBO(5, "微博"),

    /** 亿豚网 */
    YITUN(99, "亿豚网");

    private int val;

    private String label;

    static final Map<Integer, Third> dicts = new HashMap<>();
    static final Map<Integer, String> labels = new LinkedHashMap<>();

    static {
        Third[] array = Third.values();
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

    public static Third valueOf(Integer val) {
        return dicts.get(val);
    }

}
