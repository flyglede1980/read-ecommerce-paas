package net.yitun.ioften.pay;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.yitun.basic.model.Result;
import net.yitun.ioften.pay.conf.Conf;
import net.yitun.ioften.pay.model.conf.gain.GainConfDetail;
import net.yitun.ioften.pay.model.conf.gain.GainConfModify;

/**
 * <pre>
 * <b>支付 配置接口.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月8日 下午2:44:17
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月8日 下午2:44:17 LH
 *         new file.
 * </pre>
 */
public interface ConfApi {

    /**
     * 可否支付
     * 
     * @return Result<Boolean> 结果
     */
    @Deprecated
    @GetMapping(value = Conf.ROUTE //
            + "/on", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<Boolean> can();

    /**
     * 查看挖矿配置
     * 
     * @return Result<GainConfDetail> 挖矿配置
     */
    @GetMapping(value = Conf.ROUTE //
            + "/conf/gain", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<GainConfDetail> gainConf();

    /**
     * 修改挖矿配置
     * 
     * @param GainConfModify 挖矿配置
     * @return Result<?> 修改结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/conf/gain", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> gainConfModify(@RequestBody GainConfModify model);

    /**
     * 获取挖矿配置
     * 
     * @return GainConfDetail
     */
    GainConfDetail gainConfInfo();

    /**
     * 获取挖矿系数
     * 
     * @param amount 充值金额
     * @return Result<Float> 挖矿系数
     */
    Result<Float> gainConfRatio(@RequestParam("amount") Long amount);

}
