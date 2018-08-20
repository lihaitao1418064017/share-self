package com.baomidou.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.springboot.domain.entity.Permission;

import java.util.List;


/**
* @Description:
* @Author:         LiHaitao
* @CreateDate:     2018/8/20 13:03
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0.0
*/
public interface IPermissionService extends IService<Permission> {

    List<Permission> findUserPermission(Long id);

}