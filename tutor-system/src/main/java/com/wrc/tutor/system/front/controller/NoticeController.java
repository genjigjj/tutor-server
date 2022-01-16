package com.wrc.tutor.system.front.controller;


import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.common.entity.po.Notice;
import com.wrc.tutor.common.entity.query.PageQuery;
import com.wrc.tutor.common.entity.vo.MyPage;
import com.wrc.tutor.system.front.entity.query.NoticeQuery1;
import com.wrc.tutor.system.front.entity.vo.NoticeVO;
import com.wrc.tutor.system.front.service.NoticeService;
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
@RequestMapping("/system/notices")
@Api("公告接口")
public class NoticeController {

    @Autowired
    NoticeService noticeService;

    @ApiOperation(value="分页公告列表",notes="可以带查询条件")
    @ApiResponses({
            @ApiResponse(code=200,message="成功"),
    })
    @GetMapping("page")
    public MyPage<NoticeVO> page(PageQuery pageQuery, NoticeQuery1 noticeQuery){
        MyPage<Notice> myPage = noticeService.myPage(pageQuery,noticeQuery);
        List<Notice> records = myPage.getRecords();

        List<NoticeVO> needVOs = BeanCopyUtils.copyList(records, NoticeVO.class);
        MyPage<NoticeVO> myPage1 = BeanCopyUtils.copyBean(myPage,MyPage.class);

        myPage1.setRecords(needVOs);

        return myPage1;
    }

}
