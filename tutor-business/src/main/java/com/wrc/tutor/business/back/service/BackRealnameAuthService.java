package com.wrc.tutor.business.back.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wrc.tutor.business.back.entity.bo.RealnameAuthBO1;
import com.wrc.tutor.business.back.entity.query.RealnameAuthQuery1;
import com.wrc.tutor.common.entity.po.RealnameAuth;
import com.wrc.tutor.common.entity.po.Teacher;
import com.wrc.tutor.common.exception.BusinessException;
import com.wrc.tutor.common.mapper.RealnameAuthMapper;
import com.wrc.tutor.common.mapper.TeacherMapper;
import com.wrc.tutor.common.entity.query.PageQuery;
import com.wrc.tutor.common.entity.vo.MyPage;
import com.tuyang.beanutils.BeanCopyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 实名认证 服务实现类
 * </p>
 *
 * @author wrc
 * @since 2020-01-27
 */
@Service
public class BackRealnameAuthService extends ServiceImpl<RealnameAuthMapper, RealnameAuth>{

    @Autowired
    private TeacherMapper teacherMapper;


    public MyPage<RealnameAuth> myPage(PageQuery pageQuery, RealnameAuthQuery1 realnameAuthQuery) {
        //      1 将我们的分页对象转换成mybatis plus 的
        Page<RealnameAuth> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<RealnameAuth> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(realnameAuthQuery.getRealname())){
            queryWrapper.like("realname",realnameAuthQuery.getRealname());
        }
        if(realnameAuthQuery.getState()!=null){
            queryWrapper.eq("state",realnameAuthQuery.getState());
        }

        //      3调用Mapper
        IPage<RealnameAuth> iPage = getBaseMapper().selectPage(page, queryWrapper);


//        将mybatis plus 分页对象转换成我们的
        MyPage<RealnameAuth> myPage = BeanCopyUtils.copyBean(iPage, MyPage.class);
        myPage.setPages(iPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;

    }

    public void audit(Long id, RealnameAuthBO1 realnameAuthBO) {
        RealnameAuth realnameAuth = getBaseMapper().selectById(id);
        if(realnameAuth==null){
            throw new BusinessException("参数错误");
        }

        if(realnameAuthBO.getState()==null){
            throw new BusinessException("参数错误");
        }

        Teacher condition = new Teacher();
        condition.setUserId(realnameAuth.getTeacherId());
        Teacher teacher = teacherMapper.selectOne(new QueryWrapper<>(condition));

        if(realnameAuthBO.getState().compareTo(2)==0){
            realnameAuth.setState(2); //认证失败
            teacher.setRealnameAuth(0); //未认证
            if(StringUtils.isNotBlank(realnameAuthBO.getReason())){
                realnameAuth.setReason(realnameAuthBO.getReason());
            }
        }else if(realnameAuthBO.getState().compareTo(3)==0){
            realnameAuth.setState(3); //认证通过
            teacher.setRealnameAuth(1); //已认证
        }else {
            throw new BusinessException("参数错误");
        }

        teacherMapper.updateById(teacher);

        getBaseMapper().updateById(realnameAuth);
    }
}
