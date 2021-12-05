package com.lazy.cck.auth.security.authentication;

import com.lazy.cck.auth.security.pojo.LoginParams;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author wenxu
 */
public class UsernamePasswordTypeAuthentication extends UsernamePasswordAuthenticationToken {

    public UsernamePasswordTypeAuthentication(LoginParams params) {
        super(params.getUsername(), params.getPassword());
    }

}
