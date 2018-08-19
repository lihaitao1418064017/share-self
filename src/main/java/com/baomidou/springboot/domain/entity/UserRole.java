package com.baomidou.springboot.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.springboot.domain.SuperEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Description UserRole：用户角色
 * @Author LiHaitao
 * @Date 2018/8/19 20:11
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
@Data
@TableName("user_role")
public class UserRole extends SuperEntity<UserRole>{

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;
}
