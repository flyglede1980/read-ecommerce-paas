package net.yitun.conf.aop;

import javax.annotation.PostConstruct;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.utils.JsonUtil;

/**
 * <pre>
 * <b>记录服务每个业务执行耗时</b>
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
//@Component
public class LogAspect {

    @PostConstruct
    private LogAspect init() {
        log.info("{} init ...", this.getClass().getName());
        return this;
    }

    @Pointcut("execution(public * net.yitun..*.website.action.*Action.*(..))")
    public void point() {

    }

    @After("point()") // 后置通知：目标方法执行之后执行以下方法体的内容，不管是否发生异常
    public void doAfter(JoinPoint joinPoint) throws Throwable {
        log.info("{}.doAfter, {}.{} invoke after", LogAspect.class.getName(), joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName());
    }

    @Before("point()") // 前置通知：目标方法执行之前执行以下方法体的内容
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        log.info("{}.doBefore, {}.{} invoke before", LogAspect.class.getName(), joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "point()", returning = "result") // 返回通知：目标方法正常执行完毕时执行以下代码
    public void doAfterReturning(JoinPoint joinPoint, Object result) throws Throwable {
        log.info("{}.doAfterReturning, {}.{} invoke complete, result: {}", LogAspect.class.getName(),
                joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), JsonUtil.toJson(result));
    }

    @AfterThrowing(pointcut = "point()", throwing = "exp") // 异常通知：目标方法发生异常的时候执行以下代码
    public void doAfterThrowing(JoinPoint joinPoint, Exception exp) throws Throwable {
        log.info("{}.doAfterThrowing, {}.{} invoke complete, exp: {}", LogAspect.class.getName(),
                joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), exp.getMessage());
    }

    @Around("point()") // 环绕通知：目标方法执行前后分别执行一些代码，发生异常的时候执行另外一些代码
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long time = System.currentTimeMillis();
        try {
            return joinPoint.proceed();

        } finally {
            long utime = System.currentTimeMillis() - time;
            log.info("{}.doAround, {}.{} invoke use time: {} ms.", LogAspect.class.getName(),
                    joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName(), utime);
        }
    }

}