package com.wrc.tutor.system.auth.controller;

import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.common.entity.po.Advice;
import com.wrc.tutor.system.auth.entity.bo.AdviceBO1;
import com.wrc.tutor.system.auth.entity.vo.AdviceVO;
import com.wrc.tutor.system.auth.service.AuthAdviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("auth/advices")
@Api("意见建议接口")
public class AuthAdviceController {

    @Autowired
    AuthAdviceService authAdviceService;

    @ApiOperation(value = "新增意见建议", notes = "")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功"),
    })
    @PostMapping
    public AdviceVO save(@RequestBody AdviceBO1 adviceBO) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String) authentication.getPrincipal());
        Advice advice = authAdviceService.mySave(id, adviceBO);
        return BeanCopyUtils.copyBean(advice, AdviceVO.class);
    }

}
