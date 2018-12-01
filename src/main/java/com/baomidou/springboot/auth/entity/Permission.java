package com.baomidou.springboot.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.springboot.content.entity.SuperEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Description Permission:权限
 * @Author LiHaitao
 * @Date 2018/8/19 19:56
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
@Data
@TableName("permission")
public class Permission extends SuperEntity<Permission> {


    /**
     * 权限类型
     */
    private Integer type;
    /**
     * 上级id
     */
    private Long pId;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;

    /**
     * url
     */
    private String url;

    /**
     * 描述
     */
    private String description;

    /**
     * 权限编码
     */
    private String permCode;

    /**
     * 图标
     */
    private String icon;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 状态
     */
    private Integer status;


}
