package com.wrc.tutor.business.auth.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrc.tutor.common.entity.po.TeacherCategoryRelation;
import com.wrc.tutor.common.mapper.TeacherCategoryRelationMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 教师 所教学科 中间表 服务实现类
 * </p>
 *
 * @author wrc
 * @since 2020-01-27
 */
@Service
public class AuthTeacherCategoryRelationService extends ServiceImpl<TeacherCategoryRelationMapper, TeacherCategoryRelation>  {

}
