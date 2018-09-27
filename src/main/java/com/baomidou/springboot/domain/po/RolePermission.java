package com.baomidou.springboot.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.springboot.domain.SuperEntity;
import lombok.Data;

import java.util.Date;

/**
 * @Description RolePermission：角色权限
 * @Author LiHaitao
 * @Date 2018/8/19 20:15
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
@Data
@TableName("role_permission")
public class RolePermission extends SuperEntity<RolePermission> {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 权限id
     */
    private Long permissionId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifyTime;
}
