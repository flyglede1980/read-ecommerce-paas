package net.yitun.ioften.adv;

import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import net.yitun.basic.model.Result;
import net.yitun.ioften.adv.conf.Conf;
import net.yitun.ioften.adv.model.adv.AdvDetail;

/**
 * <pre>
 * <b>广告 显示接口.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月5日 下午6:54:27 LH
 *         new file.
 * </pre>
 */
public interface ShowApi {

    /**
     * APP 闪屏广告
     * 
     * @return Result<AdvDetail> 闪屏广告
     */
    @GetMapping(value = Conf.ROUTE //
            + "/flash", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<AdvDetail> flash();

    /**
     * 推荐页 Banner
     * 
     * @return Result<Collection<AdvDetail>> 广告列表
     */
    @GetMapping(value = Conf.ROUTE //
            + "/banner", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<Collection<AdvDetail>> banner();

}
