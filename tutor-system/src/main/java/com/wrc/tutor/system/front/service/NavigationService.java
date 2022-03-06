package com.wrc.tutor.system.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrc.tutor.common.entity.po.Navigation;
import com.wrc.tutor.common.mapper.NavigationMapper;
import com.wrc.tutor.system.front.entity.query.NavigationQuery1;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wrc
 * @since 2020-01-25
 */
@Service
public class NavigationService extends ServiceImpl<NavigationMapper, Navigation> {

    public List<Navigation> myList(NavigationQuery1 navigationQuery) {
        return getBaseMapper().selectList(new QueryWrapper<Navigation>().eq("status", true)
                .and(queryWrapper -> queryWrapper.eq("role", navigationQuery.getRole()).or().eq("role", "all")));
    }
}
