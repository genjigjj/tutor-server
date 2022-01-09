package com.wrc.tutor.pay.auth.controller;

import com.wrc.tutor.common.entity.query.PageQuery;
import com.wrc.tutor.common.entity.vo.MyPage;
import com.wrc.tutor.pay.auth.entity.query.PayInfoQuery1;
import com.wrc.tutor.pay.auth.entity.vo.PayInfoVO;
import com.wrc.tutor.pay.auth.service.AuthPayInfoService;
import com.wrc.tutor.common.entity.po.PayInfo;
import com.tuyang.beanutils.BeanCopyUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("auth/payInfos/me")
public class AuthPayInfoController {

    @Autowired
    AuthPayInfoService authPayInfoService;


    @ApiOperation(value="分页获取我的支付信息")
    @GetMapping("page")
    public MyPage<PayInfoVO> pageAppoint(PageQuery pageQuery, PayInfoQuery1 payInfoQuery){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long id = Long.valueOf((String)authentication.getPrincipal());


        MyPage<PayInfo> myPage = authPayInfoService.myPage(id,pageQuery,payInfoQuery);
        List<PayInfo> records = myPage.getRecords();

        List<PayInfoVO> voS = BeanCopyUtils.copyList(records, PayInfoVO.class);
        MyPage<PayInfoVO> myPage1 = BeanCopyUtils.copyBean(myPage, MyPage.class);

        myPage1.setRecords(voS);

        return myPage1;
    }
}
