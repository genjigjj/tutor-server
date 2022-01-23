package com.wrc.tutor.business.auth.controller;


import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.business.auth.entity.bo.RealnameAuthBO1;
import com.wrc.tutor.business.auth.entity.vo.RealnameAuthVO;
import com.wrc.tutor.business.auth.service.AuthRealnameAuthService;
import com.wrc.tutor.business.common.exception.ResourceNotFondException;
import com.wrc.tutor.common.entity.po.RealnameAuth;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 实名认证 前端控制器
 * </p>
 *
 * @author wrc
 * @since 2020-01-27
 */
@RestController
@RequestMapping("/business/auth/realnameAuths/me")
public class AuthRealnameAuthController {

    @Autowired
    AuthRealnameAuthService authRealnameAuthService;

    @ApiOperation(value="获取我的实名认证信息")
    @GetMapping
    public RealnameAuthVO getRealnameAuthForTeacher(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        RealnameAuth realnameAuth = null;
        try {
            realnameAuth = authRealnameAuthService.getRealnameAuthForTeacher(id);
        } catch (ResourceNotFondException e) {
            e.printStackTrace();
        }
        return  BeanCopyUtils.copyBean(realnameAuth,RealnameAuthVO.class);
    }

    @PutMapping
    @ApiOperation(value="新增或更新我的实名认证信息")
    public RealnameAuthVO saveOrUpdateRealnameAuthForTeacher(@RequestBody RealnameAuthBO1 realnameAuthBO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());

        RealnameAuth realnameAuth = authRealnameAuthService.saveOrUpdateRealnameAuthForTeacher(id,realnameAuthBO);
        return BeanCopyUtils.copyBean(realnameAuth,RealnameAuthVO.class);
    }

}

