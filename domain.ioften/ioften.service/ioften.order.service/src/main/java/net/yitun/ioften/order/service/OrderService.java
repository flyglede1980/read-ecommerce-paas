package net.yitun.ioften.order.service;

import net.yitun.basic.model.Result;
import net.yitun.ioften.order.entity.cards.Orders;
import net.yitun.ioften.order.model.cardorder.CardOrderCreate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 会员卡订单、课程订单、商品订单信息服务接口定义
 * @since 1.0.0
 * @see Repository
 * @see CardOrderCreate
 * @see Insert
 * @see Update
 * @author Flyglede
 */
public interface OrderService {

    /**
     * 创建订单基本信息及会员卡订单信息
     * @param model--订单基本信息及会员卡订单信息
     * @return 创建操作结果信息
     */
    Result<Orders> createCardOrder(CardOrderCreate model);
}
