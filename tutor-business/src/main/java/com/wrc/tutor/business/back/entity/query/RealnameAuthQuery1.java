package com.wrc.tutor.business.back.entity.query;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 实名认证
 * </p>
 *
 * @author wrc
 * @since 2020-01-25
 */
@Data
public class RealnameAuthQuery1 implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 身份证正面图片
     */
    private String image1;

    /**
     * 身份证反面图片
     */
    private String image2;

    /**
     * 老师id_外键(用户)
     */
    private Long teacherId;

    /**
     * 状态 1未审核, 2审核通过 3审核不通过
     */
    private Integer state;

    /**
     * 审核失败原因
     */
    private String reason;

    /**
     * 排序
     */
    private Long weight;

    /**
     * 状态 0禁用 1启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
