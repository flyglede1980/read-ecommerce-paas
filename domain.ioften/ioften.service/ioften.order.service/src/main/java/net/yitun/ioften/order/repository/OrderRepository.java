package net.yitun.ioften.order.repository;

import net.yitun.ioften.order.entity.cards.CardOrders;
import net.yitun.ioften.order.entity.cards.Orders;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 订单信息、会员卡订单信息服务接口定义
 * @since 1.0.0
 * @see Repository
 * @see ResultType
 * @see Insert
 * @see Update
 * @see Orders
 *
 * @author Flyglede
 * @date 2019-01-11
 */
@Repository
public interface OrderRepository {
    /**
     * 订单信息表名定义
     */
    static final String T_OrderOrders = "order_orders";
    /**
     * 会员卡订单信息表名定义
     */
    static final String T_OrderCardOrders = "order_cardorders";
    /**
     * 订单信息表字段定义
     */
    static final String T_OrderOrders_Columns = "orderid, userid, contactperson, telephone, orderno, quality, ordermoney, dealmoney, "
            + "dealmoney, orderchannel, paychannel, paymethod, currency, rate, foreignno, remark, ordertime, ctime, utime, dimensioncode, type, isvalid";
    /**
     * 会员卡订单表字段定义
     */
    static final String T_OrderCardOrders_Columns = "orderid, cardtypeid, status";

    /**
     * 创建订单基本信息
     * @param orders--订单基本信息
     * @return true--创建成功;false--创建失败
     */
    @ResultType(Boolean.class)
    @Insert("insert into " + T_OrderOrders + "(" + T_OrderOrders_Columns + ") values(#{orderId}, #{userId}, #{contactPerson}, #{telephone}, " +
            "#{orderNo}, #{quality}, #{orderMoney}, #{dealMoney}, #{orderChannel}, #{payChannel}, #{payMethod}, #{currency}, #{rate}, " +
            "#{foreignNo}, #{remark}, #{orderTime}, #{cTime}, #{uTime}, #{dimensionCode}, #{type}, #{isValid})")
    boolean createOrder(Orders orders);

    /**
     * 创建订单基本信息
     * @param cardOrders--订单基本信息
     * @return true--创建成功;false--创建失败
     */
    @ResultType(Boolean.class)
    @Insert("insert into " + T_OrderCardOrders + "(" + T_OrderCardOrders_Columns + ") values(#{orderId}, #{cardTypeId}, #{status}")
    boolean createCardOrder(CardOrders cardOrders);
}