package net.yitun.basic.cache.support.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import net.yitun.basic.cache.utils.ByteUtil;

public class IntRedisSerializer implements RedisSerializer<Integer> {

    @Override
    public byte[] serialize(Integer value) throws SerializationException {
        return null == value ? null : ByteUtil.int2Bytes(value.intValue());
    }

    @Override
    public Integer deserialize(byte[] bytes) throws SerializationException {
        return null == bytes ? null : ByteUtil.bytes2Int(bytes);
    }

}
