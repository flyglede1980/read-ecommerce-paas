package net.yitun.ioften.pay.service;

import net.yitun.basic.model.Result;
import net.yitun.ioften.pay.entity.vo.conf.GainConf;
import net.yitun.ioften.pay.model.conf.gain.GainConfModify;

/**
 * <pre>
 * <b>支付 配置服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月8日 下午3:07:33
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月8日 下午3:07:33 LH
 *         new file.
 * </pre>
 */
public interface ConfService {

    /**
     * 可否支付
     * 
     * @return boolean true:已打开; false:已关闭
     */
    boolean can();

    /**
     * 获取挖矿配置
     * 
     * @return GainConf
     */
    GainConf gainConf();

    /**
     * 获取挖矿配置
     * 
     * @param model
     * @return Result<?>
     */
    Result<?> gainConfModify(GainConfModify model);

    /**
     * 获取文章挖矿充值系数
     * 
     * @param amount 充值总额
     * @return Float 充值系数
     */
    Float gainConfRechargeRatio(Long amount);

}
