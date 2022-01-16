package com.wrc.tutor.business.back.controller;

import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.business.back.entity.bo.NeedBO1;
import com.wrc.tutor.business.back.entity.query.NeedQuery1;
import com.wrc.tutor.business.back.entity.vo.NeedVO;
import com.wrc.tutor.business.back.service.BackNeedService;
import com.wrc.tutor.common.entity.dto.NeedDTO;
import com.wrc.tutor.common.entity.query.PageQuery;
import com.wrc.tutor.common.entity.vo.MyPage;
import io.swagger.annotations.Api;
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

@RestController
@RequestMapping("/business/back/needs")
@Api("需求接口")
public class BackNeedController {

    @Autowired
    BackNeedService backNeedService;

    @ApiOperation(value="分页需求列表",notes="可以带查询条件")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:need:select')")
    @GetMapping("page")
    public MyPage<NeedVO> page(PageQuery pageQuery, NeedQuery1 needQuery){
        MyPage<NeedDTO> myPage = backNeedService.pageDTO(pageQuery,needQuery);
        List<NeedDTO> records = myPage.getRecords();

        List<NeedVO> needVOS = BeanCopyUtils.copyList(records, NeedVO.class);
        MyPage<NeedVO> myPage1 = BeanCopyUtils.copyBean(myPage,MyPage.class);

        myPage1.setRecords(needVOS);

        return myPage1;
    }


    @ApiOperation(value="审核需求",notes="")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @PreAuthorize("hasAuthority('business:need:update')")
    @PatchMapping("{id}/audit")
    public void   audit(@PathVariable("id") Long id, @RequestBody NeedBO1 needBO){
        backNeedService.audit(id, needBO);
    }

}
