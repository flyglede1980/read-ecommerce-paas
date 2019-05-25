package net.yitun.comon.service;

import net.yitun.basic.utils.PwdUtil.LEVEL;

/**
 * <pre>
 * <b>检测服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月24日 下午11:41:11 LH
 *         new file.
 * </pre>
 */
public interface CheckService {

    /**
     * 密码强度
     * 
     * @param passwd
     * @return LEVEL
     */
    LEVEL checkPwd(String passwd);

    /**
     * 是否已登录
     * 
     * @return boolean
     */
    boolean checkIsLogin();

}
