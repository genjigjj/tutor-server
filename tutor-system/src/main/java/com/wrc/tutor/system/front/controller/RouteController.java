package com.wrc.tutor.system.front.controller;


import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.common.entity.po.Route;
import com.wrc.tutor.system.front.entity.query.RouteQuery1;
import com.wrc.tutor.system.front.entity.vo.RouteVO;
import com.wrc.tutor.system.front.service.RouteService;
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
@RequestMapping("/routes")
@Api("后台管理系统路由")
public class RouteController {

    @Autowired
    RouteService routeService;

    @ApiOperation(value="获取路由列表",notes="可以带查询条件")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping
    public List<RouteVO> list(RouteQuery1 routeQuery){
        List<Route> routes = routeService.myList(routeQuery);
        return BeanCopyUtils.copyList(routes,RouteVO.class);
    }

}
