package net.yitun.ioften.pay.dicts;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import net.yitun.basic.dict.Dict;

/**
 * <pre>
 * <b>支付 币种类型.</b>
 * <b>Description:</b>
 *    币种类型, N:其他, RMB, SDC, SDT
 *
 * <b>Author:</b> ZH
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
public enum Currency implements Dict {

    /** 其他 */
    N(0, "其他"),

    /** RMB */
    RMB(1, "RMB"),

    /** SDC */
    SDC(3, "SDC"),

    /** SDT */
    SDT(5, "SDT");

    private int val;

    private String label;

    static final Map<Integer, Currency> dicts = new HashMap<>();
    static final Map<Integer, String> labels = new LinkedHashMap<>();

    static {
        Currency[] array = Currency.values();
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

    public static Currency valueOf(Integer val) {
        return dicts.get(val);
    }
}
