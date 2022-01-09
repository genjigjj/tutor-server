package com.wrc.tutor.pay.back.controller;

import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.common.entity.po.Cashout;
import com.wrc.tutor.common.entity.query.PageQuery;
import com.wrc.tutor.common.entity.vo.MyPage;
import com.wrc.tutor.pay.back.entity.bo.CashoutBO1;
import com.wrc.tutor.pay.back.entity.query.CashoutQuery1;
import com.wrc.tutor.pay.back.entity.vo.CashoutVO;
import com.wrc.tutor.pay.back.service.BackCashoutService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 大学生认证 前端控制器
 * </p>
 *
 * @author wrc
 * @since 2020-01-27
 */
@RestController
@RequestMapping("back/cashouts")
public class BackCashoutController {

    @Autowired
    BackCashoutService backCashoutService;



    @ApiOperation(value="分页体现列表",notes="可以带查询条件和排序字段")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('pay:cashout:select')")
    @GetMapping("page")
    public MyPage<CashoutVO> page(PageQuery pageQuery, CashoutQuery1 cashoutQuery1){
        MyPage<Cashout> pagePO = backCashoutService.myPage(pageQuery, cashoutQuery1);
        List<Cashout> records = pagePO.getRecords();

//        转换成我们的分页对象
        MyPage<CashoutVO> pageVO = BeanCopyUtils.copyBean(pagePO,MyPage.class);

//        将PO转换成VO
        List<CashoutVO> vos = BeanCopyUtils.copyList(records, CashoutVO.class);
        pageVO.setRecords(vos);
        return pageVO;
    }


    @ApiOperation(value="审核提现",notes="")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('pay:cashout:update')")
    @PatchMapping("{id}/audit")
    public void   audit(@PathVariable("id") Long id, @RequestBody CashoutBO1 cashoutBO){
        backCashoutService.audit(id, cashoutBO);
    }

}

