package com.wrc.tutor.system.back.controller;

import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.common.entity.po.Advice;
import com.wrc.tutor.common.entity.query.PageQuery;
import com.wrc.tutor.common.entity.vo.MyPage;
import com.wrc.tutor.system.back.entity.bo.AdviceBO1;
import com.wrc.tutor.system.back.entity.query.AdviceQuery1;
import com.wrc.tutor.system.back.entity.vo.AdviceVO;
import com.wrc.tutor.system.back.service.BackAdviceService;
import com.wrc.tutor.system.common.validate.group.Insert;
import com.wrc.tutor.system.common.validate.group.Update;
import io.swagger.annotations.Api;
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
@RequestMapping("/system/back/advices")
@Api("建议接口")
public class BackAdviceController {


    @Autowired
    BackAdviceService backAdviceService;

    @ApiOperation(value="新添")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:advice:insert')")
    @PostMapping
    public AdviceVO save(@Validated(Insert.class) @RequestBody AdviceBO1 bo){
        Advice po = backAdviceService.mySave(bo);
        return BeanCopyUtils.copyBean(po,AdviceVO.class);
    }


    @ApiOperation(value="根据id删除")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:advice:delete')")
    @DeleteMapping("{id}")
    public void removeById(@PathVariable("id") Long id){
        backAdviceService.myRemoveById(id);
    }


    @ApiOperation(value="部分字段修改地区")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:advice:update')")
    @PatchMapping
    public void patchRoute(@Validated(Update.class) @RequestBody AdviceBO1 bo){
        backAdviceService.myPatch(bo);
    }


    @ApiOperation(value="分页")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:advice:select')")
    @GetMapping("page")
    public MyPage<AdviceVO> page(PageQuery pageQuery, AdviceQuery1 query){
        MyPage<Advice> pagePO = backAdviceService.myPage(pageQuery, query);
        List<Advice> records = pagePO.getRecords();

//        转换成我们的分页对象
        MyPage<AdviceVO> pageVO = BeanCopyUtils.copyBean(pagePO, MyPage.class);

//        将PO转换成VO
        List<AdviceVO> vos = BeanCopyUtils.copyList(records, AdviceVO.class);
        pageVO.setRecords(vos);
        return pageVO;
    }

}
