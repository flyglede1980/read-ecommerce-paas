package net.yitun.ioften.order.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单编号生成
 * @since 1.0.0
 * @see SimpleDateFormat
 * @see Date
 * @author Flyglede
 * @date 2019-01-11
 */
public class OrderNoCreator {
    private static int addPart = 1;
    private static String result = "";
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static String lastDate = "";
    /**
     * 生成并获取订单号
     * @param length--订单号长度
     * @return 返回指定长度的订单号(17位时间戳+n位递增数)
     */
    public synchronized static String getOrderNo(int length) {
        //获取时间部分字符串
        Date now = new Date();
        String nowStr = sdf.format(now);
        //获取数字后缀值部分
        if (OrderNoCreator.lastDate.equals(nowStr)) {
            addPart += 1;
        } else {
            addPart = 1;
            lastDate = nowStr;
        }

        if (length > nowStr.length()) {
            length -= nowStr.length();
            for (int i = 0; i < length - ((addPart + "").length()); i++) {
                nowStr += "0";
            }
            nowStr += addPart;
            result = nowStr;
        } else {
            result = nowStr;
        }
        return result;
    }
}