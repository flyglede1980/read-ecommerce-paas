package net.yitun.ioften.cms;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.conf.Conf;
import net.yitun.ioften.cms.model.share.ShareDetail;

/**
 * <pre>
 * <b>资讯 分享挖矿.</b>
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
public interface ShareApi {

    /**
     * 生成分享代码
     * 
     * @param aid 资讯ID
     * @return Result<ShareDetail> 分享代码
     */
    @GetMapping(value = Conf.ROUTE //
            + "/share/{aid}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<ShareDetail> gen(@PathVariable("aid") Long aid);

    /**
     * 展示分享信息
     * 
     * @param id 分享代码
     * @return Result<ShareDetail> 分享信息
     */
    @GetMapping(value = Conf.ROUTE //
            + "/share/show/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<ShareDetail> show(@PathVariable("id") Long id);

}
