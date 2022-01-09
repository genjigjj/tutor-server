package com.wrc.tutor.common.entity.dto;

import com.wrc.tutor.common.entity.po.Permission;
import com.wrc.tutor.common.entity.po.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTO1 {

    private Long id;

    private String username;

    private String nickname;

    private String realName;

    private String password;

    private String email;

    private String phone;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private List<Permission> permissions;

    private List<Role> roles;

}
