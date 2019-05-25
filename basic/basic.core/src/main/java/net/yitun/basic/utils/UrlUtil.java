package net.yitun.basic.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * <b>URL 工具</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-15 10:54:54.900
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2017-09-15 10:54:54.900 LH
 *         new file.
 * </pre>
 */
public abstract class UrlUtil {

    /** 默认编码, UTF-8 */
    private static final String CHARSET = "UTF-8";

    /* 日志记录器. */
    protected static final Logger logger = LoggerFactory.getLogger(UrlUtil.class);

    /**
     * 受保护的构造方法, 防止外部构建对象实例.
     */
    protected UrlUtil() {
        super();
    }

    /**
     * URLEncode 编码
     * 
     * @param str
     * @return String
     */
    public static String encode(String str) {
        return encode(str, CHARSET);
    }

    /**
     * URLEncode 编码
     * 
     * @param str
     * @param charset
     * @return String
     */
    public static String encode(String str, String charset) {
        if (null == str)
            return null;

        if (0 == str.length())
            return str;

        try {
            return URLEncoder.encode(str, CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * URLDecode 解码
     * 
     * @param str
     * @return String
     */
    public static String decode(String str) {
        return decode(str, CHARSET);
    }

    /**
     * URLDecode 解码
     * 
     * @param str
     * @param charset
     * @return String
     */
    public static String decode(String str, String charset) {
        if (null == str)
            return null;

        if (0 == str.length())
            return str;

        try {
            return URLDecoder.decode(str, CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 从URL中提取参数
     * 
     * @param url
     * @return Map<String, Object>
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getParams(String url) {

        int len = 0;
        Map<String, Object> map = new HashMap<>();
        if (null == url //
                || 0 == (len = url.length()))
            return map;

        int index = url.indexOf("?");
        if (-1 != index)
            url = url.substring(index + 1, len - 1);

        String key = null;
        Object value = null;
        String[] urls = url.split("&"), subs = null;

        for (String sub : urls) {
            subs = sub.split("=");
            key = subs[0];
            if (null == (value = map.get(key))) {
                map.put(key, subs[1]);

            } else if (value instanceof String) {
                List<String> list;
                map.put(key, list = new ArrayList<>());
                list.add(String.valueOf(value));
                list.add(subs[1]);

            } else {
                ((List<String>) value).add(subs[1]);
            }
        }

        return map;
    }
}
