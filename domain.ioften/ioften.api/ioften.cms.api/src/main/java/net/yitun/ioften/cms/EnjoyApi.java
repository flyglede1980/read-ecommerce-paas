package net.yitun.ioften.cms;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.conf.Conf;
import net.yitun.ioften.cms.model.enjoy.EnjoyDetail;

/**
 * <pre>
 * <b>资讯 点赞挖矿.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月23日 下午3:39:08
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月23日 下午3:39:08 LH
 *         new file.
 * </pre>
 */
public interface EnjoyApi {

    /**
     * 资讯点赞
     * 
     * @param aid 资讯ID
     * @return Result<EnjoyDetail> 点赞结果
     */
    @PutMapping(value = { Conf.ROUTE + "/enjoy/{aid}", Conf.ROUTE + "/enjoy/article/{aid}" } //
            , consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<EnjoyDetail> enjoy(@PathVariable("aid") Long aid);

}
