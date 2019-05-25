package net.yitun.ioften.pay.service;

import com.github.pagehelper.Page;

import net.yitun.ioften.pay.entity.Flows;
import net.yitun.ioften.pay.entity.vo.flows.MyFlowsTotal;
import net.yitun.ioften.pay.model.flows.FlowsQuery;
import net.yitun.ioften.pay.model.flows.MyFlowsTotalQuery;

/**
 * <pre>
 * <b>支付 流水服务.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月8日 下午6:27:43
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月8日 下午6:27:43 LH
 *         new file.
 * </pre>
 */
public interface FlowsService {

    /**
     * 分页查询
     * 
     * @param query 筛选参数
     * @return Page<Flows> 分页列表
     */
    Page<Flows> query(FlowsQuery query);

    /**
     * 流水合计
     * 
     * @param query 筛选参数
     * @return MyFlowsTotal 统计合计
     */
    MyFlowsTotal myTotal(MyFlowsTotalQuery query);

    /**
     * 流水合计<br/>
     * 统计用户指定天(2018-10-11)或月(2018-10)的流水
     * 
     * @param uid
     * @param dayOrMonth 2018-01 or 2018-01-01
     * @return MyFlowsTotal
     */
    MyFlowsTotal myTotal(Long uid, String dayOrMonth, boolean cache);

}
