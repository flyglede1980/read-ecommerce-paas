package net.yitun.basic.website.support.convert;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <b>日期转换器.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年9月15日 下午12:52:19 LH
 *         new file.
 * </pre>
 */
@Slf4j
@Component("basic.website.DateConverter")
public class DateConverter implements Converter<String, Date> {

    protected static final DateFormat[] ARRAY = new DateFormat[5];
    static {
        ARRAY[0] = new SimpleDateFormat("yyyy-MM");
        ARRAY[1] = new SimpleDateFormat("yyyy-MM-dd");
        ARRAY[2] = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        ARRAY[3] = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        ARRAY[4] = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    }

    @PostConstruct
    protected void init() {
        if (log.isDebugEnabled())
            log.debug("{} init ...", this.getClass().getName());
    }

    @Override
    public Date convert(String source) {
        String value = source.trim();
        if ("".equals(value))
            return null;

        int len = value.length();
        if (7 == len && source.matches("^\\d{4}-\\d{1,2}$"))
            return parse(0, source);

        if (10 == len && source.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$"))
            return parse(1, source);

        if (16 == len && source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$"))
            return parse(2, source);

        if (19 == len && source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$"))
            return parse(3, source);

        if (23 == len && source.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{3}$"))
            return parse(4, source);

        if (log.isWarnEnabled())
            log.warn("Invalid date value '" + source + "'");

        throw new IllegalArgumentException("Invalid date value '" + source + "'");
    }

    private Date parse(int index, String source) {
        try {
            return ARRAY[index].parse(source);
        } catch (Exception e) {
        }
        return null;
    }

}