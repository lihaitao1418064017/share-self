package com.baomidou.springboot.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.springboot.content.entity.SuperEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Description Role:角色
 * @Author LiHaitao
 * @Date 2018/8/19 20:06
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
@Data
@TableName("role")
public class Role extends SuperEntity<Role> {


    /**
     * 角色名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 状态: 0：禁用；1：正常
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;


}
