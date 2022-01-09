package com.wrc.tutor.upms.back.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wrc.tutor.common.entity.po.Permission;
import com.wrc.tutor.upms.back.entity.bo.PermissionBO1;
import com.wrc.tutor.upms.back.entity.dto.PermissionDTO;
import com.wrc.tutor.upms.back.entity.query.PermissionQuery1;
import com.wrc.tutor.upms.back.service.BackPermissionService;
import com.wrc.tutor.upms.common.exception.ResourceNotFondException;
import com.wrc.tutor.upms.common.validate.group.Insert;
import com.wrc.tutor.upms.common.validate.group.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
@RestController
@RequestMapping("back/permissions")
public class BackPermissionController {

    @Autowired
    BackPermissionService backPermissionService;

    /**
     * 增加单个权限
     * TODO 返回新增的id 还是 返回整个Permission?
     * @param
     * @param
     * @return 新添加的权限
     */
    @PreAuthorize("hasAuthority('upms:permission:insert')")
    @PostMapping
    public PermissionDTO savePermission(@Validated(Insert.class) @RequestBody PermissionBO1 permission){
        permission.setId(null);
        return backPermissionService.savePermission(permission);
    }


    /**
     * 根据Id删除单个权限
     * TODO 删除是否需要把删除成功的权限返回去，各大公司的都没有返回，原因是什么?
     */
    @PreAuthorize("hasAuthority('upms:permission:delete')")
    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removePermissionById(@PathVariable("id") Long id){
        backPermissionService.removePermissionById(id);
    }

    /**
     * 根据Ids删除多个权限
     */
    @PreAuthorize("hasAuthority('upms:permission:delete')")
    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void removePermissionsByIds( @RequestBody List<Long> ids){
        backPermissionService.removePermissionsByIds(ids);
    }

    /**
     * 覆盖修改单个权限
     */
    @PreAuthorize("hasAuthority('upms:permission:update')")
    @PutMapping
    public PermissionDTO updatePermission(@Validated(Update.class) @RequestBody PermissionBO1 permission){
        return backPermissionService.updatePermission(permission);
    }


    @PreAuthorize("hasAuthority('upms:permission:update')")
    @PatchMapping
    public PermissionDTO patchPermission(@Validated(Update.class) @RequestBody PermissionBO1 permission){
        return backPermissionService.patchPermission(permission);
    }

    /**
     * 根据id查询单个权限
     * @return 不存在返回空,不抛异常
     */
    @PreAuthorize("hasAuthority('upms:permission:select')")
    @GetMapping("{id}")
    public PermissionDTO getPermissionById( @PathVariable("id") Long  id) throws ResourceNotFondException {
        return backPermissionService.getPermissionById(id);
    }


    /**
     * 根据权限名查询单个权限
     * @param name
     * @return 不存在返回空,不抛异常
     */
    @PreAuthorize("hasAuthority('upms:permission:select')")
    @GetMapping("name/{name}")
    public PermissionDTO getPermissionByName(@PathVariable("name") String name) throws ResourceNotFondException {
        return backPermissionService.getPermissionByName(name);
    }

    /**
     * 多条件查询+分页+排序
     * TODO 直接使用 mybatis plus 提供的Page还是自定义一个PageVO？
     * TODO 不能直接返回数据层的 Permission 要返回 PermissionView 去掉密码等字段，防止字段暴露
     * 不存在返回空,不抛异常
     */
    @PreAuthorize("hasAuthority('upms:permission:select')")
    @GetMapping("page")
    public IPage<PermissionDTO> pagePermissions(Page<Permission> pageQuery, PermissionQuery1 permissionQuery){
        return backPermissionService.pagePermissions(pageQuery, permissionQuery);
    }


    @PreAuthorize("hasAuthority('upms:permission:select')")
    @GetMapping
    public List<PermissionDTO> listPermissions(PermissionQuery1 permissionQuery){
        return backPermissionService.listPermissions(permissionQuery);
    }


    /**
     * 以下开始测试
     */


    /**
     * 根据父id获取子权限列表
     * 没有返回空列表不抛异常
     * @param parentId
     * @return
     */
    @PreAuthorize("hasAuthority('upms:permission:select')")
    @GetMapping("parentId/{parentId}")
    List<PermissionDTO> listPermissionsByParentId(@PathVariable("parentId") Long parentId){
        return backPermissionService.listPermissionsByParentId(parentId);
    }


}

