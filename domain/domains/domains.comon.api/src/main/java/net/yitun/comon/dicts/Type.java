package net.yitun.comon.dicts;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import net.yitun.basic.dict.Dict;

/**
 * <pre>
 * <b>验证码类型.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月12日 下午7:09:33 LH
 *         new file.
 * </pre>
 */
public enum Type implements Dict {

    IMG,

    SMS,

    CAS;

    private int val;

    private String label;

    static final Map<Integer, Type> dicts = new HashMap<>();
    static final Map<Integer, String> labels = new LinkedHashMap<>();

    static {
        Type[] array = Type.values();
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

    public static Type valueOf(Integer val) {
        return dicts.get(val);
    }
}
