package com.wrc.tutor.pay.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.common.entity.bo.FeignTeacherBO1;
import com.wrc.tutor.common.entity.po.Cashin;
import com.wrc.tutor.common.entity.po.Cashout;
import com.wrc.tutor.common.entity.po.Teacher;
import com.wrc.tutor.common.entity.query.PageQuery;
import com.wrc.tutor.common.entity.request.TransferBizContent;
import com.wrc.tutor.common.entity.response.TransferResponse;
import com.wrc.tutor.common.entity.vo.MyPage;
import com.wrc.tutor.common.service.FeignTeacherService;
import com.wrc.tutor.common.util.key.IdWorker;
import com.wrc.tutor.pay.auth.service.alipay.AlipayService;
import com.wrc.tutor.pay.back.entity.bo.CashoutBO1;
import com.wrc.tutor.pay.back.entity.query.CashoutQuery1;
import com.wrc.tutor.common.exception.BusinessException;
import com.wrc.tutor.pay.common.mapper.CashinMapper;
import com.wrc.tutor.pay.common.mapper.CashoutMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 大学生认证 服务实现类
 * </p>
 *
 * @author wrc
 * @since 2020-01-27
 */
@Service
public class BackCashoutService extends ServiceImpl<CashoutMapper, Cashout> {

    @Autowired
    AlipayService alipayService;

    @Autowired
    FeignTeacherService iFeignTeacher;

    @Autowired
    CashinMapper cashinMapper;

    public MyPage<Cashout> myPage(PageQuery pageQuery, CashoutQuery1 cashoutQuery) {
        //      1 将我们的分页对象转换成mybatis plus 的
        Page<Cashout> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<Cashout> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(cashoutQuery.getRealname())) {
            queryWrapper.like("realname", cashoutQuery.getRealname());
        }
        if (cashoutQuery.getState() != null) {
            queryWrapper.eq("state", cashoutQuery.getState());
        }

        //      3调用Mapper
        IPage<Cashout> iPage = getBaseMapper().selectPage(page, queryWrapper);

//        将mybatis plus 分页对象转换成我们的
        MyPage<Cashout> myPage = BeanCopyUtils.copyBean(iPage, MyPage.class);
        myPage.setPages(iPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }

    @Transactional
    public void audit(Long id, CashoutBO1 cashoutBO) {
        Cashout cashout = getBaseMapper().selectById(id);
        if (cashout == null) {
            throw new BusinessException("参数错误");
        }

        if (cashoutBO.getState() == null) {
            throw new BusinessException("参数错误");
        }
        if (cashoutBO.getState().compareTo(3) == 0) {

            cashout.setState(3); //审核通过

            // 提交支付宝提现请求
            TransferBizContent transferBizContent = new TransferBizContent();
            transferBizContent.setOutBizNo(IdWorker.getTimeId()); //商户唯一订单号
            transferBizContent.setTransAmount(cashout.getCash()); //转账金额
            transferBizContent.setOrderTitle("闽师家教提现"); //标题
            transferBizContent.setRemark(cashout.getNote());//备注
            transferBizContent.getPayeeInfo().setIdentity(cashout.getCashoutAccount()); //收款方帐号
            transferBizContent.getPayeeInfo().setName(cashout.getRealname()); //真实姓名
            TransferResponse transfer;
            try {
                transfer = alipayService.transfer(transferBizContent);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException("支付宝配置异常");
            }
            if (!transfer.getAlipayFundTransUniTransferResponse().getCode().equals("10000")) {
                throw new BusinessException(transfer.getAlipayFundTransUniTransferResponse().getSubMsg());
            }

        } else if (cashoutBO.getState().compareTo(2) == 0) {
            cashout.setState(2); //审核失败
            Teacher teacher = iFeignTeacher.getById(cashout.getUserId());
            if (teacher == null) {
                throw new BusinessException("教师信息不存在");
            }
//            新建一条收入信息
            Cashin cashin = new Cashin();
            cashin.setUserId(cashout.getUserId()); //用户id
            cashin.setTotalAmount(cashout.getCash()); //收入金额
            cashin.setBalance(teacher.getBalance().add(cashout.getCash())); //老师余额
            cashin.setName("提现失败返还到余额");
            cashin.setDescription(cashoutBO.getReason());
            cashin.setCteateTime(LocalDateTime.now());
            cashinMapper.insert(cashin);

//            更新老师余额: 原来余额 + 加上提现失败的余额
            teacher.setBalance(teacher.getBalance().add(cashout.getCash()));
            FeignTeacherBO1 feignTeacherBO1 = BeanCopyUtils.copyBean(teacher, FeignTeacherBO1.class);
            iFeignTeacher.patchById(feignTeacherBO1);

            if (StringUtils.isNotBlank(cashoutBO.getReason())) {
                cashout.setReason(cashoutBO.getReason());
            }

        } else {
            throw new BusinessException("参数错误");
        }

        getBaseMapper().updateById(cashout);
    }
}
