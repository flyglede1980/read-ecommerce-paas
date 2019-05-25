package net.yitun.ioften.pay.service;

import net.yitun.basic.model.Result;

/**
 * <pre>
 * <b>支付 汇率服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月7日 上午10:47:42 LH
 *         new file.
 * </pre>
 */
public interface RateService {

    /**
     * 当前汇率
     * 
     * @return Long
     */
    Long now();

    /**
     * 刷新汇率
     * 
     * @return Result<Boolean> true:成功; false:失败
     */
    Result<Boolean> refresh();

}
