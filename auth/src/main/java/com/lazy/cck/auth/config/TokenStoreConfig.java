package com.lazy.cck.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;

@Configuration
public class TokenStoreConfig {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Bean // 声明RedisTokenStore实现
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }


//    @Resource
//    private DataSource dataSource;
//
//    @Bean // 声明TokenStore实现
//    public TokenStore tokenStore() {
//        return new JdbcTokenStore(dataSource);
//    }

}
