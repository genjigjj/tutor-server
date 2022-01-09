package com.wrc.tutor.upms.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrc.tutor.common.entity.po.Route;
import com.wrc.tutor.common.mapper.RouteMapper;
import com.wrc.tutor.upms.front.entity.query.RouteQuery1;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wrc
 * @since 2019-08-23
 */
@Service
@Slf4j
public class RouteService extends ServiceImpl<RouteMapper, Route>{

    public List<Route> listRoutes(RouteQuery1 routeQuery) {
        return getBaseMapper().selectList(new QueryWrapper<>());
    }
}
