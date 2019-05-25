package net.yitun.ioften.crm.dicts;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import net.yitun.basic.dict.Dict;

/**
 * <pre>
 * <b>用户等级.</b>
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
public enum Level implements Dict {

    /** 普通 */
    N(0, "普通"),

    /** VIP1 */
    VIP1(1, "VIP1"),

    /** VIP2 */
    VIP2(3, "VIP2"),

    /** VIP3 */
    VIP3(5, "VIP3");

    private int val;

    private String label;

    static final Level[] array = Level.values();
    static final Map<Integer, Level> dicts = new HashMap<>();
    static final Map<Integer, String> labels = new LinkedHashMap<>();

    static {
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

    public Level nextLevel() {
        for (Level level : array)
            if (level.ordinal() > this.ordinal())
                return level;
        return this;
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

    public static Level valueOf(Integer val) {
        return dicts.get(val);
    }

}
