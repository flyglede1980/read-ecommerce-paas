package net.yitun.ioften.adv.service;

/**
 * <pre>
 * <b>广告 配置服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月7日 下午4:34:31
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月7日 下午4:34:31 LH
 *         new file.
 * </pre>
 */
public interface ConfService {

    /**
     * 显示闪屏广告
     * 
     * @return boolean true: 是; false:否
     */
    boolean isFlashAdv();

    /**
     * 显示推荐页Banner
     * 
     * @return boolean true: 是; false:否
     */
    boolean isBannerAdv();

}
