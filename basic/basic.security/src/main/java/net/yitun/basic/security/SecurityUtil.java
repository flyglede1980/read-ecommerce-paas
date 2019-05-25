package net.yitun.basic.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import net.yitun.basic.Code;
import net.yitun.basic.security.dicts.Role;
import net.yitun.basic.security.model.AuthenticationToken;
import net.yitun.basic.security.support.exp.SecurityException;

/**
 * <pre>
 * <b>安全工具.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年9月15日 下午3:49:17 LH
 *         new file.
 * </pre>
 */
@Slf4j
public abstract class SecurityUtil {

    /** 认证头 */
    public static final String AUTH = "Authorization";
    public static final String BEARER = "Bearer"; // HTTP头认证的前缀

    /** 有效期 */
    public static final long MAX_LIFE_TIMES = 1000L * 60 * 60 * 24 * (!log.isDebugEnabled() ? 7 : 180); // 默认7天
    /** 自动刷新令牌时临近过期剩余时间 */
    public static final long MIN_LEAVE_EXPIRE_TIME = 1000L * 60 * 60 * 1; // 默认1小时

    /** 认证密钥 */
    private static final String KEY = "WWlUQGp3dEF1dGg=";
    private static final SecretKey SECRET = SecurityUtil.key();

    /** 密码加密工具 */
    public static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    private static final ThreadLocal<Authentication> LOCAL_AUTHENTICATION = new ThreadLocal<>();

    /**
     * 生成加密KEY
     * 
     * @return SecretKey
     */
    private static SecretKey key() {
        byte[] b = Base64.getDecoder().decode(KEY);
        byte[] key = Base64.getEncoder().encode(b);
        return new SecretKeySpec(key, 0, key.length, "AES");
    }

    /**
     * 明文密码加密
     * 
     * @param passwd 明文
     * @return String 加密后密钥
     */
    public static String encode(String passwd) {
        return ENCODER.encode(passwd);
    }

    /**
     * 生成令牌
     * 
     * @param id       用户ID
     * @param nick     用户昵称
     * @param roles    拥有的角色
     * @param response Http响应
     * @return String 生成的令牌
     */
    public static String bindToken(String id, String nick, String roles, HttpServletResponse response) {
        Map<String, Object> //
        claims = new HashMap<>();
        claims.put("id", id);
        claims.put("sub", nick);
        if (null != roles && !roles.isEmpty())
            claims.put("roles", roles);

        long nowTime = System.currentTimeMillis();
        String compact = Jwts.builder()//
                .setClaims(claims) //
                .setSubject(nick) // 主体
                .setAudience(String.valueOf(id)) // 用户
                .setIssuer("tb.cn") // 签发者
                .setIssuedAt(new Date(nowTime)) // 签发时间
                .setNotBefore(new Date(nowTime)) // 在此之前不可用
                .setExpiration(new Date(nowTime + MAX_LIFE_TIMES)) // 过期时间， 必须大于签发时间
                .signWith(SignatureAlgorithm.HS256, SECRET) //
                .compact();

        // 需要添加"Bearer "字符串头
        String token = SecurityUtil.BEARER + " " + compact;
        if (null == response)
            return token;

        // 可直接设置到响应头中
        response.addHeader(AUTH, token);
        response.addHeader("Cache-Control", "no-store"); // 防止前端取到的时历史缓存
        response.addHeader("Access-Control-Expose-Headers", "Authorization"); // 解决跨越时授权获取特殊的响应头

        return token;
    }

