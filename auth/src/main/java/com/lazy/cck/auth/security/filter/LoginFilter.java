package com.lazy.cck.auth.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazy.cck.auth.security.authentication.PhoneAndCodeAuthentication;
import com.lazy.cck.auth.security.authentication.UsernamePasswordTypeAuthentication;
import com.lazy.cck.auth.security.pojo.LoginParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    public LoginFilter(AuthenticationManager manager) {
        super(new AntPathRequestMatcher("/account/login", HttpMethod.POST.name()));
        this.setAuthenticationManager(manager);
        this.setAuthenticationSuccessHandler(this::loginSuccess);
        this.setAuthenticationFailureHandler(this::loginFailure);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginParams params = new ObjectMapper().readValue(request.getInputStream(), LoginParams.class);
            Authentication authentication;
            switch (params.getType()){
                case PHONE:
                    if(StringUtils.isBlank(params.getPhone())||StringUtils.isBlank(params.getCode())){
                        throw new BadCredentialsException("手机号/验证码不能为空");
                    }
                    authentication = new PhoneAndCodeAuthentication(params);
                    break;
                default:
                    if(StringUtils.isBlank(params.getUsername())||StringUtils.isBlank(params.getPassword())){
                        throw new BadCredentialsException("账户名/密码不能为空");
                    }
                    authentication = new UsernamePasswordTypeAuthentication(params);
                    break;
            }

            return getAuthenticationManager().authenticate(authentication);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BadCredentialsException(e.getMessage());
        }
    }

    private void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        RequestAccount account = (RequestAccount) authentication.getPrincipal();

        new ObjectMapper().writeValue(response.getOutputStream(), account.toString());
    }

    private void loginFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        log.error("登录失败:", e);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), "login failed");
    }
}