package net.yitun.ioften.crm.support;

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

/**
 * <pre>
 * <b>用户 监听处理新邀请注册.</b>
 * <b>Description:</b>
 *     // TODO 监听邀请注册, 如果是被邀请用户，需要为邀请用户派发挖矿奖励
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
                        + ".invite", durable = "true", autoDelete = "false", exclusive = "false")) })
public class SignupInviteListener {

    @Autowired
    protected AmqpSender amqp;

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());

        // // 测试注册MQ通道是否可用
        // this.amqp.send(Constant.MQ_EXCHANGE //
        // , Constant.MQ_ROUTEKEY_USER_SIGNUP + ".byphone", new long[] { 1L, 2L });
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

        System.out.println("<<< uid:" + uid);

        AckUtil.ack(delivery, channel); // 应答消息已被成功处理
    }

}
