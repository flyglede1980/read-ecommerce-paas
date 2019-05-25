package net.yitun.basic.cache.support.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import net.yitun.basic.cache.utils.ByteUtil;

public class LongRedisSerializer implements RedisSerializer<Long> {

    @Override
    public byte[] serialize(Long value) throws SerializationException {
        return null == value ? null : ByteUtil.long2Bytes(value.longValue());
    }

    @Override
    public Long deserialize(byte[] bytes) throws SerializationException {
        return null == bytes ? null : ByteUtil.bytes2Long(bytes);
    }

}
