package net.yitun.basic.dict;

import java.util.Map;

/**
 * <pre>
 * <b>枚举字典.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月6日 上午10:48:09 LH
 *         new file.
 * </pre>
 */
public interface Dict {

    int val();

    String label();

    Map<Integer, ? extends Dict> dicts();

}