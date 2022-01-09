package com.wrc.tutor.business.auth.controller;


import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.business.auth.entity.bo.NeedBO1;
import com.wrc.tutor.business.auth.entity.query.NeedQuery1;
import com.wrc.tutor.business.auth.entity.vo.NeedVO;
import com.wrc.tutor.business.auth.service.AuthNeedService;
import com.wrc.tutor.business.common.validate.group.Insert;
import com.wrc.tutor.common.entity.dto.NeedDTO;
import com.wrc.tutor.common.entity.po.Need;
import com.wrc.tutor.common.entity.query.PageQuery;
import com.wrc.tutor.common.entity.vo.MyPage;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 预约表 前端控制器
 * </p>
 *
 * @author wrc
 * @since 2020-01-27
 */
@Validated
@RestController
@RequestMapping("auth/needs/me")
public class AuthNeedController {

    @Autowired
    AuthNeedService AuthNeedService;

    @ApiOperation(value="检测我是否是某需求的发布者")
    @GetMapping("{needId}/isMyPublish")
    public boolean isMyPublish(@PathVariable("needId") Long needId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        return AuthNeedService.isMyPublish(id,needId);
    }

    @ApiOperation(value="检测我是否已预约了某需求")
    @GetMapping("{needId}/isMyAppoint")
    public boolean isMyAppoint(@PathVariable("needId") Long needId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        return AuthNeedService.isMyAppoint(id,needId);
    }

//    -------------------------------------------------------------------------

    @ApiOperation(value="学生选中某个老师为标") //需要调用支付服务
    @PatchMapping("{needId}/teacher/{teacherId}")
    public String chooseTeacherForNeed(@PathVariable("needId") Long needId,@PathVariable("teacherId") Long teacherId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        return AuthNeedService.chooseTeacherForNeed(id,needId,teacherId); //返回 支付地址
    }


    @ApiOperation(value="学生端添加一个需求")
    @PostMapping
    public NeedVO saveNeed(@Validated(Insert.class) @RequestBody NeedBO1 needBO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Need need  = AuthNeedService.saveNeed(id,needBO);

        return null;
    }


    @ApiOperation(value="学员端分页获取我的需求")
    @GetMapping("page")
    public MyPage<NeedVO> pageNeeds(PageQuery pageQuery, NeedQuery1 needQuery){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        MyPage<NeedDTO> myPage = AuthNeedService.pageDTO(id,pageQuery,needQuery);
        List<NeedDTO> records = myPage.getRecords();

        List<NeedVO> needVOS = BeanCopyUtils.copyList(records, NeedVO.class);
        MyPage<NeedVO> myPage1 = BeanCopyUtils.copyBean(myPage, MyPage.class);

        myPage1.setRecords(needVOS);

        return myPage1;
    }


    @ApiOperation(value="学员端关闭需求")
    @PatchMapping("{needId}/close")
    public NeedVO closeAppoint(@PathVariable("needId") Long needId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Need need = AuthNeedService.close(id,needId);

//        return BeanCopyUtils.copyBean(need, NeedVO.class);
        return null; //
    }

    @ApiOperation(value="学员端删除需求")
    @PatchMapping("{needId}/delete")
    public NeedVO deleteAppoint(@PathVariable("needId") Long needId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        Need need = AuthNeedService.deleteAppoint(id,needId);

//        return BeanCopyUtils.copyBean(need, NeedVO.class);
        return null; //
    }

}

