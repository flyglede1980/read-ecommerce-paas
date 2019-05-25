package net.yitun.basic.security.support.filter;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.Code;
import net.yitun.basic.model.Result;
import net.yitun.basic.security.SecurityUtil;
import net.yitun.basic.utils.WebUtil;

@Slf4j
@Component("basic.security.SecurityFilter")
public class SecurityFilter extends BasicAuthenticationFilter {

    public SecurityFilter(AuthenticationManager auth) {
        super(auth);
    }

    @PostConstruct
    protected void init() {
        if (log.isDebugEnabled())
            log.debug("{} init ...", this.getClass().getName());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 通过解析Token
        try {
            SecurityUtil.parseToken(request, response);
        } catch (Exception e) {
            if (log.isDebugEnabled())
                log.debug("<<< {}.doFilterInternal(): {} {}", SecurityFilter.class.getName(), WebUtil.getIp(request),
                        e.getMessage());
            WebUtil.outJson(Code.UNAUTHED, new Result<>(Code.UNAUTHED, e.getMessage()), response);
            return;
        }

        // 执行正常的后续过来处理
        chain.doFilter(request, response);
    }

}
