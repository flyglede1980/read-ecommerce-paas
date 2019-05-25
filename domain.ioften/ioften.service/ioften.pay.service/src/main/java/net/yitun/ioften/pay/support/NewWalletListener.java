package net.yitun.ioften.pay.support;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.amqp.support.AckUtil;
import net.yitun.basic.amqp.support.AmqpSender;
import net.yitun.basic.utils.IdUtil;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.ioften.crm.conf.Conf;
import net.yitun.ioften.pay.entity.Wallet;
import net.yitun.ioften.pay.service.WalletService;

/**
 * <pre>
 * <b>支付 监听处理新用户生成钱包账户.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月29日 下午4:12:59 LH
 *         new file.
 * </pre>
 */
@Slf4j
@Component
@RabbitListener(bindings = { //
        @QueueBinding(key = { Conf.MQ_ROUTEKEY_USER_SIGNUP + ".*" } //
                , exchange = @Exchange(value = Conf.MQ_EXCHANGE, type = ExchangeTypes.TOPIC, durable = "true") //
                , value = @Queue(value = Conf.MQ_ROUTEKEY_USER_SIGNUP
                        + ".wallet", durable = "true", autoDelete = "false", exclusive = "false")) })
public class NewWalletListener {

    @Autowired
    protected AmqpSender amqp;

    @Autowired
    protected WalletService service;

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());
    }

    @RabbitHandler
    public void handle(@Payload long[] message //
            , Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long delivery) {
        if (log.isInfoEnabled())
            log.info(">>> {}.handle() message:{}", this.getClass().getName(), JsonUtil.toJson(message));

        long uid = -1L;
        // 判断MQ消息是否有效, 不符合约定邀请或无效直接忽略该条消息
        if (null == message || 2 != message.length || IdUtil.MIN >= (uid = message[0])) {
            if (log.isWarnEnabled())
                log.warn("<<< {}.handle() ingore message:{}", this.getClass().getName(), JsonUtil.toJson(message));
            AckUtil.ack(delivery, channel); // 应答消息已被成功处理
            return;
        }

        Wallet wallet = null;
        try {
            // 执行创建用户钱包账户
            wallet = this.service.create(uid);
        } catch (Exception e) {
            log.error("<<< {}.handle() failed message:{}, error:{}", //
                    this.getClass().getName(), JsonUtil.toJson(message), e.toString());
            return;
        } finally {
            AckUtil.ack(delivery, channel); // 应答消息已被成功处理
        }

        if (log.isInfoEnabled())
            log.info("<<< {}.handle() success message:{}, wallet:{}", //
                    this.getClass().getName(), JsonUtil.toJson(message), JsonUtil.toJson(wallet));
    }

}
