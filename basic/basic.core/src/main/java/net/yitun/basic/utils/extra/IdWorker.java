package net.yitun.basic.utils.extra;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <b>IdWorker</b>
 * <b>Description:</b>
 *  Twitter_Snowflake
 *  SnowFlake生产的ID二进制结构表示如下(每部分用-分开):
 *      0 - 00000000 00000000 00000000 00000000 00000000 0 - 00000 - 00000 - 00000000 0000 
 *      1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0，所以未使用
 *          41位时间截(毫秒级)(可以使用69年，年T = (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69)，注意，41位时间截不是存储当前时间的时间截，而是时间截的差值（当前时间截 - 开始时间截得到的值），
 *          这里的开始时间截，一般是我们的id生成器开始使用的时间，由我们程序来指定的（如下下面程序IdWorker类的twepoch属性）。
 *      10位的数据机器位，可以部署在1024个节点，包括5位datacenterId（最大支持2^5＝32个，二进制表示从00000-11111，也即是十进制0-31）和5位workerId（最大支持2^5＝32个，原理同datacenterId）
 *      12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号
 *  所有位数加起来共64位，恰好是一个Long型（转换为字符串长度为18）。
 * 
 *  单台机器实例，通过时间戳保证前41位是唯一的，分布式系统多台机器实例下，通过对每个机器实例分配不同的datacenterId和workerId避免中间的10位碰撞。
 *  最后12位每毫秒从0递增生产ID，再提一次：每毫秒最多生成4096个ID，每秒可达4096000个。理论上，只要CPU计算能力足够，单机每秒可生产400多万个，实测300w+，效率之高由此可见。
 * 
 *  优点：
 *      性能高，每秒可生成几百万ID。
 *      时间戳在高位，自增序列在低位，整个ID是趋势递增的，按照时间有序。
 *      可以根据自身业务需求灵活调整bit位划分，满足不同需求。
 *      整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)。
 *      不依赖数据库等第三方系统，以服务的方式部署，稳定性更高，生成ID的性能也是非常高的。
 * 
 *  缺点：
 *      依赖机器时钟，如果机器时钟回拨，会导致重复ID生成。
 *      在单机上是递增的，但是由于涉及到分布式环境，每台机器上的时钟不可能完全同步，有时候会出现不是全局递增的情况
 * 
 * 改进参考： https://blog.csdn.net/u011381576/article/details/79367233
 * 
 * <b>Author:</b> LH
 * <b>Date:</b> 2018-07-24 10:28:20.406
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018-07-24 10:28:20.406 LH
 *         new file.
 * </pre>
 */
@Slf4j
public class IdWorker {

    // ==============================Fields===========================================
    /** 工作机器ID(0~31) */
    private long workerId;

    /** 数据中心ID(0~31) */
    private long datacenterId;

    /** 毫秒内序列(0~4095) */
    private long sequence = 0L;

    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;

    /** 开始时间截 (2011-01-01) */
    private static final long TWEPOCH = 1293811200000L;

    /** 机器id所占的位数 */
    private static final long WORKER_ID_BITS = 5L;

    /** 数据标识id所占的位数 */
    private static final long DATACENTER_ID_BITS = 5L; // 可以设置为3L, 则只有8个数据中心，但每个中心拥有128个Server

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private static final long MAX_WORKER_ID = -1L ^ (-1L << WORKER_ID_BITS);

    /** 支持的最大数据标识id，结果是31 */
    private static final long MAX_DATACENTER_ID = -1L ^ (-1L << DATACENTER_ID_BITS);

    /** 序列在id中占的位数 */
    private static final long SEQUENCE_BITS = 12L;

    /** 机器ID向左移12位 */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

    /** 数据标识id向左移17位(12+5) */
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

    /** 时间截向左移22位(5+5+12) */
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private static final long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BITS);

    /** 最小ID值 */
    public static final long MIN = 1016723701000000000L;

    // ==============================Constructors=====================================

    /**
     * 构造函数
     * 
     * @param workerId     工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     */
    public IdWorker(int workerId, int datacenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            IllegalArgumentException e = new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
            log.error(e.getMessage(), e);
            throw e;
        }

        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            IllegalArgumentException e = new IllegalArgumentException(
                    String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATACENTER_ID));
            log.error(e.getMessage(), e);
            throw e;
        }

        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    // ==============================Methods==========================================
    /**
     * 获得下一个ID (该方法是线程安全的)
     * 
     * @return long 分布式唯一ID
     */
    public synchronized long next() {
        long timestamp = System.currentTimeMillis();

        // 发生回拨，当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            // //如果偏差比较小，则等待
            long delay = lastTimestamp - timestamp;
            if (delay < 1000)
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            timestamp = System.currentTimeMillis();
            // 如果还没好，报警
            if (timestamp < lastTimestamp) {
                RuntimeException e = new RuntimeException(String.format(
                        "Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
                log.error(e.getMessage(), e);
                throw e;
            }
        }

        // 时间戳改变，毫秒内序列重置
        if (timestamp != lastTimestamp)
            sequence = 0L; // 如果和上次生成时间不同,重置sequence，就是下一毫秒开始，sequence计数重新从0开始累加

        // 如果是同一时间生成的，则进行毫秒内序列
        else {
            // sequence自增，因为sequence只有12bit，所以和sequenceMask相与一下，去掉高位
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // 判断是否溢出,也就是每毫秒内超过4095，当为4096时，与sequenceMask相与，sequence就等于0
            if (sequence == 0)
                // 阻塞自旋等待到下一毫秒, 获得新的时间戳
                timestamp = nextMillis(lastTimestamp);
        }

        // 上次生成ID的时间截
        lastTimestamp = timestamp;

        // 移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT) //
                | (datacenterId << DATACENTER_ID_SHIFT) | (workerId << WORKER_ID_SHIFT) | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * 
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long nextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp)
            timestamp = System.currentTimeMillis();
        return timestamp;
    }

    protected static long getWorkerId() {
        long id = 0L;

        // InetAddress ip = InetAddress.getLocalHost();
        // NetworkInterface network = NetworkInterface.getByInetAddress(ip);

        byte[] mac = null;
        try {
            NetworkInterface network = null;
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface nint = en.nextElement();
                if (!nint.isLoopback() && nint.getHardwareAddress() != null) {
                    network = nint;
                    break;
                }
            }
            mac = network.getHardwareAddress();
        } catch (SocketException | NullPointerException e) {
            e.printStackTrace();
        }

        // byte rndByte = (byte) (new Random().nextInt() & 0x000000FF);
        // // take the last byte of the MAC address and a random byte as worker ID
        // return ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) rndByte) << 8))) >> 6;

        id = ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
        id = id % (MAX_WORKER_ID + 1);
        return id;
    }

    /**
     * 获取 maxWorkerId
     */
    protected static long getWorkerId(long datacenterId) {
        StringBuffer mpid = new StringBuffer();
        mpid.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty())
            // GET jvmPid
            mpid.append(name.split("@")[0]);

        // MAC + PID 的 hashcode 获取16个低位
        return (mpid.toString().hashCode() & 0xffff) % (MAX_WORKER_ID + 1);
    }

    /**
     * 数据标识id部分
     */
    protected static long getDatacenterId() {
        long id = 0L;

        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (null != network) {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                id = id % (MAX_DATACENTER_ID + 1);
            }

        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }

        return id;
    }

}