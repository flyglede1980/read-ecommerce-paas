package net.yitun.basic.cache.utils;

/**
 * <pre>
 * <b>原生二进制互转.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年10月10日 下午7:52:34 LH
 *         new file.
 * </pre>
 */
public abstract class ByteUtil {

    /**
     * int 转换 byte
     * 
     * @param i
     * @return byte
     */
    public static byte int2Byte(int i) {
        return (byte) i;
    }

    /**
     * byte 转换 int
     * 
     * @param b
     * @return int
     */
    public static int byte2Int(byte b) {
        // Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
        return b & 0xFF;
    }

    /**
     * int 转换 bytes
     * 
     * @param value
     * @return bytes
     */
    public static byte[] int2Bytes(int value) {
        byte[] targets = new byte[4];
        targets[3] = (byte) (value & 0xff); // 最低位
        targets[2] = (byte) ((value >> 8) & 0xff); // 次低位
        targets[1] = (byte) ((value >> 16) & 0xff); // 次高位
        targets[0] = (byte) (value >>> 24); // 最高位,无符号右移。
        return targets;
    }

    /**
     * bytes 转换 int
     * 
     * @param bytes
     * @return int
     */
    public static int bytes2Int(byte[] bytes) {
        byte[] a = new byte[4];
        int i = a.length - 1, j = bytes.length - 1;
        for (; i >= 0; i--, j--) { // 从b的尾部(即int值的低位)开始copy数据
            if (j >= 0)
                a[i] = bytes[j];
            else
                a[i] = 0; // 如果b.length不足4,则将高位补0
        }
        int v0 = (a[0] & 0xff) << 24; // &0xff将byte值无差异转成int,避免Java自动类型提升后,会保留高位的符号位
        int v1 = (a[1] & 0xff) << 16;
        int v2 = (a[2] & 0xff) << 8;
        int v3 = (a[3] & 0xff);
        return v0 + v1 + v2 + v3;
    }

    /**
     * long 转换 bytes
     * 
     * @param num
     * @return byte[]
     */
    public static byte[] long2Bytes(long num) {
        byte[] buffer = new byte[8];
        for (int ix = 0; ix < 8; ++ix) {
            int offset = 64 - (ix + 1) * 8;
            buffer[ix] = (byte) ((num >> offset) & 0xff);
        }
        return buffer;
    }

    /**
     * bytes 转换 long
     * 
     * @param bytes
     * @return long
     */
    public static long bytes2Long(byte[] bytes) {
        long num = 0;
        for (int ix = 0; ix < 8; ++ix) {
            num <<= 8;
            num |= (bytes[ix] & 0xff);
        }
        return num;
    }

}