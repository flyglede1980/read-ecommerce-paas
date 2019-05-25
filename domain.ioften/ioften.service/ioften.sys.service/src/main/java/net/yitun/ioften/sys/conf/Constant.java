package net.yitun.ioften.sys.conf;

/**
 * <pre>
 * <b>SYS 常量.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年8月22日 下午3:02:19 LH
 *         new file.
 * </pre>
 */
public class Constant {

    /** 配置缓存 */
    public static final String CK_CONF = "sys.conf#3600#-1"; // 缓存1H
    /** QA问题缓存 */
    public static final String CK_ISSUE = "sys.issue#86400#-1"; // 缓存24H

    /** 运营账号缓存 */
    public static final String CK_ADMIN = "sys.admin#604800#-1"; // 缓存7*24H
    // =================================================================
}
