package com.wrc.tutor.business.back.controller;

import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.business.back.entity.bo.CategoryBO1;
import com.wrc.tutor.business.back.entity.query.CategoryQuery1;
import com.wrc.tutor.business.back.entity.vo.CategoryVO;
import com.wrc.tutor.business.back.service.BackCategoryService;
import com.wrc.tutor.business.common.validate.group.Insert;
import com.wrc.tutor.business.common.validate.group.Update;
import com.wrc.tutor.common.entity.po.Category;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("back/categories")
@Api("课程接口")
public class BackCategoryController {

    @Autowired
    BackCategoryService backCategoryService;

    @ApiOperation(value="获取科目列表",notes="可以带查询条件")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:category:select')")
    @GetMapping
    public List<CategoryVO> list(CategoryQuery1 categoryQuery){
        List<Category> categories = backCategoryService.Mylist(categoryQuery);
        return BeanCopyUtils.copyList(categories, CategoryVO.class);
    }


    @ApiOperation(value="根据父ID获取科目列表",notes = "0 代表顶级科目")
    @ApiImplicitParam(paramType="path", name = "id", value = "父ID", required = true, dataType = "Long")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:category:select')")
    @GetMapping("parentId/{id}")
    public List<CategoryVO> listByParentId(@PathVariable("id") Long parentId){
        List<Category> categories = backCategoryService.listByParentId(parentId);
        return BeanCopyUtils.copyList(categories, CategoryVO.class);
    }



    @ApiOperation(value="新添科目",notes = "0 代表顶级科目")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:category:insert')")
    @PostMapping
    public CategoryVO save(@Validated(Insert.class) @RequestBody CategoryBO1 categoryBO){
        Category category = backCategoryService.mySave(categoryBO);
        return BeanCopyUtils.copyBean(category,CategoryVO.class);
    }


    @ApiOperation(value="根据id删除科目",notes = "0 代表顶级科目")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:category:delete')")
    @DeleteMapping("{id}")
    public void removeRouteById(@PathVariable("id") Long id){
        backCategoryService.myRemoveById(id);
    }


    @ApiOperation(value="部分字段修改学科",notes = "0 代表顶级科目")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:category:update')")
    @PatchMapping
    public void patchRoute(@Validated(Update.class) @RequestBody CategoryBO1 categoryBO){
        backCategoryService.myPatch(categoryBO);
    }


}
