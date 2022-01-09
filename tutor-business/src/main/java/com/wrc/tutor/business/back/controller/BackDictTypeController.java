package com.wrc.tutor.business.back.controller;

import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.business.back.entity.bo.DictTypeBO1;
import com.wrc.tutor.business.back.entity.query.DictTypeQuery1;
import com.wrc.tutor.business.back.entity.vo.DictTypeVO;
import com.wrc.tutor.business.back.service.BackDictTypeService;
import com.wrc.tutor.common.entity.po.DictType;
import com.wrc.tutor.common.entity.query.PageQuery;
import com.wrc.tutor.common.entity.vo.MyPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("back/dictTypes")
@Api("字典接口")
public class BackDictTypeController {

    @Autowired
    BackDictTypeService backDictTypeService;

    @ApiOperation(value="分页获取字典类型",notes = "具体码写死请参考数据库")
    @ApiImplicitParam(paramType="path", name = "code", value = "字典码", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:dictType:select')")
    @GetMapping("page")
    public MyPage<DictTypeVO> page(PageQuery pageQuery, DictTypeQuery1 dictTypeQuery){
        MyPage<DictType> pagePO = backDictTypeService.myPage(pageQuery, dictTypeQuery);
        List<DictType> records = pagePO.getRecords();

//        转换成我们的分页对象
        MyPage<DictTypeVO> pageVO = BeanCopyUtils.copyBean(pagePO,MyPage.class);

//        将PO转换成VO
        List<DictTypeVO> vos = BeanCopyUtils.copyList(records, DictTypeVO.class);
        pageVO.setRecords(vos);
        return pageVO;
    }

    @ApiOperation(value="增加字典类型",notes = "具体码写死请参考数据库")
    @ApiImplicitParam(paramType="path", name = "code", value = "字典码", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:dictType:insert')")
    @PostMapping
    public DictTypeVO save(@RequestBody DictTypeBO1 dictTypeBO){
        DictType dictType = backDictTypeService.mySave(dictTypeBO);
        return BeanCopyUtils.copyBean(dictType,DictTypeVO.class);
    }

    @ApiOperation(value="部分字段修改字典类型",notes = "具体码写死请参考数据库")
    @ApiImplicitParam(paramType="path", name = "code", value = "字典码", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:dictType:update')")
    @PatchMapping
    public DictTypeVO listByCode(@RequestBody DictTypeBO1 dictTypeBO1){
        DictType dictType = backDictTypeService.myUpdate(dictTypeBO1);
        return BeanCopyUtils.copyBean(dictType,DictTypeVO.class);
    }

}
