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
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.ioften.crm.MesgApi;
import net.yitun.ioften.crm.conf.Conf;
import net.yitun.ioften.crm.model.mesg.MesgStore;

/**
 * <pre>
 * <b>用户 新消息推送监听.</b>
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
        @QueueBinding(key = { Conf.MQ_ROUTEKEY_USER_MESGS } //
                , exchange = @Exchange(value = Conf.MQ_EXCHANGE, type = ExchangeTypes.TOPIC, durable = "true") //
                , value = @Queue(value = Conf.MQ_ROUTEKEY_USER_MESGS, durable = "true", autoDelete = "false", exclusive = "false")) })
public class NewMesgListener {

    @Autowired
    protected MesgApi mesgApi;

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());
    }

    @RabbitHandler
    public void handle(@Payload MesgStore message //
            , Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long delivery) {
        if (log.isInfoEnabled())
            log.info(">>> {}.handle() new mesg push, message:{}", this.getClass().getName(), JsonUtil.toJson(message));

        // 判断MQ消息是否有效
        if (null == message) {
            if (log.isWarnEnabled())
                log.warn("<<< {}.handle() new mesg push ingore, message:{}", this.getClass().getName(),
                        JsonUtil.toJson(message));
            AckUtil.ack(delivery, channel); // 应答消息已被成功处理
            return;
        }

        SecurityUtil.auto(0L); // 启用系统操作权限
        Result<Boolean> result = null;
        try {
            result = this.mesgApi.store(message); // 先对消息就行存档处理
        } catch (Exception e) {
            log.error("<<< {}.handle() new mesg store failed, message:{}, error:{}", //
                    this.getClass().getName(), JsonUtil.toJson(message), e.toString());
            return;
        } finally {
            AckUtil.ack(delivery, channel); // 应答消息已被成功处理
        }

        if (log.isInfoEnabled())
            log.info("<<< {}.handle() new mesg push success, message:{}, result:{}", //
                    this.getClass().getName(), JsonUtil.toJson(message), JsonUtil.toJson(result));
    }

}
