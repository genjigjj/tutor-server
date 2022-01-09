/**
 *
 */
package com.wrc.tutor.auth.front.controller;


import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.auth.front.entity.bo.UserBO1;
import com.wrc.tutor.auth.front.entity.dto.UserDTO;
import com.wrc.tutor.auth.front.entity.vo.UserVO;
import com.wrc.tutor.auth.front.service.UserService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/users")
public class UserController {

//	@Autowired
//	private ProviderSignInUtils providerSignInUtils;

    @Autowired
    UserService userService;
    /**
     * 用户注册
     */
    @PostMapping
    public UserVO saveUser(@Validated @RequestBody UserBO1 userBO){
        UserDTO userDTO = userService.save(userBO);
        return BeanCopyUtils.copyBean(userDTO,UserVO.class);
    }

    /**
     * 校验用户是否存在
     */
    @GetMapping("/isExist/{username}")
    public Map<String,Boolean> usernameIsExist(@PathVariable("username") String username){
        boolean isExist =  userService.usernameIsExist(username);
        Map<String,Boolean> map = new HashedMap();
        map.put("isExist",isExist);
        return map;
    }

}
