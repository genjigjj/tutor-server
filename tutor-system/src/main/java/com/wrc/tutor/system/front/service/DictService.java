package com.wrc.tutor.system.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrc.tutor.common.entity.po.Dict;
import com.wrc.tutor.common.entity.po.DictType;
import com.wrc.tutor.common.mapper.DictMapper;
import com.wrc.tutor.common.mapper.DictTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DictService extends ServiceImpl<DictMapper, Dict> {

    @Autowired
    DictTypeMapper dictTypeMapper;

    public List<Dict> listByCode(String code) {
        DictType condition = new DictType();
        condition.setCode(code);
        DictType dictType = dictTypeMapper.selectOne(new QueryWrapper<>(condition));

        if(dictType==null){
            return new ArrayList<>(0);
        }

        Dict condition2 = new Dict();
        condition2.setDictTypeId(dictType.getId());

        return getBaseMapper().selectList(new QueryWrapper<>(condition2));
    }
}
