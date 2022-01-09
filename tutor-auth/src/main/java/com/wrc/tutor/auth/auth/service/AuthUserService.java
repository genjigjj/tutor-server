package com.wrc.tutor.auth.auth.service;

import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.auth.auth.entity.dto.UserDTO;
import com.wrc.tutor.auth.base.authentication.code.core.Manager;
import com.wrc.tutor.auth.common.exception.BusinessException;
import com.wrc.tutor.auth.front.entity.bo.FeignUserBO1;
import com.wrc.tutor.auth.front.entity.vo.FeignUserInfo;
import com.wrc.tutor.auth.front.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthUserService {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    private Manager manager;


    //根据用户名获取用户
    public UserDTO getByUsername(Object principal) {
        String username = (String) principal;
        FeignUserInfo userInfo = userService.getUserInfo(username);
        return BeanCopyUtils.copyBean(userInfo, UserDTO.class);
    }

    public boolean emailIsExist(String email) {
        FeignUserInfo has = userService.getUserInfo(email);
        return has != null;
    }

    public boolean phoneIsExist(String phone) {
        FeignUserInfo has = userService.getUserInfo(phone);
        return has != null;
    }

    public UserDTO bindingEmail(String username, String email, String code) {
//       校验验证码
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        manager.validate(request, "emailCode");
//        查出用户并添加邮箱
        FeignUserInfo userInfo = userService.getUserInfo(username);
        if (StringUtils.isNotBlank(userInfo.getEmail())) {
            throw new BusinessException("请先解绑原邮箱后再绑定");
        }
        FeignUserBO1 feignUserBO = BeanCopyUtils.copyBean(userInfo, FeignUserBO1.class);
        feignUserBO.setEmail(email);
        userService.saveUser(feignUserBO);
        return BeanCopyUtils.copyBean(feignUserBO, UserDTO.class);
    }


    public UserDTO bindingPhone(String username, String phone, String code) {
        //       校验验证码
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        manager.validate(request, "emailCode");
//        查出用户并添加手机号
        FeignUserInfo userInfo = userService.getUserInfo(username);
        userInfo.setEmail(phone);
        FeignUserBO1 feignUserBO = BeanCopyUtils.copyBean(userInfo, FeignUserBO1.class);
        userService.saveUser(feignUserBO);
        return BeanCopyUtils.copyBean(userInfo, UserDTO.class);
    }

    public UserDTO emailCodeUpdatePassword(String username, String newPassword, String code) {
        return null;
    }

    public UserDTO phoneCodeUpdatePassword(String username, String newPassword, String code) {
        return null;
    }

    public UserDTO sendEmailCodeForUser(String username) {
        return null;
    }

    public UserDTO sendPhoneCodeForUser(String username) {
        return null;
    }
}
