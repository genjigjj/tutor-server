package com.wrc.tutor.business.auth.controller;


import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.business.auth.entity.bo.TeacherAuthBO1;
import com.wrc.tutor.business.auth.entity.vo.TeacherAuthVO;
import com.wrc.tutor.business.auth.service.AuthTeacherAuthService;
import com.wrc.tutor.business.common.exception.ResourceNotFondException;
import com.wrc.tutor.common.entity.po.TeacherAuth;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 在职教师认证 前端控制器
 * </p>
 *
 * @author wrc
 * @since 2020-01-27
 */
@RestController
@RequestMapping("auth/teacherAuths/me")
public class AuthTeacherAuthController {

    @Autowired
    AuthTeacherAuthService authTeacherAuthService;

    @ApiOperation(value="获取我的教师认证信息")
    @GetMapping
    public TeacherAuthVO getTeacherAuthForTeacher(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        TeacherAuth teacherAuth = null;
        try {
            teacherAuth = authTeacherAuthService.getTeacherAuthForTeacher(id);
        } catch (ResourceNotFondException e) {
            e.printStackTrace();
        }
        return  BeanCopyUtils.copyBean(teacherAuth,TeacherAuthVO.class);
    }

    @PutMapping
    @ApiOperation(value="新增或更新我的教师认证信息")
    public TeacherAuthVO saveOrUpdateTeacherAuthForTeacher(@RequestBody TeacherAuthBO1 teacherAuthBO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        TeacherAuth teacherAuth = authTeacherAuthService.saveOrUpdateTeacherAuthForTeacher(id,teacherAuthBO);
        return BeanCopyUtils.copyBean(teacherAuth,TeacherAuthVO.class);
    }

}

