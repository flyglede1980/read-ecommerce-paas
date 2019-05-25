package net.yitun.basic.cache.support.cache;

import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.Nullable;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <b>Redis缓存管理器.</b>
 * <b>Description:</b>
 * 支持缓存过期时间和在临近时间点自动刷新缓存。
 * 注解配置失效时间简单的方法就是在容器名称上动动手脚，通过解析特定格式的名称来变向实现失效时间的获取。比如第一个#后面的5可以定义为失效时间，第二个#后面的2是刷新缓存的时间
 * 
 *    https://blog.csdn.net/xiaolyuh123/article/details/78620302
 *    https://github.com/wyh-spring-ecosystem-student/spring-boot-student/tree/releases/spring-boot-student-cache-redis
 *    http://www.cnblogs.com/ASPNET2008/p/6511500.html
 *    https://github.com/yantrashala/spring-cache-self-refresh
 *    https://www.jianshu.com/p/c5b2617b83e2
 *    https://github.com/xiaolyuh/layering-cache/wiki/%E6%89%93%E5%BC%80%E5%86%85%E7%BD%AE%E7%9A%84%E7%9B%91%E6%8E%A7%E9%A1%B5%E9%9D%A2
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月29日 下午7:54:06 LH
 *         new file.
 * </pre>
 */
@Slf4j
public class AutoRedisCacheManager extends RedisCacheManager {

    protected RedisCacheWriter cacheWriter;

    protected RedisCacheConfiguration defaultCacheConfig;

    public AutoRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
        this.cacheWriter = cacheWriter;
        this.defaultCacheConfig = defaultCacheConfiguration;

        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());
    }

    @Override
    public Cache getCache(String name) {
        return super.getCache(name);
    }

    @Override
    protected RedisCache createRedisCache(String name, @Nullable RedisCacheConfiguration cacheConfig) {
        return new AutoRedisCache(name, this.cacheWriter, cacheConfig != null ? cacheConfig : this.defaultCacheConfig);
    }

}
