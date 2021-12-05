package com.lazy.cck.auth.security.pojo;

import com.lazy.cck.auth.security.pojo.enums.LoginType;
import lombok.Data;

@Data
public class LoginParams{

    private String username;

    private String password;

    /**
     * 手机号
     */
    private String phone;
    /**
     * 验证码
     */
    private String code;

    private LoginType type = LoginType.PASSWORD;

}
