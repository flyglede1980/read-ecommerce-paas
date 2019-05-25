package net.yitun.ioften.sys;

import java.util.Set;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.ioften.sys.conf.Conf;
import net.yitun.ioften.sys.model.config.ConfigDetail;
import net.yitun.ioften.sys.model.config.ConfigQuery;
import net.yitun.ioften.sys.model.config.ConfigStore;

/**
 * <pre>
 * <b>系统 配置管理.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月6日 上午10:07:02 LH
 *         new file.
 * </pre>
 */
public interface ConfigApi {

    /**
     * 简要信息
     * 
     * @param code 代码
     * @return Result<ConfigDetail> 简要信息
     */
    @GetMapping(value = Conf.ROUTE //
            + "/config/{code}", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<ConfigDetail> info(@PathVariable("code") String code);

    /**
     * 详细信息
     * 
     * @param code 代码
     * @return Config 详情信息
     */
    ConfigDetail code(@PathVariable("code") String code);

    /**
     * 分页查询
     * 
     * @param query 查询参数
     * @return PageResult<ConfigDetail> 分页结果
     */
    @GetMapping(value = Conf.ROUTE //
            + "/configs", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<ConfigDetail> query(ConfigQuery query);

    /**
     * 存储信息
     * 
     * @param model 存储模型
     * @return Result<?> 存储结果
     */
    @PutMapping(value = Conf.ROUTE //
            + "/config", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<?> store(@RequestBody ConfigStore model);

    /**
     * 删除信息
     * 
     * @param ids ID清单
     * @return Result<Integer> 删除结果
     */
    @DeleteMapping(value = Conf.ROUTE //
            + "/config", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<Integer> delete(@RequestParam("id") Set<Long> ids);

}
