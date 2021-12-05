package com.lazy.cck.auth.config;

import com.lazy.cck.auth.security.filter.LoginFilter;
import com.lazy.cck.auth.security.filter.RequestLogFilter;
import com.lazy.cck.auth.security.provider.PhoneAuthenticationProvider;
import com.lazy.cck.auth.security.provider.UserNameAuthenticationProvider;
import com.lazy.cck.auth.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;

    private final StringRedisTemplate stringRedisTemplate;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new RequestLogFilter(), LogoutFilter.class)
                .addFilterAfter(new LoginFilter(authenticationManager()), RequestLogFilter.class)
                .logout().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new UserNameAuthenticationProvider(userDetailsService))
                .authenticationProvider(new PhoneAuthenticationProvider(userDetailsService, stringRedisTemplate));

    }
}
