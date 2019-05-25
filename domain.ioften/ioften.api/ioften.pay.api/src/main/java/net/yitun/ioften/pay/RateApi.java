package net.yitun.ioften.pay;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import net.yitun.basic.model.Result;
import net.yitun.ioften.pay.conf.Conf;

/**
 * <pre>
 * <b>支付 汇率接口.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月6日 下午12:43:11 LH
 *         new file.
 * </pre>
 */
public interface RateApi {

    /**
     * 当前汇率
     * 
     * @return Long 汇率, 小数点后移了8位
     */
    @GetMapping(value = Conf.ROUTE //
            + "/rate", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Long now();

    /**
     * 刷新汇率
     * 
     * @return Result<?> 刷新结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/rate/refresh", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> refresh();

}
