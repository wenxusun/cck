package com.lazy.cck.auth.security.authentication;


import com.lazy.cck.auth.security.pojo.LoginParams;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author wenxu
 */
public class PhoneAndCodeAuthentication extends UsernamePasswordAuthenticationToken {


    public PhoneAndCodeAuthentication(LoginParams params) {
        super(params.getPhone(), params.getCode());
    }

}
