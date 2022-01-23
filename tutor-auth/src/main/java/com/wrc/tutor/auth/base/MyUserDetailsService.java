package com.wrc.tutor.auth.base;

import com.wrc.tutor.auth.front.entity.vo.FeignUserInfo;
import com.wrc.tutor.auth.front.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * 具体实现 @see JdbcDaoImpl
 */
@Component
@Slf4j
public class MyUserDetailsService implements UserDetailsService {


    //@Autowired
    //private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FeignUserInfo userInfo = userService.getUserInfo(username);
        if (userInfo == null) {
            log.error("用户不存在");
            throw new UsernameNotFoundException("用户不存在" + username);
        }
        return buildUser(userInfo);
    }

    private UserDetails buildUser(FeignUserInfo userInfo) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<String> roles = userInfo.getRolesName();
        List<String> permissions = userInfo.getPermissionsValue();

        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        });

        permissions.forEach(permission -> {
            authorities.add(new SimpleGrantedAuthority(permission));
        });
        return User.builder()
//                .username(userInfo.getUsername())
                .username(String.valueOf(userInfo.getId())) //TODO 这里为了方便,将userId当作Username
                .password(userInfo.getPassword())
                // .disabled(userInfo.getStatus() == 0)
                .authorities(authorities).build();
    }

}
