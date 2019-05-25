package net.yitun.basic.cache.support.cache;

import java.time.Duration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;

/**
 * <pre>
 * <b>Redis缓存器.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018年12月9日 下午11:20:32
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年12月9日 下午11:20:32 LH
 *         new file.
 * </pre>
 */
public class AutoRedisCache extends RedisCache {

    protected Duration ttl;

    protected final String name;

    protected static final String separator = "#";

    protected RedisCacheWriter cacheWriter;

    protected RedisCacheConfiguration cacheConfig;

    public AutoRedisCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig) {
        super(StringUtils.split(name, separator)[0], cacheWriter, cacheConfig);

        this.ttl = cacheConfig.getTtl();
        this.cacheWriter = cacheWriter;
        this.cacheConfig = cacheConfig;

        // 处理名字中携带的自定义TTL和自动刷新缓存控制
        String[] names = StringUtils.split(name, separator);
        this.name = names[0];
        if (1 < names.length)
            this.ttl = Duration.ofSeconds(Long.valueOf(names[1]));

        if (2 < names.length)
            Duration.ofSeconds(Long.valueOf(names[2]));
    }

    @Override
    public void put(Object key, Object value) {
        Object cacheValue = preProcessCacheValue(value);
        if (!isAllowNullValues() && null == cacheValue)
            throw new IllegalArgumentException(String.format(
                    "Cache '%s' does not allow 'null' values. Avoid storing null via '@Cacheable(unless=\"#result == null\")' or configure RedisCache to allow 'null' via RedisCacheConfiguration.",
                    this.name));
        this.cacheWriter.put(this.name, createAndConvertCacheKey(key), serializeCacheValue(cacheValue), this.ttl); // cacheConfig.getTtl()
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        Object cacheValue = preProcessCacheValue(value);
        if (!isAllowNullValues() && null == cacheValue)
            return get(key);
        byte[] result = this.cacheWriter.putIfAbsent(this.name, createAndConvertCacheKey(key), serializeCacheValue(cacheValue),
                this.ttl); // cacheConfig.getTtl()
        return null == result ? null : new SimpleValueWrapper(fromStoreValue(deserializeCacheValue(result)));
    }

    protected byte[] createAndConvertCacheKey(Object key) {
        return serializeCacheKey(createCacheKey(key));
    }

    /**
     * 刷新缓存数据
     */
//    private void refreshCache(Object key, String cacheKeyStr) {
//        Long ttl = this.redisOperations.getExpire(cacheKeyStr);
//        if (null != ttl && ttl <= CustomizedRedisCache.this.preloadSecondTime) {
//            // 尽量少的去开启线程，因为线程池是有限的
//            ThreadTaskHelper.run(new Runnable() {
//                @Override
//                public void run() {
//                    // 加一个分布式锁，只放一个请求去刷新缓存
//                    RedisLock redisLock = new RedisLock((RedisTemplate) redisOperations, cacheKeyStr + "_lock");
//                    try {
//                        if (redisLock.lock()) {
//                            // 获取锁之后再判断一下过期时间，看是否需要加载数据
//                            Long ttl = CustomizedRedisCache.this.redisOperations.getExpire(cacheKeyStr);
//                            if (null != ttl && ttl <= CustomizedRedisCache.this.preloadSecondTime) {
//                                // 通过获取代理方法信息重新加载缓存数据
//                                CustomizedRedisCache.this.getCacheSupport()
//                                        .refreshCacheByKey(CustomizedRedisCache.super.getName(), key.toString());
//                            }
//                        }
//                    } catch (Exception e) {
//                        logger.info(e.getMessage(), e);
//                    } finally {
//                        redisLock.unlock();
//                    }
//                }
//            });
//        }
//    }

}
