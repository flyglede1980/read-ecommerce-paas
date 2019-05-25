package net.yitun.ioften.pay.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

import net.yitun.basic.utils.DateUtil;
import net.yitun.ioften.pay.conf.Constant;
import net.yitun.ioften.pay.entity.Flows;
import net.yitun.ioften.pay.entity.vo.flows.FlowsQueryVo;
import net.yitun.ioften.pay.entity.vo.flows.MyFlowsTotal;
import net.yitun.ioften.pay.model.flows.FlowsQuery;
import net.yitun.ioften.pay.model.flows.MyFlowsTotalQuery;
import net.yitun.ioften.pay.repository.FlowsRepository;
import net.yitun.ioften.pay.service.FlowsService;

@Service("pay.FlowsService")
public class FlowsServiceImpl implements FlowsService {

    @Autowired
    protected FlowsRepository repository;

    @Override
    public Page<Flows> query(FlowsQuery query) {
        FlowsQueryVo queryVo //
                = new FlowsQueryVo(null, query.getUid(), query.getStime(), query.getEtime(), query.getWay());
        return this.repository.query(queryVo);
    }

    @Override
    @Cacheable(key = "#query.uid+':'+#query.month", value = Constant.CKEY_FLOWS_MY //
            , condition = "null!=#query && #query.cache && null!=#query.uid && null!=#query.month && (null==#query.way || 0==#query.way.size())")
    public MyFlowsTotal myTotal(MyFlowsTotalQuery query) {
        FlowsQueryVo queryVo //
                = new FlowsQueryVo(null, query.getUid(), query.getStime(), query.getEtime(), query.getWay());
        MyFlowsTotal myTotal = this.repository.myTotal(queryVo);
        if (null != myTotal)
            myTotal.setCtime(new Date(System.currentTimeMillis()));
        else
            myTotal = new MyFlowsTotal(0L, 0L, 0L, new Date(System.currentTimeMillis()));
        return myTotal;
    }

    @Override
    @Cacheable(key = "#uid+':'+#dayOrMonth", condition = "#cache", value = Constant.CKEY_FLOWS_MY)
    public MyFlowsTotal myTotal(Long uid, String dayOrMonth, boolean cache) {
        Date stime = null, etime = null;
        if (7 == dayOrMonth.length()) {
            Date date = DateUtil.parse(dayOrMonth, "yyyy-MM");
            stime = DateUtil.monthOfStartTime(date);
            etime = DateUtil.monthOfEndTime(date);
        } else if (10 == dayOrMonth.length()) {
            Date date = DateUtil.parse(dayOrMonth, "yyyy-MM-dd");
            stime = DateUtil.dayOfStartTime(date);
            etime = DateUtil.dayOfEndTime(date);
        } else
            return null;

        return this.myTotal(new MyFlowsTotalQuery(uid, null, null, stime, etime, false));
    }

}
