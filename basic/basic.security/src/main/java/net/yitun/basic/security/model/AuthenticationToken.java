package net.yitun.basic.security.model;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * <pre>
 * <b>认证令牌.</b>
 * <b>Description:</b>
 *    
 * <b>Author:</b> LH
 * <b>Date:</b> 2017-09-20 16:48:53.202
 * <b>Copyright:</b> Copyright ©2018 cn.tb. All rights reserved.
 * <b>Changelog:</b>
 *   Ver   Date                    Author                Detail
 *   ----------------------------------------------------------------------
 *   0.1   2018年11月12日 上午10:47:00 LH
 *         new file.
 * </pre>
 */
public class AuthenticationToken extends UsernamePasswordAuthenticationToken {

    /** SVUID */
    private static final long serialVersionUID = 1L;

    public AuthenticationToken(Object principal, Object credentials, Object details,
            Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.setDetails(details);
    }

}
