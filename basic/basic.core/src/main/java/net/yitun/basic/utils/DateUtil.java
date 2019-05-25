/**
 * 
 */
package net.yitun.basic.utils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * <b>日期 辅助工具.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-13 17:26:17.168
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2017-09-13 17:26:17.168 LH
 *         new file.
 * </pre>
 */
public abstract class DateUtil {

    /** 模板: HH:mm:ss */
    public static final String TIME = "HH:mm:ss";

    /** 模板: HH:mm */
    public static final String TIME_SHORT = "HH:mm";

    /** 模板: HH:mm:ss.SSS */
    public static final String TIME_STAMP = "HH:mm:ss.SSS";

    /** 模板: yyyy-MM-dd */
    public static final String DATE = "yyyy-MM-dd";

    /** 模板: MM-dd */
    public static final String DATE_SHORT = "MM-dd";

    /** 模板: yyyy-MM-dd HH:mm:ss */
    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /** 模板: yyyyMMddHHmmss */
    public static final String DATE_TIME_SHORT = "yyyyMMddHHmmss";

    /** 模板: yyyy-MM-dd HH:mm:ss.SSS */
    public static final String DATE_TIME_STAMP = "yyyy-MM-dd HH:mm:ss.SSS";

    /** 模板: yyyyMMddHHmmssSSS */
    public static final String DATE_TIME_STAMP_SHORT = "yyyyMMddHHmmssSSS";

    /** 最大日期: 9999-12-31 */
    public static final String MAX_DATE = "9999-12-31";

    /** 最大时间: 9999-12-31 23:59:59.999 */
    public static final String MAX_DATE_TIME = "9999-12-31 23:59:59.999";

