package net.yitun.ioften.sys.service;

import java.util.Set;

import com.github.pagehelper.Page;

import net.yitun.basic.model.Result;
import net.yitun.ioften.sys.entity.Config;
import net.yitun.ioften.sys.model.config.ConfigQuery;
import net.yitun.ioften.sys.model.config.ConfigStore;

/**
 * <pre>
 * <b>系统 配置服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月6日 上午11:58:08 LH
 *         new file.
 * </pre>
 */
public interface ConfigService {

    /**
     * 详情信息
     * 
     * @param id ID
     * @return Config 详情信息
     */
    Config get(Long id);

    /**
     * 详情信息
     * 
     * @param code 代码
     * @return Config 详情信息
     */
    Config getByCode(String code);

    /**
     * 分页查询
     * 
     * @param query 筛选参数
     * @return Page<Config> 分页结果
     */
    Page<Config> query(ConfigQuery query);

    /**
     * 修改配置
     * 
     * @param model 修改模型
     * @return Config 修改结果
     */
    Result<?> store(ConfigStore model);

    /**
     * 删除配置
     * 
     * @param ids ID清单
     * @return Result<Integer> 删除结果
     */
    Result<Integer> delete(Set<Long> ids);

}
