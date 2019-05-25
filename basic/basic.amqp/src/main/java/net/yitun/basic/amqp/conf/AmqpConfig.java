package net.yitun.basic.amqp.conf;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <b>消息队列 配置.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月12日 上午11:30:03 LH
 *         new file.
 * </pre>
 */
@Slf4j
@Configuration
public class AmqpConfig {

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());

//        org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration
//        org.springframework.boot.autoconfigure.amqp.RabbitAnnotationDrivenConfiguration
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    @Primary
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) // 必须是prototype类型
    // 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        template.setChannelTransacted(false);
        return template;
    }

    @Bean("syncRabbitTemplate")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) // 必须是prototype类型
    // 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置
    public RabbitTemplate syncRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        template.setChannelTransacted(true);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置确认模式手工确认

        return factory;
    }

//    @Bean
//    public SimpleMessageListenerContainer messageContainer() {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//        container.setQueues(queue());
//        container.setExposeListenerChannel(true);
//        container.setMaxConcurrentConsumers(1);
//        container.setConcurrentConsumers(1);
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); // 设置确认模式手工确认
//        container.setMessageListener(new ChannelAwareMessageListener() {
//
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                byte[] body = message.getBody();
//                System.out.println("receive msg : " + new String(body));
//                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); // 确认消息成功消费
//            }
//        });
//        return container;
//    }
//
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setAddresses(host + ":" + port);
//        connectionFactory.setUsername(username);
//        connectionFactory.setPassword(password);
//        connectionFactory.setVirtualHost(virtualHost);
//        // 自动创建的ConnectionFactory无法完成事件的回调，即没有设置下面的代码
//        connectionFactory.setPublisherConfirms(publisherConfirms); // 如果要进行消息回调，则这里必须要设置为true
//        return connectionFactory;
//    }

//    @Bean // 消息队列
//    public Queue cfsQueue() {
//        // name, durable: 队列持久, exclusive:排他性,同一个连接可见,关闭连接删除该队列，同一个连接下的通道关闭不删除, autodelete
//        return new Queue("cfs.sure", true, false, false, null);
//    }
//
//    @Bean // 消息路由的交换器
//    public TopicExchange cfsExchange() {
//        return new TopicExchange("cfs-exchange", true, false);
//    }
//
//    TopicExchange(*、#模糊匹配routing key，routing key必须包含".")，DirectExchange，FanoutExchange(无routing key概念)
//    @Bean // 将 cfs.sure 队列 与 cfs-exchange 交换器绑定, binding_key 为 cfs.sure.# 模糊匹配, 只要路由Key为cfx.sure作为前缀即可放到对应的队列中
//    public Binding bindingCfsExchangeMessage(Queue cfsQueue, TopicExchange cfsExchange) {
//        return BindingBuilder.bind(cfsQueue).to(cfsExchange).with("cfs.sure.#");
//    }

}
