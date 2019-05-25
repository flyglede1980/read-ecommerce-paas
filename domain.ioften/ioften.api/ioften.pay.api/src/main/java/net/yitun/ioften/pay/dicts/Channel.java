package net.yitun.ioften.pay.dicts;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import net.yitun.basic.dict.Dict;

/**
 * <pre>
 * <b>支付 支付通道.</b>
 * <b>Description:</b>
 *    支付通道, N:其他; APP:站内; BANK:银联; BLOCK:区块; ALIPAY:支付宝; WXPAY:微信支付
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
public enum Channel implements Dict {

    /** 其他 */
    N(0, "其他"),

    /** 站内 */
    APP(1, "站内"),

    /** 银联 */
    BANK(3, "银联"),

    /** 支付宝 */
    ALIPAY(5, "支付宝"),

    /** 微信支付 */
    WXPAY(7, "微信支付"),

    /** 区块 */
    BLOCK(9, "区块");

    private int val;

    private String label;

    static final Map<Integer, Channel> dicts = new HashMap<>();
    static final Map<Integer, String> labels = new LinkedHashMap<>();

    static {
        Channel[] array = Channel.values();
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

    public static Channel valueOf(Integer val) {
        return dicts.get(val);
    }
}
