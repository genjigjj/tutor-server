package com.wrc.tutor.pay.feign.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tuyang.beanutils.BeanCopyUtils;
import com.wrc.tutor.common.entity.bo.CashinBO;
import com.wrc.tutor.common.entity.po.Cashin;
import com.wrc.tutor.pay.auth.service.alipay.AlipayService;
import com.wrc.tutor.pay.common.mapper.CashinMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeignCashinService extends ServiceImpl<CashinMapper, Cashin> {

    @Autowired
    AlipayService alipayService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    public Cashin saveCashin(CashinBO cashinBO) {
        Cashin cashin = BeanCopyUtils.copyBean(cashinBO, Cashin.class);
        getBaseMapper().insert(cashin);
        return cashin;
    }
}
