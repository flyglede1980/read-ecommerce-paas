package net.yitun.ioften.pay.dicts;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import net.yitun.basic.dict.Dict;

/**
 * <pre>
 * <b>支付 流水用途.</b>
 * <b>Description:</b>
 *    流水用途: N:其他; VIEW:阅读; SHARE:分享; ENJOY:点赞; INVITE:邀请; GIVEIN:获得打赏; GIVEOUT:打赏他人; ADVIN:广告收入; AWARDOUT:设置奖励 SHOP:购物; FETCH:提币; ADDED:充值
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月8日 下午5:44:39
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月8日 下午5:44:39 LH
 *         new file.
 * </pre>
 */
@AllArgsConstructor
public enum Way implements Dict {

    /** 其他 */
    N(0, "其他"),

    /** 阅读 */
    VIEW(1, "阅读"),

    /** 点赞 */
    ENJOY(3, "点赞"),

    /** 分享 */
    SHARE(5, "分享"),

    /** 邀请 */
    INVITE(7, "邀请"),

    /** 获得打赏 */
    GIVEIN(9, "获得打赏"),

    /** 打赏他人 */
    GIVEOUT(11, "打赏他人"),

    /** 广告收入 */
    ADVIN(13, "广告收入"),

    /** 设置奖励 */
    AWARDOUT(15, "设置奖励"),

    /** 购物 */
    SHOP(17, "购物"),

    /** 提币 */
    FETCH(19, "提币"),

    /** 充值 */
    ADDED(21, "充值");

    private int val;

    private String label;

    static final Map<Integer, Way> dicts = new HashMap<>();
    static final Map<Integer, String> labels = new LinkedHashMap<>();

    static {
        Way[] array = Way.values();
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

    public static Way valueOf(Integer val) {
        return dicts.get(val);
    }
}
