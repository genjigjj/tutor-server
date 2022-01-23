package com.wrc.tutor.auth.common.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * 资源服务器配置
 *
 * @author zhailiang
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MyResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(
                        "/users/userInfo**",
                        "/users/**", "/auth/login",
                        "/auth/**",
                        "/back/**",
                        "/code/**",
                        "/users/isExist/**",
                        "/users/**",
                        "oauth/**",
                        "/social/unbinding"
                ).permitAll()
                .anyRequest().authenticated();
    }
}
