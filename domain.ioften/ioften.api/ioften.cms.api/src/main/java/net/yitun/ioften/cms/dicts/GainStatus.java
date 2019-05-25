package net.yitun.ioften.cms.dicts;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import net.yitun.basic.dict.Dict;
import net.yitun.basic.dict.Status;

/**
 * <pre>
 * <b>资讯 挖矿状态.</b>
 * <b>Description:</b>
 *    ON:已开启; OFF:已关闭; REDO:重新开始; FAILED:挖矿失败; SUCCESS:挖矿成功
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月12日 下午7:27:02
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月12日 下午7:27:02 LH
 *         new file.
 * </pre>
 */
@AllArgsConstructor
public enum GainStatus implements Dict {

    /** 已开启 */
    ON(1, "已开启"),

    /** 已关闭 */
    OFF(3, "已关闭"),

    /** 重新开始 */
    REDO(5, "重新开始"),

    /** 重复挖矿 */
    REPEAT(7, "重复挖矿"),

    /** 挖矿失败 */
    FAILED(9, "挖矿失败"),

    /** 挖矿成功 */
    SUCCESS(13, "挖矿成功");

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
