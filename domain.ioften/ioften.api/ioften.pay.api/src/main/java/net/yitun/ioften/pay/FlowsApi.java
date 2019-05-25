package net.yitun.ioften.pay;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.ioften.pay.conf.Conf;
import net.yitun.ioften.pay.model.flows.FlowsDetail;
import net.yitun.ioften.pay.model.flows.FlowsQuery;
import net.yitun.ioften.pay.model.flows.MyFlowsTotalDetail;
import net.yitun.ioften.pay.model.flows.MyFlowsTotalQuery;

/**
 * <pre>
 * <b>支付 流水接口.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月8日 下午6:10:39
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月8日 下午6:10:39 LH
 *         new file.
 * </pre>
 */
public interface FlowsApi {

    /**
     * 分页查询
     * 
     * @param query 筛选参数
     * @return PageResult<FlowsDetail> 分页列表
     */
    @GetMapping(value = Conf.ROUTE //
            + "/flowss", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    PageResult<FlowsDetail> query(FlowsQuery query);

    /**
     * 流水统计
     *
     * @param query
     * @return Result<MyFlowsTotalDetail> 合计结果
     */
    @GetMapping(value = Conf.ROUTE //
            + "/flows/mytotal", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Result<MyFlowsTotalDetail> myTotal(MyFlowsTotalQuery query);

}
