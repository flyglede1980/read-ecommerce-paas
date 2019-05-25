package net.yitun.basic.security.support;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import net.yitun.basic.Code;
import net.yitun.basic.model.Result;

/**
 * <pre>
 * <b>安全异常处理器.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月14日 上午8:47:01 LH
 *         new file.
 * </pre>
 */
@RestControllerAdvice // @ResponseBody + @ControllerAdvice
public interface SecurityExceptionHandle {

    Logger log = LoggerFactory.getLogger(SecurityExceptionHandle.class);

    @ExceptionHandler(value = { SecurityException.class })
    public default Result<?> handler(SecurityException e, HttpServletResponse response) {
        response.setStatus(Code.UNAUTHED);
        return new Result<>(Code.UNAUTHED, "无效认证, 请重新认证");
    }

    @ExceptionHandler(value = { AuthenticationCredentialsNotFoundException.class })
    public default Result<?> handler(AuthenticationCredentialsNotFoundException e, HttpServletResponse response) {
        response.setStatus(Code.UNAUTHED);
        return new Result<>(Code.UNAUTHED, "缺失凭证, 请重新认证获取");
        // 无可用令牌导致 SecurityContextHolder.getContext().setAuthentication(null);
    }

    @ExceptionHandler(value = { AccessDeniedException.class })
    public default Result<?> handler(AccessDeniedException e, HttpServletResponse response) {
        response.setStatus(Code.FORBIDDEN);
        // String path = request.getMethod() + " " + request.getRequestURI();
        return new Result<>(Code.FORBIDDEN, "拒绝访问, 请重新登录或联系客服");
    }

}