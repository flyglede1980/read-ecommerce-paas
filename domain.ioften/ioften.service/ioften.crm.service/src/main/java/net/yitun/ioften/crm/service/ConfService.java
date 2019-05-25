package net.yitun.ioften.crm.service;

/**
 * <pre>
 * <b>用户 配置服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月21日 下午2:43:36
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月21日 下午2:43:36 LH
 *         new file.
 * </pre>
 */
public interface ConfService {

    /**
     * 生成邀请文案
     * 
     * @param nick 昵称
     * @return String
     */
    String buildInviteContent(String nick);

}
