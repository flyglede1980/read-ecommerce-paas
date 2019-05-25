package net.yitun.basic.cache.support.locker;

import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.connection.RedisStringCommands.SetOption;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

import lombok.extern.slf4j.Slf4j;

//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisCommands;

/**
 * <pre>
 * <b>Redis 分布式锁.</b>
 * <b>Description:</b>
 * Redis分布式锁使用 SET key value NX EX expire 实现, 
 * 方案在 Redis 官方 SET 命令页有详细介绍 -> http://doc.redisfans.com/string/set.html
 * 
 * EX second ：设置键的过期时间为 second 秒。 SET key value EX second 效果等同于 SETEX key second value 。
 * PX millisecond ：设置键的过期时间为 millisecond 毫秒。 SET key value PX millisecond 效果等同于 PSETEX key millisecond value 。
 * NX ：只在键不存在时，才对键进行设置操作。 SET key value NX 效果等同于 SETNX key value 。
 * XX ：只在键已经存在时，才对键进行设置操作。
 * 
 * EX: 以秒为单位设置 key 的过期时间，等效于EXPIRE key seconds
 * NX: 当 key 不存在时, 将key的值设为value, 当且key 不存在，等效于 SETNX
 * 
 * https://my.oschina.net/dengfuwei/blog/1600681
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2019年1月10日 下午7:02:17
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2019年1月10日 下午7:02:17 LH
 *         new file.
 * </pre>
 */
@Slf4j
public class RedisLock {

    /** 当前锁的key */
    private volatile String key;
    /** 当前锁的值 */
    private volatile String keyValue;
    /** 当前锁的锁持续时长 */
    private volatile long expire;

    /** 是否已持有锁标识 */
    private volatile boolean locked = false;

    // 分布式全局加锁器
    private String _key = "_lock"; // 锁的key, 默认: _lock
    private String _keyValue = "_lock."; // 锁的值, 默认: _keyValue
    private Long _expire = 3000L; // 锁持续时长 (ms), 默认: 3000ms
    private Long _tryTimeOut = 1000L; // 请求锁的超时时长(ms), 默认: 1000ms

