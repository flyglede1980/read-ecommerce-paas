package net.yitun.basic.website.support;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import net.yitun.basic.Code;
import net.yitun.basic.exp.BizException;
import net.yitun.basic.model.Result;

/**
 * <pre>
 * <b>Web异常处理器.</b>
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
@RestControllerAdvice // @ResponseBody + @ControllerAdvice
public interface WebsiteExceptionHandle {

    Logger log = LoggerFactory.getLogger(WebsiteExceptionHandle.class);

    @ExceptionHandler(value = { Exception.class })
    public default Result<?> handler(Exception e, HttpServletResponse response) {
        if (log.isInfoEnabled())
            log.info("<<< 系统异常, " + e.toString(), e);
        response.setStatus(Code.SERVER_ERROR);
        return new Result<>(Code.SERVER_ERROR, "系统异常, 请联系管理");
    }

    @ExceptionHandler(value = BizException.class)
    public default Result<?> handler(BizException e, HttpServletResponse response) {
        if (log.isInfoEnabled())
            log.info("<<< 业务异常, " + e.getMessage());
//        response.setStatus(e.getCode());
        return new Result<>(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public default Result<?> handler(BindException e, HttpServletResponse response) {
        if (log.isDebugEnabled())
            log.debug("<<< 参数装配无效, " + e.getMessage());

        // e.getFieldError(): 随机返回一个对象属性的异常信息。如果要一次性返回所有对象属性异常信息，则调用e.getAllErrors()
        List<ObjectError> objErrors = e.getAllErrors();
        Set<String> errors = new HashSet<>(objErrors.size());
        for (ObjectError error : objErrors)
            if (error instanceof FieldError) {
                FieldError ferror = (FieldError) error;
                errors.add(ferror.getField() + "=[" + ferror.getRejectedValue() + "] " + ferror.getDefaultMessage());
            } else
                errors.add(error.getDefaultMessage());
//        response.setStatus(Code.BAD_REQ);
        return new Result<>(Code.BAD_REQ, "参数装配无效: " + StringUtils.join(errors, "; "));
    }

    @ExceptionHandler(MultipartException.class)
    public default Result<?> handler(MultipartException e, HttpServletResponse response) { // RedirectAttributes redirect
        if (log.isDebugEnabled())
            log.debug("<<< 文件上传异常, " + e.getMessage());
//        response.setStatus(Code.SERVER_ERROR);
        return new Result<>(Code.SERVER_ERROR, "文件上传异常, 请联系管理");
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public default Result<?> handler(HttpMessageNotReadableException e, HttpServletResponse response) {
//        response.setStatus(Code.BAD_REQ);
        return new Result<>(Code.BAD_REQ, "请求参数无法识别");
    }

    @ExceptionHandler(value = ServletRequestBindingException.class)
    public default Result<?> handler(ServletRequestBindingException e, HttpServletResponse response) {
//        response.setStatus(Code.BAD_REQ);
        return new Result<>(Code.BAD_REQ, "请求参数缺失");
    }

    @ExceptionHandler(value = { HttpMediaTypeNotSupportedException.class })
    public default Result<?> handler(HttpMediaTypeNotSupportedException e, HttpServletResponse response) {
//        response.setStatus(Code.BAD_REQ);
        return new Result<>(Code.BAD_REQ, "请求参数格式不支持");
    }

    @ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
    public default Result<?> handler(HttpRequestMethodNotSupportedException e, HttpServletResponse response) {
//        response.setStatus(Code.BAD_REQ);
        return new Result<>(Code.BAD_REQ, "请求方式无效, 请使用" + Arrays.toString(e.getSupportedMethods()) + "方式");
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public default Result<?> handler(MethodArgumentNotValidException e, HttpServletResponse response) {
        Set<String> errors = new HashSet<>();
        for (ObjectError error : e.getBindingResult().getAllErrors())
            errors.add(error.getDefaultMessage());
//        response.setStatus(Code.BAD_REQ);
        return new Result<>(Code.BAD_REQ, "请求参数无效: " + StringUtils.join(errors, "; "));
    }

}