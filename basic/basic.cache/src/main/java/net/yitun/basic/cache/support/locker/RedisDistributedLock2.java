//package net.yitun.basic.cache.support.locker;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
//import org.springframework.dao.DataAccessException;
//import org.springframework.data.redis.connection.RedisConnection;
//import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
//import org.springframework.data.redis.core.RedisCallback;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.types.Expiration;
//import org.springframework.util.Assert;
//import org.springframework.util.StringUtils;
//
//import lombok.extern.slf4j.Slf4j;
//
////import redis.clients.jedis.JedisCluster;
////import redis.clients.jedis.JedisCommands;
//
///**
// * <pre>
// * <b>Redis 分布式锁.</b>
// * <b>Description:</b>
// * Redis分布式锁使用 SET key value NX EX expire 实现, 
// * 方案在 Redis 官方 SET 命令页有详细介绍 -> http://doc.redisfans.com/string/set.html
// * 
// * EX second ：设置键的过期时间为 second 秒。 SET key value EX second 效果等同于 SETEX key second value 。
// * PX millisecond ：设置键的过期时间为 millisecond 毫秒。 SET key value PX millisecond 效果等同于 PSETEX key millisecond value 。
// * NX ：只在键不存在时，才对键进行设置操作。 SET key value NX 效果等同于 SETNX key value 。
// * XX ：只在键已经存在时，才对键进行设置操作。
// * 
// * EX: 以秒为单位设置 key 的过期时间，等效于EXPIRE key seconds
// * NX: 当 key 不存在时, 将key的值设为value, 当且key 不存在，等效于 SETNX
// *    
// * <b>Author:</b> LH
// * <b>Date:</b> 2019年1月10日 下午7:02:17
// * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
// * <b>Changelog:</b>
// *   Ver   Date                    Author                Detail
// *   ----------------------------------------------------------------------
// *   0.1   2019年1月10日 下午7:02:17 LH
// *         new file.
// * </pre>
// */
//@Slf4j
////@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
//public class RedisDistributedLock2 {
//
//    /** 调用set后的返回值 */
//    private static final String OK = "OK";
//
//    /** 解锁的lua脚本 */
//    public static final String UNLOCK_LUA;
//
//    static {
//        StringBuilder sb = new StringBuilder();
//        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
//        sb.append("then ");
//        sb.append("    return redis.call(\"del\",KEYS[1]) ");
//        sb.append("else ");
//        sb.append("    return 0 ");
//        sb.append("end ");
//        UNLOCK_LUA = sb.toString();
//    }
//
//    /** 锁标志对应的key */
//    private String lockKey;
//
//    /** 锁对应的值 */
//    private String lockValue;
//
//    /** 记录到日志的锁标志对应的key */
//    private String lockKeyLog = "";
//
//    /** 请求锁的超时时间(ms), 默认: 100 */
//    private long timeOut = 100;
//
//    /** 锁的有效时间(s), 默认: 60 */
//    private long expireTime = 60;
//
//    final Random random = new Random();
//
//    /** 锁标记 */
//    private volatile boolean locked = false;
//
//    private RedisTemplate<Object, Object> redisTemplate;
//
//    /**
//     * 默认锁的过期时间和请求锁的超时时间
//     *
//     * @param redisTemplate
//     * @param lockKey       锁的key
//     */
//    public RedisDistributedLock2(RedisTemplate<Object, Object> redisTemplate, String lockKey) {
//        this.redisTemplate = redisTemplate;
//        this.lockKey = lockKey + "_lock";
//    }
//
//    /**
//     * 默认锁的过期时间，指定请求锁的超时时间
//     *
//     * @param redisTemplate
//     * @param lockKey       锁的key
//     * @param timeOut       请求锁的超时时间(单位：毫秒)
//     */
//    public RedisDistributedLock2(RedisTemplate<Object, Object> redisTemplate, String lockKey, long timeOut) {
//        this(redisTemplate, lockKey);
//        this.timeOut = timeOut;
//    }
//
//    /**
//     * 默认请求锁的超时时间，指定锁的过期时间
//     *
//     * @param redisTemplate
//     * @param lockKey       锁的key
//     * @param expireTime    锁的过期时间(单位：秒)
//     */
//    public RedisDistributedLock2(RedisTemplate<Object, Object> redisTemplate, String lockKey, int expireTime) {
//        this(redisTemplate, lockKey);
//        this.expireTime = expireTime;
//    }
//
//    /**
//     * 指定锁的过期时间和请求锁的超时时间
//     *
//     * @param redisTemplate
//     * @param lockKey       锁的key
//     * @param timeOut       请求锁的超时时间(单位：毫秒)
//     * @param expireTime    锁的过期时间(单位：秒)
//     */
//    public RedisDistributedLock2(RedisTemplate<Object, Object> redisTemplate, String lockKey, long timeOut,
//            int expireTime) {
//        this(redisTemplate, lockKey);
//        this.timeOut = timeOut;
//        this.expireTime = expireTime;
//    }
//
//    /**
//     * 尝试获取锁, 立即返回
//     *
//     * @return 是否获得锁成功
//     */
//    public boolean lock() {
//        // 不存在则添加 且设置过期时间（单位ms）
//        this.lockValue = UUID.randomUUID().toString();
//        String result = this.set(this.lockKey, this.lockValue, this.expireTime);
//        this.locked = OK.equalsIgnoreCase(result);
//        return this.locked;
//    }
//
//    /**
//     * 尝试获取锁, 超时返回
//     *
//     * @return 是否尝试获得锁成功
//     */
//    public boolean lockTry() {
//        // 生成随机key
//        this.lockValue = UUID.randomUUID().toString();
//        long timeout = timeOut * 1000000; // 请求锁超时时间，纳秒
//        long nowTime = System.nanoTime(); // 系统当前时间，纳秒
//
//        while ((System.nanoTime() - nowTime) < timeout) {
//            String result = this.set(lockKey, lockValue, expireTime);
//            if (OK.equalsIgnoreCase(result)) {
//                this.locked = true;
//                return this.locked; // 上锁成功结束请求
//            }
//
//            // 每次请求等待一段时间
//            sleep(10, 50000);
//        }
//
//        return this.locked;
//    }
//
//    /**
//     * 阻塞方式获取锁
//     *
//     * @return 是否获得锁成功
//     */
//    public boolean lockBlock() {
//        this.lockValue = UUID.randomUUID().toString();
//        while (true) {
//            // 不存在则添加 且设置过期时间（单位ms）
//            String result = this.set(this.lockKey, this.lockValue, this.expireTime);
//            if (OK.equalsIgnoreCase(result)) {
//                this.locked = true;
//                return this.locked;
//            }
//
//            // 每次请求等待一段时间
//            sleep(10, 50000);
//        }
//    }
//
//    /**
//     * <pre>
//     * 解锁
//     * 可以通过以下修改，让这个锁实现更健壮：
//     * 不使用固定的字符串作为键的值，而是设置一个不可猜测（non-guessable）的长随机字符串，作为口令串（token）。 
//     * 不使用 DEL命令来释放锁，而是发送一个 Lua 脚本，这个脚本只在客户端传入的值和键的口令串相匹配时，才对键进行删除。
//     * 这两个改动可以防止持有过期锁的客户端误删现有锁的情况出现。
//     * </pre>
//     */
//    public Boolean unlock() {
//        // 只有加锁成功并且锁还有效才去释放锁
//        if (!this.locked)
//            return true;
////        return (Boolean) this.redisTemplate.execute(connection -> {
////            return  connection.eval(UNLOCK_LUA.getBytes(), ReturnType.INTEGER, 1, null);
////        }, true);
//
//        return (Boolean) this.redisTemplate.execute(new RedisCallback<Boolean>() {
//            @Override
//            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
//                Long result = 0L;
//                List<String> keys = Arrays.asList(lockKey);
//                List<String> values = Arrays.asList(lockValue);
//                Object conn = connection.getNativeConnection();
//
////                // 集群模式
////                if (conn instanceof JedisCluster)
////                    result = (Long) ((JedisCluster) conn).eval(UNLOCK_LUA, keys, values);
////
////                // 单机模式
////                if (conn instanceof Jedis)
////                    result = (Long) ((Jedis) conn).eval(UNLOCK_LUA, keys, values);
//
//                if (result == 0 && !StringUtils.isEmpty(lockKeyLog))
//                    log.info("Redis分布式锁，解锁{}失败！解锁时间：{}", lockKeyLog, System.currentTimeMillis());
//
//                locked = result == 0;
//                return result == 1;
//            }
//        });
//    }
//
//    /**
//     * 加锁
//     *
//     * @param key     锁的Key
//     * @param value   锁里面的值
//     * @param seconds 过去时间（秒）
//     * @return String
//     */
//    private String set(final String key, final String value, final long seconds) {
//        Assert.isTrue(!StringUtils.isEmpty(key), "key不能为空");
//
//        Boolean result = this.redisTemplate.execute(connection -> {
//            return connection.stringCommands().set(key.getBytes(), value.getBytes(),
//                    Expiration.from(seconds, TimeUnit.SECONDS), SetOption.SET_IF_ABSENT);
//        }, true);
//
//        return result + "";
//
////        return (String) this.redisTemplate.execute(new RedisCallback<String>() {
////            @Override
////            public String doInRedis(RedisConnection connection) throws DataAccessException {
////                String result = null;
////                // io.lettuce.core.RedisAsyncCommandsImpl,
////                // io.lettuce.core.StatefulRedisConnectionImpl
////                Object conn = connection.getNativeConnection();
////
////                if (conn instanceof JedisCommands)
////                    result = ((JedisCommands) conn).set(key, value, "NX", "EX", seconds);
////
////                if (!StringUtils.isEmpty(lockKeyLog) && !StringUtils.isEmpty(result))
////                    log.info("获取锁{}的时间：{}", lockKeyLog, System.currentTimeMillis());
////
////                return result;
////            }
////        });
//    }
//
//    /** 线程等待时间 */
//    private void sleep(long millis, int nanos) {
//        try {
//            Thread.sleep(millis, this.random.nextInt(nanos));
//        } catch (InterruptedException e) {
//            if (log.isDebugEnabled())
//                log.debug(">>> RedisLock.sleep() 获取分布式锁休眠被中断：{}", e.toString());
//        }
//    }
//
//    public String getLockKeyLog() {
//        return lockKeyLog;
//    }
//
//    public void setLockKeyLog(String lockKeyLog) {
//        this.lockKeyLog = lockKeyLog;
//    }
//
//    public long getExpireTime() {
//        return expireTime;
//    }
//
//    public void setExpireTime(int expireTime) {
//        this.expireTime = expireTime;
//    }
//
//    public long getTimeOut() {
//        return timeOut;
//    }
//
//    public void setTimeOut(long timeOut) {
//        this.timeOut = timeOut;
//    }
//
//}