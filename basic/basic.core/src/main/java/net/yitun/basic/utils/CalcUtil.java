package net.yitun.basic.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * <pre>
 * <b>计算 工具.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2017-09-20 16:48:53.202 LH
 *         new file.
 * </pre>
 */
public abstract class CalcUtil {

    /** 精度, 小数点后移8位 */
    public static final int SCALE = 100000000;

    /**
     * 受保护的构造方法, 防止外部构建对象实例.
     */
    protected CalcUtil() {
        super();
    }

    /**
     * 加法计算(2位小数，四舍五入), total + augend
     * 
     * @param total
     * @param augend
     * @return double
     */
    public static double add(double total, double augend) {
        // 2为小数，四舍五入
        return add(total, augend, RoundingMode.HALF_UP);
    }

    /**
     * 加法计算(2位小数，指定取整模式), total + augend
     * 
     * @param total
     * @param augend
     * @param mode
     * @return double
     */
    public static double add(double total, double augend, RoundingMode mode) {
        if (0D == augend)
            return 0.00D;

        BigDecimal amount = new BigDecimal(total) //
                .add(new BigDecimal(augend)) //
                .setScale(2, mode); // 2为小数，四舍五入

        return amount.doubleValue();
    }

    /**
     * 减法计算(2位小数，四舍五入), total - subtrahend
     * 
     * @param total
     * @param subtrahend
     * @return double
     */
    public static double subtract(double total, double subtrahend) {
        // 2为小数，四舍五入
        return subtract(total, subtrahend, RoundingMode.HALF_UP);
    }

    /**
     * 减法计算(2位小数，指定取整模式), total - subtrahend
     * 
     * @param total
     * @param subtrahend
     * @param mode
     * @return double
     */
    public static double subtract(double total, double subtrahend, RoundingMode mode) {
        if (0D == subtrahend)
            return 0.00D;

        BigDecimal amount = new BigDecimal(total) //
                .subtract(new BigDecimal(subtrahend)) //
                .setScale(2, mode); // 2为小数，四舍五入

        return amount.doubleValue();
    }

    /**
     * 乘法计算(2位小数，四舍五入), total * multiplicand
     * 
     * @param total
     * @param multiplicand
     * @return double
     */
    public static double multiply(double total, double multiplicand) {
        // 2为小数，四舍五入
        return multiply(total, multiplicand, RoundingMode.HALF_UP);
    }

    /**
     * 乘法计算(2位小数，指定取整模式), total * multiplicand
     * 
     * @param total
     * @param multiplicand
     * @param mode
     * @return double
     */
    public static double multiply(double total, double multiplicand, RoundingMode mode) {
        if (0D == total || 0D == multiplicand)
            return 0.00D;

        BigDecimal amount = new BigDecimal(total) //
                .multiply(new BigDecimal(multiplicand)) //
                .setScale(2, mode); // 2为小数，四舍五入

        return amount.doubleValue();
    }

    /**
     * 除法计算(2位小数，四舍五入), total/divisor
     * 
     * @param total
     * @param divisor
     * @return double
     */
    public static double divide(double total, double divisor) {
        // 2为小数，四舍五入
        return divide(total, divisor, RoundingMode.HALF_UP);
    }

    /**
     * 除法计算(2位小数，指定取整模式), total/divisor
     * 
     * @param total
     * @param divisor
     * @param mode
     * @return double
     */
    public static double divide(double total, double divisor, RoundingMode mode) {
        if (0D == divisor)
            return 0.00D;

        BigDecimal amount = new BigDecimal(total) //
                .divide(new BigDecimal(divisor)) //
                .setScale(2, mode); // 2为小数，四舍五入

        return amount.doubleValue();
    }

}
