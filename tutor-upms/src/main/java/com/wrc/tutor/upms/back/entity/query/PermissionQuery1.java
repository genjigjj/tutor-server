package com.wrc.tutor.upms.back.entity.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wrc.tutor.common.entity.po.Permission;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author wrc
 * @since 2019-11-03
 */
@Data
public class PermissionQuery1 implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    private Long parentId;

    private String name;

    private String value;

    private String uri;

    private String icon;

    private Integer type;

    private Long weight;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public QueryWrapper<Permission> buildQueryWrapper(){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();

        if(StringUtils.isNoneBlank(getName())){
            queryWrapper.like("name",getName());
        }

//        if(StringUtils.isNoneBlank(title)){
//            queryWrapper.or();
//            queryWrapper.like("title",title);
//        }

        return queryWrapper;
    }


}
