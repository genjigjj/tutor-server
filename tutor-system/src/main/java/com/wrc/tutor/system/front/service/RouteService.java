package com.wrc.tutor.system.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrc.tutor.common.entity.po.Route;
import com.wrc.tutor.common.mapper.RouteMapper;
import com.wrc.tutor.system.front.entity.query.RouteQuery1;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wrc
 * @since 2020-01-25
 */
@Service
public class RouteService extends ServiceImpl<RouteMapper, Route> {

    public List<Route> myList(RouteQuery1 routeQuery) {
        QueryWrapper<Route> queryWrapper = new QueryWrapper<>();
        return getBaseMapper().selectList(queryWrapper);
    }
}