    private RedisTemplate<String, Object> locker;
    private static final byte[] UNLOCK_LUA; // 解锁LUA脚本
    private final Random random = new Random();
    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ").append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ").append("    return 0 ").append("end ");
        UNLOCK_LUA = sb.toString().getBytes(Charset.forName("UTF-8"));
    }

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());
    }

    public RedisLock(RedisTemplate<String, Object> locker) {
        this.locker = locker;
    }

    public RedisLock(Long expire, Long tryTimeOut, RedisTemplate<String, Object> locker) {
        this.locker = locker;
        this._expire = expire;
        this._tryTimeOut = tryTimeOut;
    }

    public RedisLock(String key, String keyValue, Long expire, Long tryTimeOut, RedisTemplate<String, Object> locker) {
        super();
        this._key = key;
        this._keyValue = keyValue;
        this._expire = expire;
        this._tryTimeOut = tryTimeOut;
        this.locker = locker;
    }

    /**
     * 尝试获取锁, 立即返回
     *
     * @return 是否获得锁成功
     */
    public boolean lock() {
        return this.lock(this._expire);
    }

    public boolean lock(Long expire) {
        return this.locked = this.lock(null, null, expire);
    }

    public boolean lock(String key, String keyValue, Long expire) {
        this.key = (null == key) ? this._key : this._key + ":" + key;
        this.keyValue = (null != keyValue) ? keyValue : this._keyValue + this.unkey();
        this.expire = (null != expire) ? expire : this._expire;

        byte[] keys = this.key.getBytes(), values = this.keyValue.getBytes();
        return this.lock(keys, values, this.expire);
    }

    /**
     * 尝试获取锁, 超时返回
     *
     * @return 是否尝试获得锁成功
     */
    public boolean lockTry() {
        return this.lockTry(this._tryTimeOut);
    }

    public boolean lockTry(Long tryTimeOut) {
        return this.locked = this.lockTry(null, null, null, tryTimeOut);
    }

    public boolean lockTry(String key, String keyValue, Long expire, Long tryTimeOut) {
        this.key = (null == key) ? this._key : this._key + ":" + key;
        this.keyValue = (null != keyValue) ? keyValue : this._keyValue + this.unkey();
        this.expire = (null != expire) ? expire : this._expire;
        tryTimeOut = (null != tryTimeOut) ? tryTimeOut : this._tryTimeOut;

        long nowTime = System.nanoTime();
        byte[] keys = this.key.getBytes(), values = this.keyValue.getBytes();
        do {
            if (this.lock(keys, values, this.expire))
                return true; // 上锁成功结束继续尝试获取锁

            sleep(10, 100000); // 尝试加锁失败后需要随机等待一段时间再继续尝试
        } while (0 == tryTimeOut || (System.nanoTime() - nowTime) < tryTimeOut);

        return false;
    }

    /**
     * 阻塞方式获取锁
     *
     * @return 是否最总获得锁成功
     */
    public boolean lockBlock() {
        return this.lockTry(0L);
    }

    public boolean lockBlock(String key, String keyValue, Long expire) {
        return this.lockTry(key, keyValue, expire, 0L);
    }

    /**
     * 加锁
     *
     * @param key     锁的Key
     * @param value   锁里面的值
     * @param seconds 过去时间（秒）
     * @return boolean 加锁是否成功
     */
    private boolean lock(final byte[] key, final byte[] value, final long expire) {
        boolean result = this.locker.execute(conn -> {
            return conn.stringCommands().set(key, value, Expiration.from(expire, TimeUnit.MILLISECONDS),
                    SetOption.SET_IF_ABSENT);
        }, true);
        return result;

//        return this.locker.execute(conn -> {
//            String res = null;
//            Object conn = conn.getNativeConnection();
//            if (conn instanceof JedisCommands)
//                res = ((JedisCommands) conn).set(key, value, "NX", "EX", expire);
//            return "OK".equalsIgnoreCase(res);
//        }, true);
    }

    /**
     * <pre>
     * 解锁
     * 可以通过以下修改，让这个锁实现更健壮：
     * 不使用固定的字符串作为键的值，而是设置一个不可猜测（non-guessable）的长随机字符串，作为口令串（token）。 
     * 不使用 DEL命令来释放锁，而是发送一个 Lua 脚本，这个脚本只在客户端传入的值和键的口令串相匹配时，才对键进行删除。
     * 这两个改动可以防止持有过期锁的客户端误删现有锁的情况出现。
     * </pre>
     * 
     * @return boolean 解锁是否成功
     */
    public boolean unlock() {
        if (!this.locked)
            return true;

        this.locked = false; // 标记已解锁处理
        return this.unlock(null, this.keyValue);
    }

    public boolean unlock(final String key, final String value) {
        if (null == value)
            return false;

        String tkey = (null == key) ? this._key : this._key + ":" + key;
        return this.locker.execute(conn -> {
            return conn.scriptingCommands().eval(UNLOCK_LUA, ReturnType.BOOLEAN, 1,
                    new byte[][] { tkey.getBytes(), value.getBytes() });
        }, true);

//        // 使用Lua脚本删除Redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
//        // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的conn来执行脚本
//        // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
//        return (Boolean) this.locker.execute(conn -> {
//            Long result = 0L;
//            Object conn = conn.getNativeConnection();
////            if (conn instanceof JedisCluster) // 集群模式
////                result = (Long) ((JedisCluster) conn).eval(UNLOCK_LUA, Arrays.asList(key), Arrays.asList(value));
////
////            if (conn instanceof Jedis) // 单机模式
////                result = (Long) ((Jedis) conn).eval(UNLOCK_LUA, Arrays.asList(key), Arrays.asList(value));
//
//            return (0 == result); // 判断逻辑需调试
//        }, true);
    }

    /** 锁唯一标签 */
    private String unkey() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /** 线程等待时间 */
    private void sleep(long millis, int nanos) {
        try {
            Thread.sleep(millis, this.random.nextInt(nanos));
        } catch (InterruptedException e) {
            if (log.isDebugEnabled())
                log.debug(">>> {}.sleep() redis distributed lock thread sleep was breaked: {}", this.getClass().getName(),
                        e.toString());
        }
    }

    public String key() {
        return this.key;
    }

    public long expire() {
        return this.expire;
    }

    public String keyValue() {
        return this.keyValue;
    }

    public boolean locked() {
        return this.locked;
    }

    public Map<String, Object> info() {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("key", this.key());
        info.put("expire", this.expire());
        info.put("keyValue", this.keyValue());
        info.put("locked", this.locked());
        return info;
    }
}