    /**
     * 解析令牌，临期自动刷新
     * 
     * @param token    认证令牌
     * @param response Http响应
     * @return Claims 权限
     */
    public static AuthenticationToken parseToken(String token, HttpServletResponse response) {
        if (!StringUtils.startsWith(token, BEARER) //
                || StringUtils.equals("Bearer xxx.xxx.xxx", token)) {
            // TODO 目前未解决屏蔽自动生成JSESSIONID问题，

            clean(); // 如果请求无令牌，则自动将其认证信息清空，防止因携带SessionID传入串token

            return null;
        }

        Claims claims = null;
        token = StringUtils.replace(token, BEARER + StringUtils.SPACE, StringUtils.EMPTY);
        // token = RegExUtils.replaceFirst(token, BEARER + StringUtils.SPACE, StringUtils.EMPTY);
        try {
            claims = Jwts.parser() //
                    .setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            throw new SecurityException(Code.UNAUTHED, "令牌无效，请重新登录认证或联系客服");
        }

        String id = claims.get("id", String.class);
        String roles = claims.get("roles", String.class);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if (StringUtils.isNotBlank(roles))
            for (String role : roles.split(","))
                if (StringUtils.isNotBlank(roles))
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        // 默认加上匿名用户
        authorities.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));

        Date expiration = claims.getExpiration();
        long nowTime = System.currentTimeMillis();
        // 判断凭证是否临近获取，主动刷新产生新令牌
        if (null != response && (null == expiration || MIN_LEAVE_EXPIRE_TIME >= (expiration.getTime() - nowTime)))
            SecurityUtil.bindToken(id, claims.get("sub", String.class), roles, response);

        // 自动将解析成功的认证凭证存入安全上下文中
        AuthenticationToken authToken = new AuthenticationToken(id, id.hashCode(), claims, authorities);
        if (null != authToken)
            SecurityContextHolder.getContext().setAuthentication(authToken);

        return authToken;
    }

    /**
     * 解析HTTP令牌
     * 
     * @param request  Http请求
     * @param response Http响应
     * @return AuthenticationToken
     */
    public static AuthenticationToken parseToken(HttpServletRequest request, HttpServletResponse response) {
        // 先获取 Authorization 请求头
        String token = request.getHeader(AUTH);
        if (null == token || 12 >= token.length()) {

            Cookie[] cookies = null;
            // 当请求头无效时, 藏尝试从Cookie中获取
            if (null != (cookies = request.getCookies()) && 0 == cookies.length)
                for (Cookie cookie : cookies)
                    if (StringUtils.equals(AUTH, cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
        }

        // TODO 在 Cloud 集群下，需要通过Cloud-Auth-X 获取认证信息

        // 将令牌转解析认证凭证
        return parseToken(token, response);
    }

    /**
     * 用户ID
     * 
     * @return Long
     */
    public static Long getId() {
        Object principal = null;
        Authentication authentication = null;
        SecurityContext context = SecurityContextHolder.getContext();
        if (null == (authentication = context.getAuthentication()) //
                || null == (principal = authentication.getPrincipal()) || "anonymousUser".equals(principal))
            return null;

        try {
            return Long.valueOf(String.valueOf(principal));
        } catch (Exception e) {
            log.error("<<< {}.getId() parse failed, id:{} error:{}", SecurityUtil.class.getName(), principal, e.getMessage());
        }

        return null;
    }

    /**
     * 用户昵称
     * 
     * @return String
     */
    public static String getName() {
        Authentication authentication = null;
        SecurityContext context = SecurityContextHolder.getContext();
        if (null == (authentication = context.getAuthentication()))
            return null;

        Object detail = authentication.getDetails();
        if (null != detail && detail instanceof Claims)
            return ((Claims) detail).getSubject();

        Object principal = authentication.getCredentials();
        if (principal instanceof UserDetails)
            return ((UserDetails) principal).getUsername();

        return null;
    }

    /**
     * 判断用户有指定角色权限
     * 
     * @param roles
     * @return boolean
     */
    public static boolean hasAnyRole(String... roles) {
        Authentication authentication = null;
        Collection<? extends GrantedAuthority> authorities = null;
        SecurityContext context = SecurityContextHolder.getContext();
        if (null == roles || 0 == roles.length //
                || null == (authentication = context.getAuthentication()) //
                || null == (authorities = authentication.getAuthorities()) || authorities.isEmpty())
            return false;

        Set<String> _roles //
                = new HashSet<>(roles.length);
        for (String role : roles)
            _roles.add("ROLE_" + role);

        for (GrantedAuthority authority : authorities)
            if (_roles.contains(authority.getAuthority()))
                return true;

        return false;
    }

    /**
     * 自动开启系统操作临时权限
     * 
     * @param id 用户ID，System: 0
     */
    public static void auto(Long id) {
        Claims claims = new DefaultClaims();
        claims.put("id", id.toString());
        claims.put("sub", "SYSTEM");
        claims.put("roles", Role.USER + "," + Role.ADMIN + "," + Role.SYSTEM);

        AuthenticationToken authToken = new AuthenticationToken( //
                id, id.hashCode(), claims, Arrays.asList( //
                        new SimpleGrantedAuthority("ROLE_" + Role.USER), //
                        new SimpleGrantedAuthority("ROLE_" + Role.ADMIN), //
                        new SimpleGrantedAuthority("ROLE_" + Role.SYSTEM)));

        Authentication authentication = null;
        SecurityContext context = SecurityContextHolder.getContext();
        if (null != (authentication = context.getAuthentication()))
            LOCAL_AUTHENTICATION.set(authentication); // 备份当前持有的认证凭证

        context.setAuthentication(authToken);
    }

    /**
     * 还原认证凭证
     */
    public static void reset() {
        Authentication authentication = null;
        if (null != (authentication = LOCAL_AUTHENTICATION.get()))
            SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 清理无效或者临时认证凭证
     */
    public static void clean() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

}
