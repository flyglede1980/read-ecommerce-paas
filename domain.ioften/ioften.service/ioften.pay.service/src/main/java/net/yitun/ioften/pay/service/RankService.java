package net.yitun.ioften.pay.service;

import com.github.pagehelper.Page;

import net.yitun.basic.model.Result;
import net.yitun.ioften.pay.entity.Rank;
import net.yitun.ioften.pay.entity.vo.rank.MyRankVo;
import net.yitun.ioften.pay.model.rank.RankQuery;

/**
 * <pre>
 * <b>支付 服豆排名服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月14日 下午1:32:36
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月14日 下午1:32:36 LH
 *         new file.
 * </pre>
 */
public interface RankService {

    /**
     * 我的排名
     * 
     * @param uid
     * @return MyRankVo
     */
    MyRankVo my(Long uid);

    /**
     * 分页查询
     * 
     * @param query 筛选参数
     * @return Page<Rank> 分页列表
     */
    Page<Rank> query(RankQuery rankQuery);

    /**
     * 清洗服豆排名
     * 
     * @return Result<Integer>
     */
    Result<Integer> etlRank();

}
