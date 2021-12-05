package com.lazy.cck.auth.security.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;


@Data
public class RequestAccount implements UserDetails {


    private Integer id;

    /**
     * 账号  账号随机生成
     */
    private String username;

    /**
     * 用户手机号  也可做登录凭证
     */
    private String phone;

    /**
     * 密码 用户密码
     */
    private String password;

    /**
     * 姓名  用户昵称
     */
    private String name;

    /**
     * token
     */
    private String token;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 模块id
     */
    private Integer moduleId;

    /**
     * 请求的唯一标识
     */
    private String requestId;

    /**
     * 账号创建时间
     */
    private LocalDateTime createAt;

    /**
     * 0:正常 1:物业相关角色 2:超级管理员角色
     */
    private Byte roleType;

    /**
     * 数组的前三位代表能否登web mini app
     */
    private Byte allowLogin;
    /**
     * 代理商id
     */
    private Integer agentId;
    public RequestAccount() {

    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }


    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public void setAgentId() {
    }
}
