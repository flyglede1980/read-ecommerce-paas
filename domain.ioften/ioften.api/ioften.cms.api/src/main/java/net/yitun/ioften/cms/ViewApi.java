package net.yitun.ioften.cms;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.conf.Conf;
import net.yitun.ioften.cms.model.gain.GainDetail;

/**
 * <pre>
 * <b>资讯 阅读挖矿.</b>
 * <b>Description:</b>
 *
 * <b>Author:</b> XJN
 * <b>Date:</b> 2018-11-12 11:17:00.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月12日 上午11:17:06 XJN
 *         new file.
 * </pre>
 */
public interface ViewApi {

    /**
     * 阅读开始
     * 
     * @param aid 资讯ID
     * @return Result<GainDetail> 挖矿状态
     */
    @GetMapping(value = Conf.ROUTE //
            + "/view/{aid}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<GainDetail> view(@PathVariable("aid") Long id);

    /**
     * 阅读挖矿提交
     * 
     * @param aid 资讯ID
     * @return Result<GainDetail> 挖矿结果
     */
    @PostMapping(value = Conf.ROUTE //
            + "/view/{aid}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<GainDetail> submit(@PathVariable("aid") Long aid);

}
