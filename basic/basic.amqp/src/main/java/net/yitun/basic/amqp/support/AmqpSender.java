package net.yitun.basic.amqp.support;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component(value = "amqp.AmqpSender")
public class AmqpSender {

    @Autowired
    @Qualifier("rabbitTemplate")
    protected RabbitTemplate amqp;

    @Autowired
    @Qualifier("syncRabbitTemplate")
    protected RabbitTemplate syncAmqp;

    /**
     * 发送通用的MQ消息
     * 
     * @param exchange
     * @param routingKey
     * @param message
     * @return boolean true: 发送成功; false:发送失败
     */
    public boolean send(String exchange, String routingKey, Object message) {
        if (null == message)
            return false;

        try {
            this.amqp.convertAndSend(exchange, routingKey, message);
//            this.amqp.convertAndSend(exchange, routingKey, message, messagePostProcessor, correlationData);
        } catch (Exception e) {
            log.error("<<< " + this.getClass().getName() + ".send() send failed, " + exchange + ":" + routingKey + ", message:"
                    + JSON.toJSONString(message) + ", reason:" + e.getMessage(), e);
            return false;
        }

        if (log.isInfoEnabled())
            log.info("<<< " + this.getClass().getName() + ".send() send success, " + exchange + ":" + routingKey + ", message:"
                    + JSON.toJSONString(message));
        return true;
    }

    /**
     * 发送同步通用的MQ消息, 引入了事务
     * 
     * @param exchange
     * @param routingKey
     * @param message
     * @return boolean true: 发送成功; false:发送失败
     */
    public boolean syncSend(String exchange, String routingKey, Object message) {
        if (null == message)
            return false;

        try {
            this.syncAmqp.convertAndSend(exchange, routingKey, message);
        } catch (Exception e) {
            log.error("<<< " + this.getClass().getName() + ".syncSend() send failed, " + exchange + ":" + routingKey
                    + ", message:" + JSON.toJSONString(message) + ", reason:" + e.getMessage(), e);
            return false;
        }

        if (log.isInfoEnabled())
            log.info("<<< " + this.getClass().getName() + ".syncSend() send success," + exchange + ":" + routingKey
                    + ", message:" + JSON.toJSONString(message));
        return true;
    }

}
