package net.yitun.basic.cache.support;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * <pre>
 * <b>计数器操作器.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月16日 下午7:00:36 LH
 *         new file.
 * </pre>
 */
public class AtomicOperations<K, V> {

    protected RedisTemplate<K, V> template;

    public AtomicOperations(RedisTemplate<K, V> template) {
        this.template = template;
    }

    public long time() {
        return execute(connection -> connection.time(), true);
    }

    public Long incr(final K key, long value) {
        byte[] rawKey = rawKey(key);
        return execute(connection -> connection.incrBy(rawKey, value), true);
    }

    public Long incr(final K key, long value, long timeout, TimeUnit unit) {
        long incr = this.incr(key, value);
        this.expire(key, timeout, unit);
        return incr;
    }

    public Long decr(final K key, long value) {
        byte[] rawKey = rawKey(key);
        return execute(connection -> connection.decrBy(rawKey, value), true);
    }

    public Long decr(final K key, long value, long timeout, TimeUnit unit) {
        long incr = this.decr(key, value);
        this.expire(key, timeout, unit);
        return incr;
    }

    public Long getIncr(final String key) {
        byte[] rawKey = rawKey(key);
        return execute(connection -> {
            byte[] rawValue = connection.get(rawKey);
            try {
                Object value = strSerializer().deserialize(rawValue);
                return Long.parseLong(String.valueOf(value));
            } catch (Exception e) {
                return null;
            }
        }, true);
    }

    public Boolean delete(K key) {
        byte[] rawKey = rawKey(key);
        Long result = execute(connection -> connection.del(rawKey), true);
        return result != null && result.intValue() == 1;
    }

    public Boolean expire(K key, final long timeout, final TimeUnit unit) {
        byte[] rawKey = rawKey(key);
        long rawTimeout = TimeoutUtils.toMillis(timeout, unit);
        return execute(connection -> {
            try {
                return connection.pExpire(rawKey, rawTimeout);
            } catch (Exception e) {
                // Driver may not support pExpire or we may be running on Redis 2.4
                return connection.expire(rawKey, TimeoutUtils.toSeconds(timeout, unit));
            }
        }, true);
    }

    @SuppressWarnings("unchecked")
    byte[] rawKey(Object key) {
        Assert.notNull(key, "non null key required");
        if (keySerializer() == null && key instanceof byte[])
            return (byte[]) key;
        return keySerializer().serialize(key);
    }

    @SuppressWarnings("unchecked")
    byte[] rawValue(Object value) {
        if (valueSerializer() == null && value instanceof byte[])
            return (byte[]) value;
        return valueSerializer().serialize(value);
    }

    @Nullable
    <T> T execute(RedisCallback<T> callback, boolean b) {
        return template.execute(callback, b);
    }

    @SuppressWarnings("rawtypes")
    RedisSerializer strSerializer() {
        return template.getStringSerializer();
    }

    @SuppressWarnings("rawtypes")
    RedisSerializer keySerializer() {
        return template.getKeySerializer();
    }

    @SuppressWarnings("rawtypes")
    RedisSerializer valueSerializer() {
        return template.getValueSerializer();
    }
}