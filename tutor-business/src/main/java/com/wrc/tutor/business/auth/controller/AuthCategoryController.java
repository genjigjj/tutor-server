package com.wrc.tutor.business.auth.controller;

import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.business.auth.entity.vo.CategoryVO;
import com.wrc.tutor.business.auth.service.AuthCategoryService;
import com.wrc.tutor.common.entity.po.Category;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/business/auth/categories/me")
@Api("课程接口")
public class AuthCategoryController {

    @Autowired
    AuthCategoryService authCategoryService;

    @ApiOperation(value="获取我的可授科目列表")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping
    public List<CategoryVO> listCategoriesForMe(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        List<Category> categories = authCategoryService.listCategoriesForTeacher(id);
        return BeanCopyUtils.copyList(categories,CategoryVO.class);
    }


    @ApiOperation(value="覆盖更新我的可授科目列表")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PutMapping
    public List<CategoryVO> updateCategoriesForMe(@RequestBody List<Long> categoriesIds){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        List<Category> categories = authCategoryService.updateCategoriesForTeacher(id,categoriesIds);;
        return BeanCopyUtils.copyList(categories,CategoryVO.class);
    }


}
