package com.wrc.tutor.system.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrc.tutor.common.entity.po.FriendLink;
import com.wrc.tutor.common.mapper.FriendLinkMapper;
import com.wrc.tutor.system.front.entity.query.FriendLinkQuery1;
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
public class FriendLinkService extends ServiceImpl<FriendLinkMapper, FriendLink> {

    public List<FriendLink> myList(FriendLinkQuery1 friendLinkQuery) {
        QueryWrapper<FriendLink> queryWrapper = new QueryWrapper<>();

        return getBaseMapper().selectList(queryWrapper);
    }
}
