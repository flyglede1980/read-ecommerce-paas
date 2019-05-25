package net.yitun.ioften.pay.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.github.pagehelper.Page;

import net.yitun.ioften.pay.entity.Flows;
import net.yitun.ioften.pay.entity.vo.flows.FlowsQueryVo;
import net.yitun.ioften.pay.entity.vo.flows.MyFlowsTotal;

/**
 * <pre>
 * <b>支付 流水持久化.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月6日 下午5:57:16 LH
 *         new file.
 * </pre>
 */
@Repository("pay.FlowsRepository")
public interface FlowsRepository {

    // 表名
    static final String TABLE = "pay_flows";

    // 查询字段
    static final String COLUMNS = "t1.id, t1.uid, t1.way, t1.amount, t1.currency, t1.target, t1.channel, t1.out_flows, t1.new_give, t1.new_income, t1.new_balance, t1.remark, t1.status, t1.ctime, t1.mtime";

    /** 分页流水 */
    @ResultType(Flows.class)
    @Select("<script> " //
            + "select " + COLUMNS + " from " + TABLE + " t1" //
            + " where valid=1 and uid=#{uid} and ctime between #{stime} and #{etime}"
            + "   <if test='null!=ways'><foreach collection='ways' item='item' separator=', ' open=' and t1.way in (' close=')'>#{item}</foreach></if></script>")
    Page<Flows> query(FlowsQueryVo query);

    /** 流水合计 */
    @ResultType(MyFlowsTotal.class)
    @Select("<script> " //
            + "select sum(case when amount &lt; 0 then amount else 0 end) as outlay"
            + "    , sum(case when amount &gt; 0 then amount else 0 end) as income" //
            + " from " + TABLE + " t1" //
            + " where valid=1 and uid=#{uid} and ctime between #{stime} and #{etime}"
            + "   <if test='null!=ways'><foreach collection='ways' item='item' separator=', ' open=' and t1.way in (' close=')'>#{item}</foreach></if></script>")
    MyFlowsTotal myTotal(FlowsQueryVo query);

    /** 新增流水 */
    @ResultType(Boolean.class)
    @Insert("insert into " + TABLE //
            + " ( id, uid, way, amount, currency, target, channel, out_flows, new_give, new_income, new_balance, remark, status, ctime, mtime, valid )" //
            + " values ( #{id}, #{uid}, #{way}, #{amount}, #{currency}, #{target}, #{channel}, #{outFlows}, #{newGive}, #{newIncome}, #{newBalance}, #{remark}, #{status}, #{ctime}, #{mtime}, 1 )")
    boolean create(Flows flows);
}
