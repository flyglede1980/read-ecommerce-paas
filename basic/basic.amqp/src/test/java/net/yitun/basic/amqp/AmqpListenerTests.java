package net.yitun.basic.amqp;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

//import javax.annotation.PostConstruct;
//
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.amqp.core.ExchangeTypes;
//import org.springframework.amqp.rabbit.annotation.Exchange;
//import org.springframework.amqp.rabbit.annotation.Queue;
//import org.springframework.amqp.rabbit.annotation.QueueBinding;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.stereotype.Component;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component
@RabbitListener(bindings = { //
        @QueueBinding(key = { "cfs.sure.#" } //
                , exchange = @Exchange(value = "cfs-exchange", type = ExchangeTypes.TOPIC, durable = "true") //
                , value = @Queue(value = "cfs.sure", durable = "true", autoDelete = "false", exclusive = "false")) })
public class AmqpListenerTests {
//
//    @Autowired
//    private MQSender sender;
//
//    @PostConstruct
//    protected void init() {
//        SureMessage message = new SureMessage(123L, "CRM_USER");
//        sender.send(MQSender.UPFILE_EXCHANGE, MQSender.UPFILE_HANDLE_ROUTEKEY, message);
//    }
//
//    @RabbitHandler
//    public void handle(@Payload SureMessage message //
//            , Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long delivery
//        log.info(AmqpListener.class.getName() + " handle message: " + Json.toJson(message));
//        AckUtil.ack(delivery, channel); // 确认已消费消息
//    }
//
}
