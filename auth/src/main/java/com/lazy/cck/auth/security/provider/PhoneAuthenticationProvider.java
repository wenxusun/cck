package com.lazy.cck.auth.security.provider;


import com.lazy.cck.auth.security.authentication.PhoneAndCodeAuthentication;
import com.lazy.cck.auth.security.pojo.RequestAccount;
import com.lazy.cck.auth.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class PhoneAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsServiceImpl userDetailsService;

    private final StringRedisTemplate stringRedisTemplate;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PhoneAndCodeAuthentication phoneAndCodeAuthentication = (PhoneAndCodeAuthentication) authentication;

        RequestAccount requestAccount = userDetailsService.loadUserByPhone(phoneAndCodeAuthentication.getPrincipal().toString());

        return new UsernamePasswordAuthenticationToken(requestAccount,null,requestAccount.getAuthorities());
    }



    @Override
    public boolean supports(Class<?> authentication) {
        return  authentication.isAssignableFrom(PhoneAndCodeAuthentication.class);
    }

}
