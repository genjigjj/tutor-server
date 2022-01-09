package com.wrc.tutor.common.entity.dto;

import com.wrc.tutor.common.entity.po.Area;
import com.wrc.tutor.common.entity.po.Category;
import com.wrc.tutor.common.entity.po.Comment;
import com.wrc.tutor.common.entity.po.Dict;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class NeedDTO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    private Long id;

    /**
     * 称呼 详情页展示 如张同学 必选
     */
    private String nickname;

    /**
     * 联系电话 教员中标后才展示,详情页面不展示 必选
     */
    private String phone;

    /**
     * 微信号 教员中标后才展示,详情页面不展示 可选
     */
    private String wechat;

    /**
     * QQ 教员中标后才展示,详情页面不展示 可选
     */
    private String qq;

    /**
     * 性别 1男 2女 详情页展示 必选
     */
    private Integer gender;

    /**
     * 教师性别要求 1男 2女 3不限 搜索条件 必选
     */
    private Integer teacherGender;

    /**
     * 状态 1审核中 2审核不通过 3审核通过 4已选定 5已完成 6已关闭
     */
    private Integer state;

    /**
     * 家长id_外键(用户)
     */
    private Long studentId;

    /**
     * 一级分类_外键 搜索条件 如小学 必选
     */
//    private Long firstCategoryId;
    private Category firstCategory;

    /**
     * 二级分类_外键 搜索条件 如一年级 必选
     */
//    private Long secondCategoryId;
    private Category secondCategory;

    /**
     * 三级分类_外键 搜索条件 如数学 必选
     */
//    private Long thirdCategoryId;
    private Category thirdCategory;

    /**
     * 城市_外键,搜索条件 必选
     */
//    private Long cityId;
    private Area city;

    /**
     * 地区_外键,搜索条件 必选
     */
//    private Long areaId;
    private Area area;

    /**
     * 学员类型_外键  字典表 如拔尖型 必选
     */
//    private Long studentTypeId;
    private Dict studentType;

    /**
     * 教师类型_外键 字典表 如在读大学生 必选
     */
//    private Long teacherTypeId;
    private Dict teacherType;

    /**
     * 老师评论id_外键
     */
//    private Long teacherCommentId;
    private Comment teacherComment;

    /**
     * 家长评论id_外键
     */
//    private Long studentCommentId;
    private Comment studentComment;

    /**
     * 详细地址 详情页展示 必选
     */
    private String address;

    /**
     * 其他要求 可选
     */
    private String demand;

    /**
     * 可上课时间, 序列化后的对象 必选
     */
    private String teachDate;

    /**
     * 预约总人数
     */
    private Long totalAppoint;

    /**
     *  上课次数 必选
     */
    private Long frequency;

    /**
     *  每次上课小时 必选
     */
    private Long timeHour;

    /**
     *  每小时价格 必选
     */
    private BigDecimal hourPrice;

    /**
     * 总价格
     */
    private BigDecimal totalPrice;

    /**
     * 关闭原因
     */
    private String reason;

    /**
     * 排序
     */
    private Long weight;

    /**
     * 家长是否已删除(软)
     */
    private Integer deleted;

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
