package com.wrc.tutor.business.auth.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrc.tutor.common.entity.po.Dict;
import com.wrc.tutor.common.mapper.DictMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author wrc
 * @since 2020-01-27
 */
@Service
public class AuthDictService extends ServiceImpl<DictMapper, Dict> {

}
