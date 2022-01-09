package com.wrc.tutor.pay.feign.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.common.entity.bo.CashoutBO;
import com.wrc.tutor.common.entity.po.Cashout;
import com.wrc.tutor.pay.auth.service.alipay.AlipayService;
import com.wrc.tutor.pay.common.mapper.CashoutMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeignCashoutService extends ServiceImpl<CashoutMapper, Cashout> {

    @Autowired
    AlipayService alipayService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    public Cashout saveCashout(CashoutBO cashoutBO) {
        Cashout cashout = BeanCopyUtils.copyBean(cashoutBO, Cashout.class);
        getBaseMapper().insert(cashout);
        return cashout;
    }
}
