package net.yitun.ioften.adv.service;

import java.util.Set;

import com.github.pagehelper.Page;

import net.yitun.basic.model.Result;
import net.yitun.ioften.adv.entity.Adv;
import net.yitun.ioften.adv.model.adv.AdvCreate;
import net.yitun.ioften.adv.model.adv.AdvModify;
import net.yitun.ioften.adv.model.adv.AdvQuery;

/**
 * <pre>
 * <b>广告 内容服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月6日 上午10:05:31 LH
 *         new file.
 * </pre>
 */
public interface AdvService {

    /**
     * 广告详细
     * 
     * @param id ID
     * @return Adv 广告详细
     */
    Adv get(Long id);

    /**
     * 分页查询
     * 
     * @param query 筛选参数
     * @return Page<User> 分页列表
     */
    Page<Adv> query(AdvQuery query);

    /**
     * 新增广告
     * 
     * @param model 广告信息
     * @return Config 新增结果
     */
    Result<?> create(AdvCreate model);

    /**
     * 修改广告
     * 
     * @param model 广告信息
     * @return Config 修改结果
     */
    Result<?> modify(AdvModify model);

    /**
     * 删除广告
     * 
     * @param ids ID清单
     * @return Result<?> 删除结果
     */
    Result<?> delete(Set<Long> ids);

}
