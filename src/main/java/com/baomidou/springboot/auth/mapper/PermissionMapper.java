package com.baomidou.springboot.auth.mapper;


import com.baomidou.springboot.SuperMapper;
import com.baomidou.springboot.auth.entity.Permission;

import javax.annotation.Resource;
import java.util.List;

/**
* @Description:
* @Author:         LiHaitao
* @CreateDate:     2018/8/20 11:24
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0.0
*/
public interface PermissionMapper extends SuperMapper<Permission> {


    List<Permission> findPermissionByUser(Long id);



}