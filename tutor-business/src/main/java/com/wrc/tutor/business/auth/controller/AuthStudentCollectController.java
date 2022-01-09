package com.wrc.tutor.business.auth.controller;


import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.business.auth.entity.vo.TeacherVO2;
import com.wrc.tutor.business.auth.service.AuthStudentCollectService;
import com.wrc.tutor.business.back.entity.query.TeacherQuery1;
import com.wrc.tutor.common.entity.po.Teacher;
import com.wrc.tutor.common.entity.query.PageQuery;
import com.wrc.tutor.common.entity.vo.MyPage;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wrc
 * @since 2020-01-27
 */
@RestController
@RequestMapping("auth/studentCollects/me")
public class AuthStudentCollectController {


    @Autowired
    AuthStudentCollectService authStudentCollectService;

    @ApiOperation(value="分页获取我收藏的老师")
    @GetMapping("teachers/page")
    public MyPage<TeacherVO2> page(PageQuery pageQuery, TeacherQuery1 teacherQuery1){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        MyPage<Teacher> myPage = authStudentCollectService.myPage(id,pageQuery,teacherQuery1);
        List<Teacher> records = myPage.getRecords();

        List<TeacherVO2> voS = BeanCopyUtils.copyList(records, TeacherVO2.class);
        MyPage<TeacherVO2> myPage1 = BeanCopyUtils.copyBean(myPage, MyPage.class);

        myPage1.setRecords(voS);

        return myPage1;
    }

    @ApiOperation(value="收藏老师")
    @PostMapping("teacherId/{teacherId}")
    public void save(@PathVariable("teacherId") Long teacherId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());
        authStudentCollectService.mySave(id,teacherId);
    }


    @ApiOperation(value="取消收藏老师")
    @DeleteMapping("teacherId/{teacherId}")
    public void delete(@PathVariable("teacherId") Long teacherId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());
        authStudentCollectService.myDelete(id,teacherId);
    }

}