    /** 星期字典, 默认1代表星期一, 0代表星期天 */
    public static final int[] WEEKS = { 0, 1, 2, 3, 4, 5, 6 };
    /** 英文星期字典, 默认1代表星期一, 0结束即代表星期天 */
    public static final String[] WEEKS_DIGITS = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
            "Saturday" };
    /** 英文简写星期字典, 默认1代表星期一, 0结束即代表星期天 */
    public static final String[] WEEKS_SUB_DIGITS = { "Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat" };
    /** 中文星期字典, 默认1代表星期一, 0结束即代表星期天 */
    public static final String[] WEEKS_ZH_CN_DIGITS = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
    /** 中文简写星期字典, 默认1代表周一, 0结束即代表日 */
    public static final String[] WEEKS_ZH_CN_SUB_DIGITS = { "日", "一", "二", "三", "四", "五", "六" };

    /** 月份字典 */
    public static final int[] MONTHS = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
    /** 英文月份字典 */
    public static final String[] MONTHS_DIGITS = { "January", "February", "March", "April", "May", "June", "July", "August",
            "September", "October", "November", "December" };
    /** 英文简写月份字典 */
    public static final String[] MONTHS_SUB_DIGITS = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct",
            "Nov", "Dec" };
    /** 中文月份字典 */
    public static final String[] MONTHS_ZH_CN_DIGITS = { "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月",
            "十二月" };
    /** 中文简写月份字典 */
    public static final String[] MONTHS_ZH_CN_SUB_DIGITS = { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二" };

    /* 日志记录器. */
    protected static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    static {
        // 设置全局的默认时区为 上海、北京时间、东八区
        final String zone = "Asia/Shanghai";
        TimeZone.setDefault(TimeZone.getTimeZone(zone));
        if (logger.isInfoEnabled())
            logger.info("{} Zone: {}", DateUtil.class.getName(), TimeZone.getDefault().getID());
    }

    /**
     * 受保护的构造方法, 防止外部构建对象实例.
     */
    protected DateUtil() {
        super();
    }

    /**
     * 当前毫秒时间.
     * 
     * @return long 毫秒时间
     */
    public static long millis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取 Unix 时间
     * 
     * @return int
     * @throws Exception
     */
    public static int unixTime() {
        long epoch = -1L;
        try {
            epoch = new SimpleDateFormat(DATE_TIME_STAMP) //
                    .parse("1970-01-01 00:00:00.000").getTime() / 1000;
        } catch (ParseException e) {
            return -1;
        }

        epoch = System.currentTimeMillis() / 1000 - epoch;
        return Integer.parseInt(Long.toString(epoch));
    }

    /**
     * 当前时间.
     * 
     * @return Date 时间实例
     */
    public static Date date() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 根据毫秒时间转成时间.
     * 
     * @param millis 时间毫秒数
     * @return Date 时间实例
     */
    public static Date date(final long millis) {
        return new Date(millis);
    }

    /**
     * <pre>
     * 将字符串使用对应的时间格式转换为时间实例,
     * 例如字符串为 2000-01-01, 时间格式为 yyyy-MM-dd, 则返回对应的时间实例,
     * 如果 null==str或者 0==str(), 则返回 null.
     * </pre>
     * 
     * @param str 字符串
     * @return Date 时间实例
     */
    public static Date date(final String str) {
        return parse(str, new SimpleDateFormat(DATE));
    }

    /**
     * <pre>
     * 将字符串使用对应的时间格式转换为时间实例,
     * 例如字符串为 2000-01-01 00:00:00, 时间格式为 yyyy-MM-dd HH:mm:ss , 则返回对应的时间实例,
     * 如果 null==str或者 0==str(), 则返回 null.
     * </pre>
     * 
     * @param str 字符串
     * @return Date 时间实例
     */
    public static Date datetime(final String str) {
        return parse(str, new SimpleDateFormat(DATE_TIME));
    }

    /**
     * <pre>
     * 将字符串使用对应的时间格式转换为时间实例,
     * 例如字符串为 2000-01-01,时间格式为 yyyy-MM-dd,则返回对应的时间实例,
     * 如果 null==str或者 0==str() 或者 null==format 或者 0==format.length(),则返回 null.
     * </pre>
     * 
     * @param str 字符串
     * @return Date 时间实例
     */
    public static Date parse(final String str) {
        return parse(str, DATE);
    }

    /**
     * <pre>
     * 将字符串使用对应的时间格式转换为时间实例,
     * 例如字符串为 2000-01-01 01:01:01,时间格式为 yyyy-MM-dd HH:mm:ss.SSS,则返回对应的时间实例,
     * 如果 null==str或者 0==str() 或者 null==format 或者 0==format.length(),则返回 null.
     * </pre>
     * 
     * @param str     字符串
     * @param pattern 时间格式
     * @return Date 时间实例
     */
    public static Date parse(final String str, final String pattern) {
        if (null == pattern || 0 == pattern.length() || null == str || 0 == str.length())
            return null;

        DateFormat df = new SimpleDateFormat(pattern);
        df.setLenient(false);
        try {
            return df.parse(str);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * <pre>
     * 将字符串使用对应的时间格式转换为时间实例,
     * 例如字符串为 2000-01-01 01:01:01,时间格式为 yyyy-MM-dd HH:mm:ss.SSS,则返回对应的时间实例,
     * 如果 null==str或者 0==str() 或者 null==format 或者 0==format.length(),则返回 null.
     * </pre>
     * 
     * @param str     字符串
     * @param pattern 时间格式
     * @param locale  时间本地化
     * @return Date 时间实例
     */
    public static Date parse(final String str, final String pattern, Locale locale) {
        if (null == locale || null == pattern || 0 == pattern.length() || null == str || 0 == str.length())
            return null;

        DateFormat df = new SimpleDateFormat(pattern, locale);
        df.setLenient(false);
        try {
            return df.parse(str);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * <pre>
     * 将字符串使用对应的时间格式化模板实例转换为时间实例,
     * 例如字符串为 2000-01-01 01:01:01,时间格式为 yyyy-MM-dd HH:mm:ss.SSS,则返回对应的时间实例,
     * 如果 null==str或者 0==str() 或者 df==null,则返回 null.
     * </pre>
     * 
     * @param str 字符串
     * @param df  时间格式化模板实例
     * @return Date 时间实例
     */
    public static Date parse(final String str, final DateFormat df) {
        if (null == df || null == str || 0 == str.length())
            return null;

        try {
            return df.parse(str);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 按模板格式化时间.
     * 
     * @return String 格式后字符串, yyyy-MM-dd
     */
    public static String format() {
        Date date = new Date(System.currentTimeMillis());
        return format(date);
    }

    /**
     * 按模板格式化时间.
     * 
     * @param millis 毫秒时间
     * @return String 格式后字符串, yyyy-MM-dd
     */
    public static String format(final long millis) {
        return format(new Date(millis));
    }

    /**
     * 按模板格式化时间.
     * 
     * @param date 时间
     * @return String 格式后字符串, yyyy-MM-dd
     */
    public static String format(final Date date) {
        DateFormat format = new SimpleDateFormat(DATE);
        return format(date, format);
    }

    /**
     * 按模板格式化时间.
     * 
     * @param format 格式
     * @return String 格式后字符串, yyyy-MM-dd
     */
    public static String format(final String format) {
        return format(new Date(System.currentTimeMillis()), format);
    }

    /**
     * 按模板格式化时间.
     * 
     * @param date 时间
     * @return String 格式后字符串, yyyy-MM-dd
     */
    public static String format(final Calendar calendar) {
        if (null == calendar)
            return null;

        return format(calendar.getTime());
    }

    /**
     * 按模板格式化当前时间.
     * 
     * @param format 时间格式
     * @return String 格式后字符串
     */
    public static String format(final DateFormat format) {
        return format.format(new Date());
    }

    /**
     * 按模板格式化时间.
     * 
     * @param millis  毫秒时间
     * @param pattern 模板
     * @return String 格式后字符串
     */
    public static String format(final long millis, final String pattern) {
        return format(new Date(millis), pattern);
    }

    /**
     * 按模板格式化时间.
     * 
     * @param millis 毫秒时间
     * @param format 格式化器
     * @return String 格式后字符串
     */
    public static String format(final long millis, final DateFormat format) {
        return format(new Date(millis), format);
    }

    /**
     * 按模板格式化时间.
     * 
     * @param date    时间
     * @param pattern 模板
     * @return String 格式后字符串
     */
    public static String format(final Date date, final String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        return format(date, format);
    }

    /**
     * 按模板格式化时间.
     * 
     * @param date   时间
     * @param format 格式化器
     * @return String 格式后字符串
     */
    public static String format(final Date date, final DateFormat format) {
        if (null == date || null == format)
            return null;

        return format.format(date);
    }

    /**
     * 按模板格式化时间.
     * 
     * @param calendar 时间
     * @param pattern  模板
     * @return String 格式后字符串
     */
    public static String format(final Calendar calendar, final String pattern) {
        if (null == calendar)
            return null;

        Date date = calendar.getTime();
        return format(date, pattern);
    }

    /**
     * 按模板格式化时间.
     * 
     * @param calendar 时间
     * @param format   格式化器
     * @return String 格式后字符串
     */
    public static String format(final Calendar calendar, final DateFormat format) {
        if (null == calendar)
            return null;

        Date date = calendar.getTime();
        return format(date, format);
    }

    /**
     * 当前日期加减天数
     * 
     * @param days
     * @return Date
     */
    public static Date addDay(int days) {
        return addDay(Calendar.getInstance(), days);
    }

    /**
     * 指定时间轴加减天数
     * 
     * @param millis
     * @param days
     * @return Date
     */
    public static Date addDay(final long millis, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return addDay(cal, days);
    }

    /**
     * 指定时间轴加减天数
     * 
     * @param date
     * @param days
     * @return Date
     */
    public static Date addDay(final Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return addDay(cal, days);
    }

    /**
     * 指定日期加减天数
     * 
     * @param date
     * @param days
     * @return Date
     */
    public static Date addDay(Calendar calendar, int days) {
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    /**
     * 截取日期, 其他部分用0补位.
     * 
     * @param date
     * @param field 如： Calendar.SECOND
     * @return long
     */
    public static long truncate(final long millis, final int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        Calendar _calendar = truncate(calendar, field);
        return _calendar.getTimeInMillis();
    }

    /**
     * 截取日期, 其他部分用0补位.
     * 
     * @param date
     * @param field 如： Calendar.SECOND
     * @return Date
     */
    public static Date truncate(final Date date, final int field) {
        if (null == date)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar _calendar = truncate(calendar, field);
        return _calendar.getTime();
    }

    /**
     * 截取日期, 其他部分用0补位.
     * 
     * @param date
     * @param field 如： Calendar.SECOND
     * @return Calendar
     */
    public static Calendar truncate(final Calendar calendar, final int field) {
        if (null == calendar)
            return null;

        if (Calendar.SECOND == field) {
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }

        if (Calendar.MINUTE == field) {
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }

        if (Calendar.HOUR == field || Calendar.HOUR_OF_DAY == field) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }

        return calendar;
    }

    /**
     * 一天的开始时间
     * 
     * @return long 如:2017-01-01 00:00:00:000
     */
    public static long dayOfStartTime() {
        Calendar calendar = Calendar.getInstance();

        Calendar _calendar = makeDayTime(calendar, true);
        return _calendar.getTimeInMillis();
    }

    /**
     * 一天的开始时间
     * 
     * @param millis 时间毫秒数
     * @return long 如:2017-01-01 00:00:00:000
     */
    public static long dayOfStartTime(final long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        Calendar _calendar = makeDayTime(calendar, true);
        return _calendar.getTimeInMillis();
    }

    /**
     * 一天的开始时间
     * 
     * @param date 时间
     * @return Date 如:2017-01-01 00:00:00:000
     */
    public static Date dayOfStartTime(final Date date) {
        if (null == date)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar _calendar = makeDayTime(calendar, true);
        return _calendar.getTime();
    }

    /**
     * 一天的开始时间
     * 
     * @param calendar 日历时间
     * @return Calendar 如:2017-01-01 00:00:00:000
     */
    public static Calendar dayOfStartTime(final Calendar calendar) {
        return makeDayTime(calendar, true);
    }

    /**
     * 一天的结束时间
     * 
     * @return long 如:2017-01-01 23:59:59.999
     */
    public static long dayOfEndTime() {
        Calendar calendar = Calendar.getInstance();

        Calendar _calendar = makeDayTime(calendar, false);
        return _calendar.getTimeInMillis();
    }

    /**
     * 一天的结束时间
     * 
     * @param millis 时间毫秒数
     * @return long 如:2017-01-01 23:59:59.999
     */
    public static long dayOfEndTime(final long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        Calendar _calendar = makeDayTime(calendar, false);
        return _calendar.getTimeInMillis();
    }

    /**
     * 一天的结束时间
     * 
     * @param date 时间
     * @return Date 如:2017-01-01 23:59:59.999
     */
    public static Date dayOfEndTime(final Date date) {
        if (null == date)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar _calendar = makeDayTime(calendar, false);
        return _calendar.getTime();
    }

    /**
     * 一天的结束时间
     * 
     * @param calendar 日历时间
     * @return Calendar 如:2017-01-01 23:59:59.999
     */
    public static Calendar dayOfEndTime(final Calendar calendar) {
        return makeDayTime(calendar, false);
    }

    /**
     * 生成一天的开始或结束时间
     * 
     * @param calendar
     * @param isStart  true:一天的开始时间; false:一天的结束时间
     * @return Calendar 如:2017-01-01 00:00:00:000 或者 2017-01-01 23:59:59.999
     */
    protected static Calendar makeDayTime(final Calendar calendar, boolean isStart) {
        if (null == calendar)
            return null;

        // 一天的开始时间, 00:00:00.000
        if (isStart) {
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
        }
        // 一天的结束时间, 23:59:59.999
        else {
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
        }

        return calendar;
    }

    /**
     * 计算两个日期相差天数(通过毫秒计算)
     * 
     * @param start 起始时间
     * @param end   结束时间
     * @return int 间隔天数
     */
    public static int dayOfInterval(Date start, Date end) {
        return dayOfInterval(start, end, BigDecimal.ROUND_DOWN);
    }

    /**
     * 计算两个日期相差天数(通过毫秒计算)
     * 
     * @param start     起始时间
     * @param end       结束时间
     * @param roundType 取整类型, 例如: BigDecimal.ROUND_DOWN
     * @return int 间隔天数
     */
    public static int dayOfInterval(Date start, Date end, int roundType) {
        // DateTime bdt = new DateTime(start.getTime());
        // DateTime edt = new DateTime(end.getTime());
        // Days days = Days.daysBetween(bdt, edt); return days.getDays();

        long times = end.getTime() - start.getTime();
        BigDecimal _times = new BigDecimal(times);
        BigDecimal divisor = new BigDecimal(86400000L);
        BigDecimal interval = _times.divide(divisor, 0, BigDecimal.ROUND_DOWN); // 86400000L = 1000L * 60 * 60 * 24
        interval.setScale(0);
        return interval.intValue();
    }

    /**
     * 当前日期对应的星期
     * 
     * @return 星期 ( 1 - 7)
     */
    public static int week() {
        Calendar cal = Calendar.getInstance();
        return week(cal);
    }

    /**
     * 指定时间轴对应的星期
     * 
     * @return 星期 ( 1 - 7)， 无效时间轴返回 -1
     */
    public static int week(final long millis) {
        if (1L >= millis)
            return -1;

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        return week(cal);
    }

    /**
     * 指定日期对应的星期
     * 
     * @return 星期 ( 1 - 7)， 无效日期返回 -1
     */
    public static int week(final Date date) {
        if (null == date)
            return -1;

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return week(cal);
    }

    /**
     * 指定日历对应的星期
     * 
     * @return 星期 ( 1 - 7)， 无效日历返回 -1
     */
    public static int week(final Calendar calendar) {
        if (null == calendar)
            return -1;

        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        week = (0 == week) ? 7 : week;
        return week;
    }

    /**
     * 月份的开始时间
     * 
     * @return Date 如:2017-01-01 00:00:00:000
     */
    public static Date monthOfStartTime() {
        Date date = new Date(System.currentTimeMillis());
        return monthOfStartTime(date);
    }

    /**
     * 月份的开始时间
     * 
     * @param date 时间
     * @return Date 如:2017-01-01 00:00:00:000
     */
    public static Date monthOfStartTime(final Date date) {
        if (null == date)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar = monthOfStartTime(calendar);

        return calendar.getTime();
    }

    /**
     * 月份的开始时间
     * 
     * @param calendar 日历时间
     * @return Calendar 如:2017-01-01 00:00:00:000
     */
    public static Calendar monthOfStartTime(final Calendar calendar) {
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar;
    }

    /**
     * 月份的结束时间
     * 
     * @param date 时间
     * @return Date 如:2017-01-31 23:59:59.999
     */
    public static Date monthOfEndTime(final Date date) {
        if (null == date)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar = monthOfEndTime(calendar);

        return calendar.getTime();
    }

    /**
     * 月份的结束时间
     * 
     * @param calendar 日历时间
     * @return Calendar 如:2017-01-31 23:59:59.999
     */
    public static Calendar monthOfEndTime(final Calendar calendar) {
        monthOfStartTime(calendar);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar;
    }

}
