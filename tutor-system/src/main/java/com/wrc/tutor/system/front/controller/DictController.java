package com.wrc.tutor.system.front.controller;


import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.common.entity.po.Dict;
import com.wrc.tutor.system.front.entity.vo.DictVO;
import com.wrc.tutor.system.front.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/dicts")
@Api("字典接口")
public class DictController {

    @Autowired
    DictService dictService;

    @ApiOperation(value="根据字典码获取字典列表",notes = "具体码写死请参考数据库")
    @ApiImplicitParam(paramType="path", name = "code", value = "字典码", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping("code/{code}")
    public List<DictVO> listByCode(@PathVariable("code") String code){
        List<Dict> dicts = dictService.listByCode(code);
        return BeanCopyUtils.copyList(dicts,DictVO.class);
    }

}
