package net.yitun.basic.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * <pre>
 * <b>随机 工具.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018-07-24 17:58:01.702
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018-07-24 17:58:01.702 LH
 *         new file.
 * </pre>
 */
public class CodeUtil {

    /**
     * 随机数字字符
     * 
     * @param len 长度
     * @return String 随机数字
     */
    public static String code(int len) {
        StringBuffer code = new StringBuffer();
        try {
            SecureRandom random //
                    = SecureRandom.getInstance("SHA1PRNG");
            for (int i = 0; i < len; i++)
                code.append(random.nextInt(10));

        } catch (NoSuchAlgorithmException e) {
            // Forward to handler
        }
        return code.toString();
    }

}
