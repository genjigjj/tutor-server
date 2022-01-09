package com.wrc.tutor.auth.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.auth.common.exception.BusinessException;
import com.wrc.tutor.auth.front.entity.bo.FeignUserBO1;
import com.wrc.tutor.auth.front.entity.bo.UserBO1;
import com.wrc.tutor.auth.front.entity.dto.UserDTO;
import com.wrc.tutor.auth.front.entity.vo.FeignUserInfo;
import com.wrc.tutor.common.entity.dto.UserDTO1;
import com.wrc.tutor.common.entity.po.Permission;
import com.wrc.tutor.common.entity.po.Role;
import com.wrc.tutor.common.entity.po.RolePermission;
import com.wrc.tutor.common.entity.po.Teacher;
import com.wrc.tutor.common.entity.po.User;
import com.wrc.tutor.common.entity.po.UserRole;
import com.wrc.tutor.common.mapper.PermissionMapper;
import com.wrc.tutor.common.mapper.RoleMapper;
import com.wrc.tutor.common.mapper.RolePermissionMapper;
import com.wrc.tutor.common.mapper.TeacherMapper;
import com.wrc.tutor.common.mapper.UserMapper;
import com.wrc.tutor.common.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService extends ServiceImpl<UserMapper, User> {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleMapper roleMapper;


    public UserDTO1 getUserInfoByUsername(String username) {
        //        找出用户,
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.or();
        queryWrapper.eq("email", username);
        queryWrapper.or();
        queryWrapper.eq("phone", username);
        User has = getBaseMapper().selectOne(queryWrapper);
        if (has == null) {
            log.info("用户不存在: username={}", username);
            return null; // TODO 远程调用抛异常还是返回 统一结果？
        }

//        查中间表得到全部角色id
        QueryWrapper<UserRole> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("user_id", has.getId());
        List<UserRole> userRoles = userRoleMapper.selectList(queryWrapper1);
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
//      查中间表得到全部权限id
        List<Long> permissionIds = new ArrayList<>(0);
        QueryWrapper<RolePermission> queryWrapper2 = new QueryWrapper<>();
        if (roleIds.size() != 0) {
            queryWrapper2.in("role_id", roleIds);
            List<RolePermission> rolePermissions = rolePermissionMapper.selectList(queryWrapper2);
            permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        }

//      查出全部角色
        List<Role> roles = new ArrayList<>(0);
        if (roleIds.size() != 0) {
            roles = roleMapper.selectBatchIds(roleIds);
        }

//      查出全部权限
        List<Permission> permissions = new ArrayList<>(0);
        if (permissionIds.size() != 0) {
            permissions = permissionMapper.selectBatchIds(permissionIds);
        }


        UserDTO1 userDTO1 = BeanCopyUtils.copyBean(has, UserDTO1.class);
        userDTO1.setRoles(roles);
        userDTO1.setPermissions(permissions);

        return userDTO1;
    }


    @Transactional
    public UserDTO save(UserBO1 userBO) {
        // 根据用户名（包括邮箱，手机号）获取用户信息
        UserDTO1 user = getUserInfoByUsername((userBO.getUsername()));
        if (user != null) {
            throw new BusinessException("用户名已存在");
        }
        // 保存用户
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(userBO.getPassword()));
        newUser.setUsername(userBO.getUsername());
        super.save(newUser);
        // 远程新建一个老师
        Teacher teacher = new Teacher();
        teacher.setUserId(newUser.getId());
        teacherMapper.insert(teacher);
        return BeanCopyUtils.copyBean(newUser, UserDTO.class);
    }


    public boolean usernameIsExist(String username) {
        UserDTO1 has = getUserInfoByUsername(username);
        return has != null;
    }

    public FeignUserInfo getUserInfo(String username) {
        UserDTO1 userDTO1 = getUserInfoByUsername(username);
        if (userDTO1 == null) {
            return null;
        }
        FeignUserInfo feignUserInfo = BeanCopyUtils.copyBean(userDTO1, FeignUserInfo.class);
        feignUserInfo.setRolesName(userDTO1.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
        feignUserInfo.setPermissionsValue(userDTO1.getPermissions().stream().map(Permission::getValue).collect(Collectors.toList()));
        return feignUserInfo;
    }

    public User saveUser(FeignUserBO1 feignUserBO) {
//       重复校验
        if (getUserInfo(feignUserBO.getUsername()) != null) {
            log.info("用户名已存在:{}", feignUserBO.getUsername());
            throw new BusinessException("用户名已存在!");
        }
//      bo 转 po 并字段处理
        User saving = BeanCopyUtils.copyBean(feignUserBO, User.class);
        super.save(saving);
        return saving;
    }


    public UserDTO1 getUserInfoById(Long id) {
        //        找出用户,
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        User has = getBaseMapper().selectOne(queryWrapper);
        if (has == null) {
            log.info("用户不存在: username={}", id);
            return null; // TODO 远程调用抛异常还是返回 统一结果？
        }
//        查中间表得到全部角色id
        QueryWrapper<UserRole> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("user_id", has.getId());
        List<UserRole> userRoles = userRoleMapper.selectList(queryWrapper1);
        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());

//      查中间表得到全部权限id
        List<Long> permissionIds = new ArrayList<>(0);
        QueryWrapper<RolePermission> queryWrapper2 = new QueryWrapper<>();
        if (roleIds.size() != 0) {
            queryWrapper2.in("role_id", roleIds);
            List<RolePermission> rolePermissions = rolePermissionMapper.selectList(queryWrapper2);
            permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        }


//      查出全部角色
        List<Role> roles = new ArrayList<>(0);
        if (roleIds.size() != 0) {
            roles = roleMapper.selectBatchIds(roleIds);
        }


//      查出全部权限
        List<Permission> permissions = new ArrayList<>(0);
        if (permissionIds.size() != 0) {
            permissions = permissionMapper.selectBatchIds(permissionIds);
        }


        UserDTO1 userDTO1 = BeanCopyUtils.copyBean(has, UserDTO1.class);
        userDTO1.setRoles(roles);
        userDTO1.setPermissions(permissions);

        return userDTO1;
    }
}
