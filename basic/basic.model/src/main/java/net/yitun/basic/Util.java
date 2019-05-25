package net.yitun.basic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 * <b>ID 辅助.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月21日 下午5:09:53
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月21日 下午5:09:53 LH
 *         new file.
 * </pre>
 */
public class Util {

    /** ID 最小值 */
    public static final long MIN_ID = 1016723701000000000L;

    /** 手机正则表达式 */
    public static final String REGEX_PHONE = "^1[345789]\\d{9}$";

    /** 邮箱正则表达式 */
    public static final String REGEX_EMAIL = "^[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}$";

    /**
     * 是否匹配
     * 
     * @param str   待匹配字符
     * @param regex 匹配正则表达式
     * @return boolean true:匹配; false:不匹配
     */
    public static boolean matche(String str, String regex) {
        if (null == str || null == regex)
            return false;

        // 编译正则表达式
        Pattern pattern = Pattern.compile(regex);
        // pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE); // 忽略大小写的写法

        Matcher matcher = pattern.matcher(str);

        // 字符串是否与正则表达式相匹配
        return matcher.matches();
    }

}
