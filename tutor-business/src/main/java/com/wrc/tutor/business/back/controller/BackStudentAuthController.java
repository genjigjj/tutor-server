package com.wrc.tutor.business.back.controller;


import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.business.back.entity.bo.StudentAuthBO1;
import com.wrc.tutor.business.back.entity.query.StudentAuthQuery1;
import com.wrc.tutor.business.back.entity.vo.StudentAuthVO;
import com.wrc.tutor.business.back.service.BackStudentAuthService;
import com.wrc.tutor.common.entity.po.StudentAuth;
import com.wrc.tutor.common.entity.query.PageQuery;
import com.wrc.tutor.common.entity.vo.MyPage;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 大学生认证 前端控制器
 * </p>
 *
 * @author wrc
 * @since 2020-01-27
 */
@RestController
@RequestMapping("/business/back/studentAuths")
public class BackStudentAuthController {

    @Autowired
    BackStudentAuthService backStudentAuthService;



    @ApiOperation(value="分页大学生认证列表",notes="可以带查询条件和排序字段")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:studentAuth:select')")
    @GetMapping("page")
    public MyPage<StudentAuthVO> page(PageQuery pageQuery, StudentAuthQuery1 studentAuthQuery){
        MyPage<StudentAuth> pagePO = backStudentAuthService.myPage(pageQuery, studentAuthQuery);
        List<StudentAuth> records = pagePO.getRecords();

//        转换成我们的分页对象
        MyPage<StudentAuthVO> pageVO = BeanCopyUtils.copyBean(pagePO,MyPage.class);

//        将PO转换成VO
        List<StudentAuthVO> vos = BeanCopyUtils.copyList(records, StudentAuthVO.class);
        pageVO.setRecords(vos);
        return pageVO;
    }


    @ApiOperation(value="审核大学生认证",notes="")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:studentAuth:update')")
    @PatchMapping("{id}/audit")
    public void   audit(@PathVariable("id") Long id, @RequestBody StudentAuthBO1 studentAuthBO){
        backStudentAuthService.audit(id, studentAuthBO);
    }

}

