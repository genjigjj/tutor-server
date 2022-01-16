package com.wrc.tutor.system.front.controller;


import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.common.entity.po.Advertisement;
import com.wrc.tutor.system.front.entity.query.AdvertisementQuery1;
import com.wrc.tutor.system.front.entity.vo.AdvertisementVO;
import com.wrc.tutor.system.front.service.AdvertisementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/advertisements")
@Api("大广告接口")
public class AdvertisementController {

    @Autowired
    AdvertisementService advertisementService;

    @ApiOperation(value="获取广告列表",notes="可以带查询条件")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping
    public List<AdvertisementVO> list(AdvertisementQuery1 advertisementQuery){
        List<Advertisement> advertisements = advertisementService.myList(advertisementQuery);
        return BeanCopyUtils.copyList(advertisements,AdvertisementVO.class);
    }

}
