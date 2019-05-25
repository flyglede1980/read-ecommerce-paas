package net.yitun.ioften.pay.website.action;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.yitun.basic.model.PageResult;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.DateUtil;
import net.yitun.ioften.pay.FlowsApi;
import net.yitun.ioften.pay.entity.Flows;
import net.yitun.ioften.pay.entity.vo.flows.MyFlowsTotal;
import net.yitun.ioften.pay.model.flows.FlowsDetail;
import net.yitun.ioften.pay.model.flows.FlowsQuery;
import net.yitun.ioften.pay.model.flows.MyFlowsTotalDetail;
import net.yitun.ioften.pay.model.flows.MyFlowsTotalQuery;
import net.yitun.ioften.pay.service.FlowsService;

@Api(tags = "支付 流水接口")
@RestController("pay.FlowsApi")
@SuppressWarnings("unchecked")
public class FlowsAction implements FlowsApi {

    @Autowired
    protected FlowsService service;

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "流水查询")
    public PageResult<FlowsDetail> query(@Validated FlowsQuery query) {
        String month = query.getMonth();
        Date date = DateUtil.parse(month, "yyyy-MM");

        query.setUid(SecurityUtil.getId());
        query.setStime(DateUtil.monthOfStartTime(date));
        query.setEtime(DateUtil.monthOfEndTime(date));

        Page<Flows> page = this.service.query(query);
        return new PageResult<>(page.getTotal(), page.getPages(), //
                page.stream()
                        .map(flows -> new FlowsDetail(flows.getId(), flows.getUid(), flows.getWay(), flows.getAmount(),
                                flows.getCurrency(), flows.getTarget(), flows.getChannel(), flows.getOutFlows(),
                                flows.getRemark(), flows.getStatus(), flows.getCtime(), flows.getMtime()))
                        .collect(Collectors.toList()));
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "流水统计")
    public Result<MyFlowsTotalDetail> myTotal(@Validated MyFlowsTotalQuery query) {
        String month = query.getMonth();
        Date date = DateUtil.parse(month, "yyyy-MM");

        query.setUid(SecurityUtil.getId());
        query.setStime(DateUtil.monthOfStartTime(date));
        query.setEtime(DateUtil.monthOfEndTime(date));

        // 非单月可以启用缓存
        boolean cache = (1 == DateUtil.format("yyyy-MM").compareTo(month));
        query.setCache(cache);

        MyFlowsTotal myTotal = null;
        if (null == (myTotal = this.service.myTotal(query)))
            return Result.UNEXIST;

        return new Result<>(
                new MyFlowsTotalDetail(myTotal.getSum(), myTotal.getOutlay(), myTotal.getIncome(), myTotal.getCtime()));
    }

}
