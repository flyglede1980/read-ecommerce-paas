package net.yitun.ioften.cms.dicts;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import net.yitun.basic.dict.Dict;
import net.yitun.basic.dict.Status;

/**
 * <pre>
 * <b>资讯状态字典.</b>
 * <b>Description:</b>
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
public enum ArticleStatus implements Dict {

    /** 草稿 */
    DRAFT(0, "草稿"),

    /** 审核中 */
    CHECK(1, "审核中"),

    /** 已拒绝 */
    REFUSE(3, "已拒绝"),

    /** 已发布 */
    ISSUE(5, "已发布"),

    /** 待发布 */
    WAIT_ISSUE(7, "待发布"),

    /** 已下线 */
    OFFLINE(9, "已下线"),

    /** 已删除 */
    DELETE(12, "已删除");

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
