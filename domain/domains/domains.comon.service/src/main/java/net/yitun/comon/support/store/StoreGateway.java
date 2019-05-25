package net.yitun.comon.support.store;

import java.io.InputStream;

/**
 * <pre>
 * <b>存储网关.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月9日 下午8:25:33 LH
 *         new file.
 * </pre>
 */
public interface StoreGateway {

    /**
     * 类型
     * 
     * @return String
     */
    default String type() {
        return null;
    };

    /**
     * 删除对象
     * 
     * @param key
     * @param isPub
     * @return String
     */
    default String del(String key, boolean isPub) {
        return null;
    };

    /**
     * 返回域名
     * 
     * @param key
     * @param isPub
     * @return String
     */
    default String dns(String key, boolean isPub) {
        return null;
    }

    /**
     * 解析对象
     * 
     * @param key
     * @param isPub
     * @return List<String>
     */
    default String view(String key, boolean isPub, long expireTime) {
        return null;
    }

    /**
     * 确认对象
     * 
     * @param key
     * @param isPub
     * @param newKey
     * @param srcBucket
     * @return String
     */
    default String sure(String key, boolean isPub, String newKey, String srcBucket) {
        return null;
    }

    /**
     * 存储对象
     * 
     * @param key
     * @param isPub
     * @param input
     * @return String
     */
    default String store(String key, boolean isPub, InputStream input) {
        return null;
    }

}