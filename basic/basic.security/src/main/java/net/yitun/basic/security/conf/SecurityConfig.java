package net.yitun.basic.security.conf;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * <b>安全 配置.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月12日 上午11:29:24 LH
 *         new file.
 * </pre>
 */
@Slf4j
@Order(1)
@EnableWebSecurity // @Configuration + @EnableGlobalAuthentication
@EnableGlobalMethodSecurity(prePostEnabled = true)
// prePostEnabled: 启用基于@PreFilter, @PreAuthorize, @PostAuthorize SpringEL表达式的方法级的安全
// @PreAuthorize("hasRole('ADMIN') AND hasRole('DBA')") @PostAuthorize ("returnObject.type == authentication.name")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @PostConstruct
    protected void init() {
        if (log.isInfoEnabled())
            log.info("{} init ...", this.getClass().getName());
    }

    /**
     * <pre>
     * 通过 {@link #authenticationManager()} 方法的默认实现尝试获取一个 {@link AuthenticationManager}. 
     * 如果被复写, 应该使用{@link AuthenticationManagerBuilder} 来指定 {@link AuthenticationManager}.
     *
     * 例如, 可以使用以下配置在内存中进行注册公开内存的身份验证{@link UserDetailsService}:
     *
     *    // 在内存中添加 user 和 admin 用户
     *    &#64;Override protected void configure(AuthenticationManagerBuilder auth) {
     *     auth.inMemoryAuthentication()
     *         .withUser("user").password("123456").roles("USER")
     *         .and().withUser("admin").password("123456").roles("USER", "ADMIN");
     *    }
     * 
     *     // 将 UserDetailsService 显示为 Bean
     *    &#64;Bean
     *    &#64;Override 
     *    public UserDetailsService userDetailsServiceBean() throws Exception { 
     *         return super.userDetailsServiceBean(); 
     *    }
     * </pre>
     */
    @Autowired
    public void global(AuthenticationManagerBuilder auth) throws Exception {
        // auth.userDetailsService(new JwtUserDetailsService());
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * <pre>
     * 复写这个方法来配置 {@link HttpSecurity}. 
     * 通常，子类不能通过调用 super 来调用此方法，因为它可能会覆盖其配置。 默认配置为：
     *      security
     *          .authorizeRequests().anyRequest().authenticated()
     *          .and().formLogin().and().httpBasic();
     * </pre>
     */
    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security //
                 // .cors().disable() // 关闭Cors支持
                 // Cors请求时，因为Preflight: 预检不携带Cookie，拦截器会认为未认证，该配置是开启支持 PreflightRequest:
                 // .cors().requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .csrf().disable() // 关闭CSRF防护
                .authorizeRequests().anyRequest().permitAll(); // 授权任何请求都不限制权限
    }

}