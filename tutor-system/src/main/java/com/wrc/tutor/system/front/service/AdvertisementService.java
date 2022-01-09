package com.wrc.tutor.system.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrc.tutor.common.entity.po.Advertisement;
import com.wrc.tutor.common.mapper.AdvertisementMapper;
import com.wrc.tutor.system.front.entity.query.AdvertisementQuery1;
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
public class AdvertisementService extends ServiceImpl<AdvertisementMapper, Advertisement> {

    public List<Advertisement> myList(AdvertisementQuery1 advertisementQuery) {
        QueryWrapper<Advertisement> queryWrapper = new QueryWrapper<>();
        //        TODO 根据业务写wrapper

        return getBaseMapper().selectList(queryWrapper);
    }
}
