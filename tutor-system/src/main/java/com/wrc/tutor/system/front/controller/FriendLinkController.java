package com.wrc.tutor.system.front.controller;


import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.common.entity.po.FriendLink;
import com.wrc.tutor.system.front.entity.query.FriendLinkQuery1;
import com.wrc.tutor.system.front.entity.vo.FriendLinkVO;
import com.wrc.tutor.system.front.service.FriendLinkService;
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
@RequestMapping("/system/friendLinks")
@Api("友情链接接口")
public class FriendLinkController {

    @Autowired
    FriendLinkService friendLinkService;

    @ApiOperation(value="获取友情链接列表",notes="可以带查询条件")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping
    public List<FriendLinkVO> list(FriendLinkQuery1 friendLinkQuery){
        List<FriendLink> friendLinks = friendLinkService.myList(friendLinkQuery);
        return BeanCopyUtils.copyList(friendLinks,FriendLinkVO.class);
    }

}
