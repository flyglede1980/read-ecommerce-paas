package net.yitun.ioften.order.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.Code;
import net.yitun.basic.model.Result;
import net.yitun.basic.utils.IdUtil;
import net.yitun.ioften.order.entity.cards.CardOrders;
import net.yitun.ioften.order.entity.cards.Orders;
import net.yitun.ioften.order.model.cardorder.CardOrderCreate;
import net.yitun.ioften.order.repository.OrderRepository;
import net.yitun.ioften.order.service.OrderService;
import net.yitun.ioften.order.util.OrderNoCreator;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员卡订单、课程订单、商品订单信息服务接口实现
 * @since 1.0.0
 * @see OrderNoCreator
 * @see Orders
 * @see OrderRepository
 * @see Update
 * @author Flyglede
 */
@Slf4j
@Service("orders.OrderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    protected OrderRepository orderRepository;

    /**
     * 创建订单基本信息及会员卡订单信息
     * @param model--订单基本信息及会员卡订单信息
     * @return 创建操作结果信息
     */
    public Result<Orders> createCardOrder(CardOrderCreate model) {
        Long orderId = IdUtil.id();
        String orderNo = OrderNoCreator.getOrderNo(20);
        Date now = new Date(System.currentTimeMillis());
        Long userId = model.getUserId();
        Long cardTypeId = model.getCardTypeId();
        Integer quality = model.getQuality();
        Integer payChannel = model.getPayChannel();
        BigDecimal orderMoney = model.getOrderMoney();
        BigDecimal rate = new BigDecimal("0.00");
        boolean blCreateOrder = false;
        boolean blCreateCardOrder = false;
        Orders order = new Orders(orderId, userId, "", "", orderNo, quality, orderMoney, orderMoney, 1, payChannel,
                2, 3, rate, "", "", now, now, now ,"", 1, 1);
        blCreateOrder = this.orderRepository.createOrder(order);
        if(!blCreateOrder)
            return new Result<>(Code.BAD_REQ, "订单信息创建失败");
        blCreateCardOrder = this.orderRepository.createCardOrder(new CardOrders(orderId, cardTypeId, 2));
        if(!blCreateCardOrder)
            return new Result<>(Code.BAD_REQ, "会员卡订单信息创建失败");
        return new Result<Orders>(Code.OK,"会员卡订单信息创建成功");
    }
}