package net.yitun.basic.dict;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AllArgsConstructor;

/**
 * <pre>
 * <b>是否字典.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年8月22日 下午3:17:39 LH
 *         new file.
 * </pre>
 */
@AllArgsConstructor
public enum YesNo implements Dict {

    /** 否 */
    NO(0, "否"),

    /** 是 */
    YES(1, "是");

    private int val;

    private String label;

    static final Map<Integer, YesNo> dicts = new HashMap<>();
    static final Map<Integer, String> labels = new LinkedHashMap<>();

    static {
        YesNo[] array = YesNo.values();
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

    public static YesNo valueOf(Integer val) {
        return dicts.get(val);
    }

}
