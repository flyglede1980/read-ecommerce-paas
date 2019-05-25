package net.yitun.basic.cache.conf;

import java.time.Duration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.cache.support.AtomicOperations;
import net.yitun.basic.cache.support.cache.AutoRedisCacheManager;
import net.yitun.basic.cache.support.serializer.IntRedisSerializer;
import net.yitun.basic.cache.support.serializer.KryoRedisSerializer;
import net.yitun.basic.cache.support.serializer.LongRedisSerializer;
import net.yitun.basic.cache.utils.IocUtil;

/**
 * <pre>
 * <b>缓存 配置.</b>
 * <b>Description:</b>
 * 
 * org.springframework.cache.Cache
 * org.springframework.cache.CacheManager
 * org.springframework.data.redis.cache.RedisCacheManager
 * 
 * org.springframework.cache.interceptor.CacheInterceptor
 * 
 * &#64;CachePut，@CacheEvict， @Cacheable
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
@Order(1)
@Configuration
@EnableCaching // 开启基于Spring注解的缓存机制, 默认会自动一个CacheManager的bean, 也可以自定义Bean替换掉CacheManager
public class CacheConfig {

    @Autowired
    protected ApplicationContext context;

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());

        IocUtil.setContext(this.context);

        // org.springframework.cache.interceptor.CacheInterceptor
        // org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
    }

    @Bean // 显示声明缓存key生成器
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    // 缓存默认TTL, 默认: 604800秒 -> 7D
    @Value("${spring.cache.expiration: 604800}")
    private long cacheExpiration;

    /**
     * 重写RedisCacheManager的getCache方法，实现设置key的有效时间 <br/>
     * 重写RedisCache的get方法，实现触发式自动刷新
     * <p>
     * 自动刷新方案：<br/>
     * 1、获取缓存后再获取一次有效时间，拿这个时间和我们配置的自动刷新时间比较，如果小于这个时间就刷新。 <br/>
     * 2、每次创建缓存的时候维护一个Map，存放key和方法信息（反射）。当要刷新缓存的时候，根据key获取方法信息。 <br/>
     * 通过获取其代理对象执行方法，刷新缓存。
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration //
                .defaultCacheConfig() //
                .entryTtl(Duration.ofSeconds(this.cacheExpiration)) // 设置缓存有效期一小时
                // .disableCachingNullValues() // 禁用缓存NULL值
                .serializeKeysWith(SerializationPair.fromSerializer(strRedisSerializer())) //
                .serializeValuesWith(SerializationPair.fromSerializer(kryoRedisSerializer()));

        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);

        return new AutoRedisCacheManager(cacheWriter, cacheConfiguration);
        // RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory)).cacheDefaults(cacheConfiguration).build();
    }

    @Bean
    public StringRedisSerializer strRedisSerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    public IntRedisSerializer intRedisSerializer() {
        return new IntRedisSerializer();
    }

    @Bean
    public LongRedisSerializer longRedisSerializer() {
        return new LongRedisSerializer();
    }

    @Bean
    public FastJsonRedisSerializer<Object> jsonRedisSerializer() {
        // 建议使用这种方式，小范围指定白名单
        ParserConfig.getGlobalInstance().addAccept("net.yitun.");
        // ParserConfig.getGlobalInstance().setAutoTypeSupport(true); // 全局开启 AutoType，不建议使用
        // return new GenericFastJsonRedisSerializer(); // 此Serializer也默认开启了AutoTypeSupport

        // 实例化Json序列化并设置必要选项
        FastJsonRedisSerializer<Object> serializer = new FastJsonRedisSerializer<>(Object.class);
        serializer.getFastJsonConfig().setSerializerFeatures(SerializerFeature.WriteBigDecimalAsPlain,
                SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);

//      SerializerFeature: 序列化选项
//      SortField       
//      WriteClassName
//      WriteMapNullValue
//      DisableCircularReferenceDetect 
//      QuoteFieldNames                   输出key时是否使用双引号,默认为true
//      WriteMapNullValue                 是否输出值为null的字段,默认为false
//      WriteNullNumberAsZero             数值字段如果为null,输出为0,而非null
//      WriteNullListAsEmpty              List字段如果为null,输出为[],而非null
//      WriteNullStringAsEmpty            字符类型字段如果为null,输出为”“,而非null
//      WriteNullBooleanAsFalse           Boolean字段如果为null,输出为false,而非null

        return serializer;
    }

    @Bean
    public KryoRedisSerializer<Object> kryoRedisSerializer() {
        return new KryoRedisSerializer<>(Object.class);
    }

//    @Bean
//    public Jackson2JsonRedisSerializer<Object> jsonRedisSerializer() {
//        Jackson2JsonRedisSerializer<Object> serializer //
//                = new Jackson2JsonRedisSerializer<>(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        serializer.setObjectMapper(om);
//        return serializer;
//    }

    @Bean
    public RedisTemplate<Object, Object> objRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(kryoRedisSerializer()); // strRedisSerializer()
        template.setHashKeySerializer(kryoRedisSerializer()); // strRedisSerializer()

        template.setValueSerializer(kryoRedisSerializer()); // jsonRedisSerializer()
        template.setHashValueSerializer(kryoRedisSerializer()); // strRedisSerializer()

        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisTemplate<String, Object> strObjRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(strRedisSerializer());
        template.setHashKeySerializer(strRedisSerializer());

        template.setValueSerializer(longRedisSerializer());
        template.setHashValueSerializer(longRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisTemplate<String, Long> strLongRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Long> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        template.setKeySerializer(strRedisSerializer());
        template.setHashKeySerializer(strRedisSerializer());

        template.setValueSerializer(longRedisSerializer());
        template.setHashValueSerializer(longRedisSerializer());

        template.afterPropertiesSet();
        return template;
    }

    // Operations<String, String> =================================================================
    @Bean
    public ValueOperations<String, String> strValOperations(RedisTemplate<String, String> strRedisTemplate) {
        return strRedisTemplate.opsForValue();
    }

    @Bean
    public SetOperations<String, String> strSetOperations(RedisTemplate<String, String> strRedisTemplate) {
        return strRedisTemplate.opsForSet();
    }

    @Bean
    public ZSetOperations<String, String> strZSetOperations(RedisTemplate<String, String> strRedisTemplate) {
        return strRedisTemplate.opsForZSet();
    }

    // Operations<String, Long> =================================================================
    @Bean
    public ValueOperations<String, Long> longValOperations(RedisTemplate<String, Long> longRedisTemplate) {
        return longRedisTemplate.opsForValue();
    }

    @Bean
    public SetOperations<String, Long> longSetOperations(RedisTemplate<String, Long> longRedisTemplate) {
        return longRedisTemplate.opsForSet();
    }

    @Bean
    public ZSetOperations<String, Long> longZSetOperations(RedisTemplate<String, Long> longRedisTemplate) {
        return longRedisTemplate.opsForZSet();
    }

    @Bean
    public AtomicOperations<String, Long> atomicOperations(RedisTemplate<String, Long> longRedisTemplate) {
        return new AtomicOperations<>(longRedisTemplate);
    }

}
