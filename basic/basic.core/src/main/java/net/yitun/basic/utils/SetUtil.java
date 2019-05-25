package net.yitun.basic.utils;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <pre>
 * <b>Set 集合辅助工具</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018-02-26 14:01:46.854
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018-02-26 14:01:46.854 LH
 *         new file.
 * </pre>
 */
@SuppressWarnings("unchecked")
public class SetUtil { // extends com.google.common.collect.Sets {

    /**
     * 受保护的构造方法, 防止外部构建对象实例.
     */
    protected SetUtil() {
        super();
    }

    /**
     * 是否为空
     * 
     * @param set
     * @return boolean
     */
    public static boolean isEmpty(Set<?> set) {
        if (null == set)
            return true;

        return 0 == set.size();
    }

    /**
     * 是否不为空
     * 
     * @param set
     * @return boolean
     */
    public static boolean isNotEmpty(Set<?> set) {
        return null != set && 0 != set.size();
    }

    /**
     * 将数组转换的Se有序集合
     * 
     * @param array
     * @return HashSet<T>
     */
    public static <T> Set<T> asSet(T... array) {
        if (null == array)
            return null;

        Set<T> set = new HashSet<>(array.length);
        for (T item : array)
            set.add(item);

        return set;
    }

    /**
     * 将数组转换的Se有序集合
     * 
     * @param array
     * @return HashSet<T>
     */
    public static <T> Set<T> asLinkedSet(T... array) {
        if (null == array)
            return null;

        Set<T> set = new LinkedHashSet<>(array.length);
        for (T item : array)
            set.add(item);

        return set;
    }

    /**
     * 将字符格式化Set集合<br/>
     * 如果原字符为null, 将返回 null. 默认分隔标记为 ","
     * 
     * @param source 原字符
     * @return Set<String>
     */
    public static Set<String> split(String source) {
        return split(source, ",");
    }

    /**
     * 将字符格式化Set集合<br/>
     * 如果原字符为null 或者 分隔标记为 null (""), 都将返回 null.
     * 
     * @param source    原字符
     * @param separator 分隔标记
     * @return Set<String>
     */
    public static Set<String> split(String source, String separator) {
        if (null == source || 0 == source.length() //
                || null == separator || 0 == separator.length())
            return null;

        String[] array = source.split(separator);

        return asSet(array);
    }

    /**
     * 将集合转为字符串
     * 
     * @param Set<String>
     * @return String set is null or empty, return null
     */
    public static String toString(Set<?> set) {
        return toString(set, ",");
    }

    /**
     * 将集合转为字符串
     * 
     * @param           Set<String>
     * @param separator 分隔标记
     * @return String set is null or empty, return null
     */
    public static String toString(Set<?> set, String separator) {
        if (null == set || set.isEmpty())
            return null;

        StringBuffer sb = new StringBuffer();
        for (Object item : set)
            sb.append(item).append(separator);

        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

}
