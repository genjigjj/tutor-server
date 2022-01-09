package com.wrc.tutor.business.back.controller;

import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.business.back.entity.bo.DictBO1;
import com.wrc.tutor.business.back.entity.vo.DictVO;
import com.wrc.tutor.business.back.service.BackDictService;
import com.wrc.tutor.common.entity.po.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("back/dicts")
@Api("字典接口")
public class BackDictController {

    @Autowired
    BackDictService backDictService;

    @ApiOperation(value="根据字典码获取字典列表",notes = "具体码写死请参考数据库")
    @ApiImplicitParam(paramType="path", name = "code", value = "字典码", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:dict:select')")
    @GetMapping("code/{code}")
    public List<DictVO> listByCode(@PathVariable("code") String code){
        List<Dict> dicts = backDictService.listByCode(code);
        return BeanCopyUtils.copyList(dicts,DictVO.class);
    }


    @ApiOperation(value="根据字典类型的ID获取字典列表",notes = "具体码写死请参考数据库")
    @ApiImplicitParam(paramType="path", name = "code", value = "字典码", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:dict:select')")
    @GetMapping("dictTypeId/{dictTypeId}")
    public List<DictVO> listByDictTypeId(@PathVariable("dictTypeId") Long dictTypeId){
        List<Dict> dicts = backDictService.listByDictTypeId(dictTypeId);
        return BeanCopyUtils.copyList(dicts,DictVO.class);
    }


    @ApiOperation(value="增加字典",notes = "具体码写死请参考数据库")
    @ApiImplicitParam(paramType="path", name = "code", value = "字典码", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:dict:inset')")
    @PostMapping
    public DictVO save(@RequestBody DictBO1 dictBO){
        Dict dict = backDictService.mySave(dictBO);
        return BeanCopyUtils.copyBean(dict,DictVO.class);
    }


    @ApiOperation(value="部分字段修改字典",notes = "具体码写死请参考数据库")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:dict:update')")
    @PatchMapping
    public DictVO update(@RequestBody DictBO1 dictBO){
        Dict dict = backDictService.myUpdate(dictBO);
        return BeanCopyUtils.copyBean(dict,DictVO.class);
    }




}
