package com.lazy.cck.auth.security.pojo.enums;

/**
 * @Author wenxu
 */
public enum LoginType {
    /**
     * 密码登录
     */
    PASSWORD(1),
    /**
     * 手机号验证码登录
     */
    PHONE(2),
    /**
     * 微信登录
     */
    WECHAT(3),
    ;
    private Integer value;

    LoginType(int i){
        this.value=i;
    }

    public Integer getValue() {
        return value;
    }
}
