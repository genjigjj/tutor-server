package com.wrc.tutor.upms.back.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author wrc
 * @since 2019-11-03
 */
@Data
public class RolePermissionVO implements Serializable {

    private static final long serialVersionUID=1L;

    private Long id;

    private Long roleId;

    private Long permissionId;


}
