package net.yitun.basic.mybatis.conf.aop;

import javax.annotation.PostConstruct;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.model.Page;

/**
 * <pre>
 * <b>分页增强</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2018-07-07 16:12:11.383
 * <b>Copyright:</b> Copyright ©2017 tb.cn. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018-07-07 16:12:11.383 LH
 *         new file.
 * </pre>
 */
@Slf4j
@Aspect
@Component
public class PageAspect {

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());
    }

//    // 拦截规则
//    @Pointcut("execution(* net.yitun..**.action.*Controller.*(..))")
//    @Pointcut("execution(public * net.yitun..*.*ServiceImpl.query(..))")
//    public void point() {
//
//    }

    /**
     * 拦截绑定分页参数
     * 
     * @param point
     * @throws Throwable
     */
    @Before("execution(public * net.yitun..*.*ServiceImpl.query(..))")
    public void doBefore(JoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        if (null != args && 0 != args.length && null != args[0] && args[0] instanceof Page)
            PageHelper.startPage(args[0]);
    }

}