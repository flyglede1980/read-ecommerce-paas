package net.yitun.ioften.cms.support;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
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
import net.yitun.basic.utils.IdUtil;
import net.yitun.basic.utils.JsonUtil;
import net.yitun.ioften.cms.conf.Conf;
import net.yitun.ioften.cms.entity.Share;
import net.yitun.ioften.cms.service.ShareService;

/**
 * <pre>
 * <b>资讯 监听分享被展示.</b>
 * <b>Description:</b>
 *    MQ消息体为分享ID，根据该ID处理如下：
 *    1、判断分享是否首次展示，如果需要就行挖矿计算、派发入账操作；
 *    2、不管是否首次展示，均需要对分享中的浏览次数累加，以及协同对文章浏览次数累加处理；
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月19日 下午2:52:24
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月19日 下午2:52:24 LH
 *         new file.
 * </pre>
 */
@Slf4j
@Component
@RabbitListener(bindings = { //
        @QueueBinding(key = { Conf.MQ_ROUTEKEY_SHARE_SHOW } //
                , exchange = @Exchange(value = Conf.MQ_EXCHANGE, type = ExchangeTypes.TOPIC, durable = "true") //
                , value = @Queue(value = Conf.MQ_ROUTEKEY_SHARE_SHOW, durable = "true", autoDelete = "false", exclusive = "false")) })
public class ShareShowListener {

    @Autowired
    protected ShareService service;

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());
    }

    @RabbitHandler
    public void handle(@Payload Long id //
            , @Header(AmqpHeaders.DELIVERY_TAG) long delivery, Channel channel, Message message) {
        if (log.isInfoEnabled())
            log.info(">>> {}.handle() calc share gain, id:{}", this.getClass().getName(), id);

        // 判断分享的ID是否有效
        if (null == id || id <= IdUtil.MIN) {
            if (log.isWarnEnabled())
                log.warn("<<< {}.handle() calc share gain ingore, id:{}", this.getClass().getName(), id);
            AckUtil.ack(delivery, channel); // 默认应答消息已被处理成功
            return;
        }

        Result<Share> result = null;
        try {
            result = this.service.calcGain(id);
        } catch (Throwable e) {
            log.error("<<< {}.handle() calc share gain failed, id:{}, error:{}", this.getClass().getName(), id, e.toString());
            return;
        } finally {
            AckUtil.ack(delivery, channel); // 应答消息已被成功处理
        }

        if (log.isInfoEnabled())
            log.info("<<< {}.handle() calc share gain success, id:{}, result:{}", this.getClass().getName(), id,
                    JsonUtil.toJson(result));
    }

}
