package com.wrc.tutor.system.back.controller;


import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.common.entity.po.Advertisement;
import com.wrc.tutor.common.entity.query.PageQuery;
import com.wrc.tutor.common.entity.vo.MyPage;
import com.wrc.tutor.system.back.entity.bo.AdvertisementBO1;
import com.wrc.tutor.system.back.entity.query.AdvertisementQuery1;
import com.wrc.tutor.system.back.entity.vo.AdvertisementVO;
import com.wrc.tutor.system.back.service.BackAdvertisementService;
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
@RequestMapping("back/advertisements2")
@Api("大广告接口")
public class BackAdvertisementController {

    @Autowired
    BackAdvertisementService backAdvertisementService;


    @ApiOperation(value="新添")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:advertisement:insert')")
    @PostMapping
    public AdvertisementVO save(@Validated(Insert.class) @RequestBody AdvertisementBO1 bo){
        Advertisement po = backAdvertisementService.mySave(bo);
        return BeanCopyUtils.copyBean(po,AdvertisementVO.class);
    }


    @ApiOperation(value="根据id删除")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:advertisement:delete')")
    @DeleteMapping("{id}")
    public void removeById(@PathVariable("id") Long id){
        backAdvertisementService.myRemoveById(id);
    }


    @ApiOperation(value="部分字段修改地区")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:advertisement:update')")
    @PatchMapping
    public void patchRoute(@Validated(Update.class) @RequestBody AdvertisementBO1 bo){
        backAdvertisementService.myPatch(bo);
    }


    @ApiOperation(value="分页")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('system:advertisement:select')")
    @GetMapping("page")
    public MyPage<AdvertisementVO> page(PageQuery pageQuery, AdvertisementQuery1 query){
        MyPage<Advertisement> pagePO = backAdvertisementService.myPage(pageQuery, query);
        List<Advertisement> records = pagePO.getRecords();

//        转换成我们的分页对象
        MyPage<AdvertisementVO> pageVO = BeanCopyUtils.copyBean(pagePO, MyPage.class);

//        将PO转换成VO
        List<AdvertisementVO> vos = BeanCopyUtils.copyList(records, AdvertisementVO.class);
        pageVO.setRecords(vos);
        return pageVO;
    }

}
