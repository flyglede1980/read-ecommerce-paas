package net.yitun.basic.amqp.support;

import com.rabbitmq.client.Channel;
// import com.rabbitmq.client.Envelope;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AckUtil {

    // 手动ack
    // channel.basicAck(envelope.getDeliveryTag(), false);
    // 重新放入队列
    // channel.basicNack(envelope.getDeliveryTag(), false, true);
    // 抛弃此条消息
    // channel.basicNack(envelope.getDeliveryTag(), false, false);
    // channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);

    /**
     * 手动ACK应答
     * 
     * @param delivery
     * @param channel
     * @return boolean true:成功; false:失败
     */
    public static boolean ack(long delivery, Channel channel) {
        try {
            channel.basicAck(delivery, false);
        } catch (Exception e) {
            log.error("<<< {}.ack() mq ack falid, delivery:{}, error:{}", AckUtil.class.getName(), delivery, e.toString());
            return false;
        }
        return true;
    }

    /**
     * 重新入队列，防止业务异常丢失
     * 
     * @param delivery
     * @param channel
     * @return boolean true:成功; false:失败
     */
    public static boolean nack(long delivery, Channel channel) {
        try {
            channel.basicNack(delivery, false, false);
        } catch (Exception e) {
            log.error("<<< {}.nack() mq nack falid, delivery:{}, error:{}", AckUtil.class.getName(), delivery, e.toString());
            return false;
        }
        return true;
    }

    /**
     * 抛弃此条消息
     * 
     * @param delivery
     * @param channel
     * @return boolean true:成功; false:失败
     */
    public static boolean reject(long delivery, Channel channel) {
        try {
            channel.basicReject(delivery, true);
        } catch (Exception e) {
            log.error("<<< {}.reject() mq nack falid, delivery:{}, error:{}", AckUtil.class.getName(), delivery, e.toString());
            return false;
        }
        return true;
    }

}
