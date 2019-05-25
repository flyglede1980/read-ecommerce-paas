package net.yitun.basic.utils;

import java.util.UUID;

import net.yitun.basic.utils.extra.IdWorker;

/**
 * <pre>
 * <b>ID 工具</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018-01-29 08:51:12.698
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018-01-29 08:51:12.698 LH
 *         new file.
 * </pre>
 */
public abstract class IdUtil {

    /** 最小ID, 起始ID */
    public static final long MIN = IdWorker.MIN;

    private static final IdWorker idw = new IdWorker(0, 0);

    /**
     * 生成 ID <br/>
     * 雪花算法
     * 
     * @return long
     */
    public static long id() {
        return idw.next();
    }

    /**
     * 生成 UUID
     * 
     * @return String
     */
    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        String _uuid = uuid.toString();
        StringBuffer str = new StringBuffer(32); // 去掉"-"符号
        str.append(_uuid.substring(0, 8)).append(_uuid.substring(9, 13));
        str.append(_uuid.substring(14, 18)).append(_uuid.substring(19, 23)).append(_uuid.substring(24));
        return str.toString();
    }

}
