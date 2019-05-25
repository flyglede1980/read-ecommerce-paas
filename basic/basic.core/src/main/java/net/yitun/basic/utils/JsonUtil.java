package net.yitun.basic.utils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

/**
 * <pre>
 * <b>Json 工具.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-15 10:34:36.441
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2017-09-15 10:34:36.441 LH
 *         new file.
 * </pre>
 */
public abstract class JsonUtil {

    /** 空 JSON:{}. */
    public static final String EMPTY = "{}";

    /** 序列化配置. */
    protected static final SerializeConfig config;

    /** 模板: yyyy-MM-dd HH:mm:ss.SSS */
    public static final String DATE_TIME_STAMP = "yyyy-MM-dd HH:mm:ss.SSS";

    static {
        config = new SerializeConfig();
        // config.put(Float.class, new DoubleSerializer("###.##"));
        // config.put(Double.class, new DoubleSerializer("###.##"));
        config.put(Date.class, new SimpleDateFormatSerializer(DATE_TIME_STAMP)); // 指定Date类型JSON序列化的格式.
    }

    /**
     * 受保护的构造方法, 防止外部构建对象实例.
     */
    protected JsonUtil() {
        super();
    }

    /**
     * 将Object转为json字符串;<br/>
     * 当null==obj时, 则返回 "{}".
     * 
     * @param obj 对象.
     * @return String json字符串.
     */
    public static String toJson(Object obj) {
        return toJson(obj, null);
    }

    /**
     * 将Object转为json字符串;<br/>
     * 当null==obj时, 则返回 defaultValue.
     * 
     * @param obj          对象.
     * @param defaultValue 默认值.
     * @return String json字符串.
     */
    public static String toJson(Object obj, String defaultValue) {
        if (null == obj)
            return defaultValue;

        // SerializerFeature: 序列化选项, SortField, WriteClassName, DisableCircularReferenceDetect (取消输入循环引用，默认)
        return JSON.toJSONString(obj, config, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 将json字符串转为JSONObject;<br/>
     * 当null==str或str为无效json字符串时, 则返回 null.
     * 
     * @param json json字符串.
     * @return JSONObject json封装对象.
     */
    public static JSONObject toBean(String json) {
        if (null == json || 0 == (json = json.trim()).length())
            return null;

        try {
            return JSON.parseObject(json);
        } catch (JSONException e) {
            // e.printStackTrace();
        }

        return null;
    }

    /**
     * 和toBean(String json, Class<T> clazz)类似，差别在于入参json为byte[]
     * 
     * @param bytes json字符串二进制.
     * @param clazz 对象的Class 类型
     * @return T 对应Javabean实例.
     */
    public static <T> T toBean(byte[] bytes, Class<T> clazz) {
        if (bytes == null)
            return null;

        try {
            return JSON.parseObject(bytes, clazz);
        } catch (JSONException e) {
            // e.printStackTrace();
        } catch (ClassCastException e) {
            // e.printStackTrace();
        }

        return null;
    }

    /**
     * 将json字符串转为指定类型的Javabean;<br/>
     * 当null==str或str为无效json字符串时, 则返回 null.
     * 
     * @param json  json字符串.
     * @param clazz 对象的Class 类型
     * @return T 对应Javabean实例.
     */
    public static <T> T toBean(String json, Class<T> clazz) {
        if (null == json || 0 == (json = json.trim()).length())
            return null;

        try {
            return JSON.parseObject(json, clazz);
        } catch (JSONException e) {
            // e.printStackTrace();
        } catch (ClassCastException e) {
            // e.printStackTrace();
        }

        return null;
    }

    /**
     * 将json字符串转为指定类型的Object;<br/>
     * 当null==str或str为无效json字符串时, 则返回 null.<br/>
     * 例：User dto = JsonUtil.toOject(json, new TypeReference<User>() {});
     * 
     * @param json  json字符串.
     * @param clazz 对象的Class 类型
     * @return T 对应Object实例.
     */
    public static <T> T toBean(String json, TypeReference<T> type) {
        if (null == json || 0 == (json = json.trim()).length())
            return null;

        try {
            return JSON.parseObject(json, type);
        } catch (JSONException e) {
            // e.printStackTrace();
        } catch (ClassCastException e) {
            // e.printStackTrace();
        }

        return null;
    }

    /**
     * 将json字符串转为指定泛型的List;<br/>
     * 当null==str或str为无效json字符串时, 则返回 null.
     * 
     * @param json  json字符串.
     * @param clazz 对象的Class 类型
     * @return T 对应泛型的List实例.
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        if (null == json || 0 == (json = json.trim()).length())
            return null;

        try {
            return JSON.parseArray(json, clazz);
        } catch (JSONException e) {
            // e.printStackTrace();
        } catch (ClassCastException e) {
            // e.printStackTrace();
        }

        return null;
    }

    /**
     * 将json字符串转为 Map
     * 
     * @param json
     * @return <T> Map<String, Object>
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String, Object> toMap(String json) {
        return toBean(json, Map.class);
    }

    /**
     * 将json字符串转为 Map
     * 
     * @param json
     * @param clazz
     * @return <T> Map<String, T>
     */
    public static <T> Map<String, T> toMap(String json, Class<T> clazz) {
        if (null == json || 0 == (json = json.trim()).length())
            return null;

        Map<String, T> map = JSON.parseObject(json, //
                new TypeReference<Map<String, T>>() {
                });

        for (Entry<String, T> entry : map.entrySet()) {
            JSONObject obj = (JSONObject) entry.getValue();
            map.put(entry.getKey(), JSON.toJavaObject(obj, clazz));
        }

        return map;
    }

    /**
     * 将json字符串转为Array;<br/>
     * 当null==str或str为无效json字符串时, 则返回 null.
     * 
     * @param json json字符串.
     * @return JSONArray 对应Array实例.
     */
    public static JSONArray toArray(String json) {
        if (null == json || 0 == (json = json.trim()).length())
            return null;

        try {
            return JSON.parseArray(json);
        } catch (JSONException e) {
            // e.printStackTrace();
        }

        return null;
    }

}
