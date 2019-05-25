package net.yitun.basic.utils;

import java.io.Flushable;
import java.io.IOException;

/**
 * <pre>
 * <b>IO 工具.</b>
 * <b>Description:</b> 主要提供在java IO中close是一个很常见的操作, 用完一个
 *    Stream或者Reader或者Writer后都需要将它关闭, 而且每次关闭时还需先判断
 *    它是否为null, 从而保证不抛出NullPointerException, 还需check Exception.
 *    
 *    而closeQuietly则将检查是否为null和忽略Exception都在一个方法里完成, 
 *    从而省略了检查null和catch IOException, 从而缩短代码长度.
 *    
 *    另外toString方法是将一个InputStream转成指定encoding的String.
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-15 10:34:58.921
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2017-09-15 10:34:58.921 LH
 *         new file.
 * </pre>
 */
public abstract class IoUtil {

    /**
     * 受保护的构造方法, 防止外部构建对象实例.
     */
    protected IoUtil() {
        super();
    }

    /**
     * 静默关闭实现 AutoCloseable 接口的对象.<br/>
     * 具体有: Nio Channel、 IO InputStream、 IO OutputStream、 IO Reader、 IO Writer
     * 
     * @param closes 实现 AutoCloseable 接口的对象.
     */
    public static void close(AutoCloseable... closes) {
        close(false, closes);
    }

    /**
     * 静默关闭实现 AutoCloseable 接口的对象.<br/>
     * 具体有: Nio Channel、 IO InputStream、 IO OutputStream、 IO Reader、 IO Writer
     * 
     * @param flush 强制刷新Buffer, 通过输出流输出, true:自动; false:忽略.
     * @param close 实现 AutoCloseable 接口的对象.
     */
    public static void close(boolean flush, AutoCloseable close) {
        if (null == close)
            return;

        if (flush && close instanceof Flushable) {
            try {
                ((Flushable) close).flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            close.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 静默关闭实现 AutoCloseable 接口的对象.<br/>
     * 具体有: Nio Channel、 IO InputStream、 IO OutputStream、 IO Reader、 IO Writer
     * 
     * @param flush  强制刷新Buffer, 通过输出流输出, true:自动; false:忽略.
     * @param closes 实现 AutoCloseable 接口的对象.
     */
    public static void close(boolean flush, AutoCloseable... closes) {
        if (null == closes)
            return;

        for (AutoCloseable close : closes)
            close(flush, close);
    }

    /**
     * 进程销毁
     * 
     * @param processes
     */
    public static void destroy(Process... processes) {
        if (null == processes)
            return;

        for (Process process : processes) {
            try {
                process.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
