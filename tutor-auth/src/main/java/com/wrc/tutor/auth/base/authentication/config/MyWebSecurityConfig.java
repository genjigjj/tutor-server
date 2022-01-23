package com.wrc.tutor.auth.base.authentication.config;

import com.wrc.tutor.auth.base.authentication.code.config.ValidateCodeConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@EnableWebSecurity
public class MyWebSecurityConfig extends WebSecurityConfigurerAdapter {

    //    验证码
    @Autowired
    ValidateCodeConfig validateCodeConfig;

    @Autowired
    AuthenticationEntryPoint authenticationEntryPoint;

    //    密码加密器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {


//       验证码
        http.apply(validateCodeConfig);
        http
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .logout()
                .logoutUrl("/logout")
//                放行登录等端点
                .and().authorizeRequests().antMatchers(
                "/logout",
                "/login",
                "/auth/**",
                "/code/**",
                "/users/isExist/**",
                "/users/**",
                "oauth/**",
                "/**/api-docs/**"
        ).permitAll()
//                其他端点认证后访问
                .anyRequest().authenticated()
//                开启跨资源和关闭跨站
                .and().cors().and().csrf().disable();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
