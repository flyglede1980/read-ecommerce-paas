package net.yitun.support;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.security.support.SecurityExceptionHandle;
import net.yitun.basic.website.support.WebsiteExceptionHandle;

/**
 * <pre>
 * <b>异常处理器.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月12日 上午11:28:19 LH
 *         new file.
 * </pre>
 */
@Slf4j
@RestControllerAdvice // @ResponseBody + @ControllerAdvice
public class ExceptionHandle implements SecurityExceptionHandle, WebsiteExceptionHandle {

    @PostConstruct
    protected void init() {
        if (log.isDebugEnabled())
            log.debug("{} init ...", this.getClass().getName());
    }

}