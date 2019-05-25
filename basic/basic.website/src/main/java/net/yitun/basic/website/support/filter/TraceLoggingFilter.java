package net.yitun.basic.website.support.filter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.utils.IdUtil;
import net.yitun.basic.utils.WebUtil;

/**
 * <pre>
 * <b>用于追踪记录.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月12日 上午11:07:12 LH
 *         new file.
 * </pre>
 */
@Slf4j
@Component("basic.website.TraceFilter")
public class TraceLoggingFilter extends CommonsRequestLoggingFilter {

    protected static final String TRACE_KEY = "BASIC.WEBSITE.TRACE";

    protected static final String STIME_KEY = "BASIC.WEBSITE.TRACE.STIME";

    @PostConstruct
    protected void init() {
        if (log.isDebugEnabled())
            log.debug("{} init ...", this.getClass().getName());
    }

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return log.isInfoEnabled();
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        long trace = IdUtil.id(), stime = System.currentTimeMillis();
        String ip = WebUtil.getIp(request), act = request.getMethod(), path = request.getRequestURI();
        log.info(">>> trace:{} ip:{} {} {}", trace, ip, act, path);
        request.setAttribute(TRACE_KEY, trace);
        request.setAttribute(STIME_KEY, stime);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        Object trace = request.getAttribute(TRACE_KEY), stime = request.getAttribute(STIME_KEY);
        // 记录服务提供端的响应时间和耗时情况，注意异常未纳入统计
        long utime = System.currentTimeMillis() - Long.valueOf(stime.toString());
        // double utime = CalcUtil.divide((System.currentTimeMillis() - Long.valueOf(stime.toString())), 1000);
        log.info("<<< trace:{} utime:{}ms", trace, utime);
    }

}
