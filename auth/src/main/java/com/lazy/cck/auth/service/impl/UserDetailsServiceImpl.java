package com.lazy.cck.auth.service.impl;

import com.lazy.cck.auth.security.pojo.RequestAccount;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("zhangsan",null,AuthorityUtils.NO_AUTHORITIES);
    }

    public RequestAccount loadUserByPhone(String phone) throws UsernameNotFoundException {

        return new RequestAccount();

    }
}
