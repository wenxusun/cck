package com.lazy.cck.auth.security.provider;



import com.lazy.cck.auth.security.authentication.UsernamePasswordTypeAuthentication;
import com.lazy.cck.auth.security.pojo.RequestAccount;
import com.lazy.cck.auth.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;


@RequiredArgsConstructor
public class UserNameAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UsernamePasswordTypeAuthentication uptAuthentication = (UsernamePasswordTypeAuthentication) authentication;

        User requestAccount = (User) userDetailsService.loadUserByUsername(uptAuthentication.getName());

        return new UsernamePasswordAuthenticationToken(requestAccount, null, requestAccount.getAuthorities());
    }





    @Override
    public boolean supports(Class<?> authentication) {
        return  authentication.isAssignableFrom(UsernamePasswordTypeAuthentication.class);
    }

}
