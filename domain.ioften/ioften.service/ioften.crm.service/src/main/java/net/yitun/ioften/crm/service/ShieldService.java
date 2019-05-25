package net.yitun.ioften.crm.service;

import java.util.Set;

import com.github.pagehelper.Page;

import net.yitun.basic.model.Result;
import net.yitun.ioften.crm.entity.Shield;
import net.yitun.ioften.crm.model.shield.ShieldQuery;

/**
 * <pre>
 * <b>用户 拉黑服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月17日 下午2:47:11 LH
 *         new file.
 * </pre>
 */
public interface ShieldService {

    /**
     * 分页查询
     * 
     * @param query 筛选参数
     * @return Page<Shield> 分页结果
     */
    Page<Shield> query(ShieldQuery query);

    /**
     * 标记拉黑
     * 
     * @param target 对方ID
     * @return Result<?> 新增结果
     */
    Result<?> stamp(Long target);

    /**
     * 解除拉黑
     * 
     * @param uid 用户ID
     * @param ids ID清单
     * @return Result<?> 删除结果
     */
    Result<?> delete(Long uid, Set<Long> ids);

}
