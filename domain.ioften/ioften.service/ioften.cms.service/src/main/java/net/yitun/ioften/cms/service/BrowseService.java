package net.yitun.ioften.cms.service;

import java.util.Set;

import com.github.pagehelper.Page;

import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.entity.Browse;
import net.yitun.ioften.cms.model.browse.BrowseQuery;
import net.yitun.ioften.pay.dicts.Way;

/**
 * <pre>
 * <b>资讯 浏览服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月12日 下午6:03:50
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月12日 下午6:03:50 LH
 *         new file.
 * </pre>
 */
public interface BrowseService {

    /**
     * 查找记录
     * 
     * @param uid 用户ID
     * @param aid 文章ID
     * @return Browse 浏览记录
     */
    Browse get(Long uid, Long aid);

    /**
     * 查找记录, 无记录自动创建
     * 
     * @param uid 用户ID
     * @param aid 文章ID
     * @return Browse 浏览记录
     */
    Browse find(Long uid, Long aid);

    /**
     * 加锁记录
     * 
     * @param uid    用户ID
     * @param aid    文章ID
     * @param expire 过期时间
     * @return boolean 是否加锁
     */
    boolean lock(String uid, String aid, Long expire);

    /**
     * 分页查询
     * 
     * @param query 筛选参数
     * @return Page<Browse> 分页结果
     */
    Page<Browse> query(BrowseQuery query);

    /**
     * 修改记录
     * 
     * @param uid   用户ID
     * @param aid   文章ID
     * @param way   挖矿方式
     * @param award 奖励
     * @return Browse 浏览记录
     */
    Browse modify(Long uid, Long aid, Way way, Long award);

    /**
     * 删除记录
     * 
     * @param uid 用户ID
     * @param ids 记录清单ID
     * @return Result<?>
     */
    Result<?> delete(Long uid, Set<Long> ids);

}
