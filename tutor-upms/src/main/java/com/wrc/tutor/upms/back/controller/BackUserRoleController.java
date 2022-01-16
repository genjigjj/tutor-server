package com.wrc.tutor.upms.back.controller;


import com.wrc.tutor.upms.back.entity.dto.RoleDTO;
import com.wrc.tutor.upms.back.service.BackUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wrc
 * @since 2019-08-23
 */
@Validated
@RestController
@Slf4j
public class BackUserRoleController {

   @Autowired
   BackUserRoleService backUserRoleService;

    /**
     * 根据用户id查询用户的角色
     */
    @PreAuthorize("hasAuthority('upms:userRole:select')")
    @GetMapping("/upms/back/users/{userId}/roles")
    List<RoleDTO> listRolesByUserId(@PathVariable("userId") Long userId){
        return backUserRoleService.listRolesByUserId(userId);
    }


    /**
     * 根据用户id和角色id列表为用户添加角色
     */
    @PreAuthorize("hasAuthority('upms:userRole:update')")
    @PutMapping("/upms/back/users/{userId}/roles")
    void saveUserRolesByUserId(@PathVariable("userId") Long userId,@RequestBody List<Long> roleIds){
        backUserRoleService.saveUserRolesByUserId(userId,roleIds);
    }


}

