package net.yitun.ioften.cms.service;

import java.util.Set;

import com.github.pagehelper.Page;

import net.yitun.basic.model.Result;
import net.yitun.ioften.cms.entity.Favorite;
import net.yitun.ioften.cms.model.favorite.FavoriteQuery;

/**
 * <pre>
 * <b>资讯 收藏服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月19日 上午10:14:51
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月19日 上午10:14:51 LH
 *         new file.
 * </pre>
 */
public interface FavoriteService {

    /**
     * 查找记录
     * 
     * @param uid 用户ID
     * @param aid 资讯ID
     * @return Favorite 收藏结果
     */
    Favorite find(Long uid, Long aid);

    /**
     * 分页查询
     * 
     * @param query 筛选参数
     * @return Page<Favorite> 分页结果
     */
    Page<Favorite> query(FavoriteQuery query);

    /**
     * 收藏资讯
     * 
     * @param uid 用户ID
     * @param aid 资讯ID
     * @return Result<Boolean> 收藏结果, true:已收藏; false:已取消
     */
    Result<Boolean> collect(Long uid, Long aid);

    /**
     * 删除收藏
     * 
     * @param uid 用户ID
     * @param ids 收藏清单ID
     * @return Result<?>
     */
    Result<?> delete(Long uid, Set<Long> ids);

}
