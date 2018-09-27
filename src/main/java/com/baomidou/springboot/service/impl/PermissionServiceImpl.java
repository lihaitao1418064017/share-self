package com.baomidou.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.springboot.domain.po.Permission;
import com.baomidou.springboot.mapper.PermissionMapper;
import com.baomidou.springboot.service.IPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @Description:
* @Author:         LiHaitao
* @CreateDate:     2018/8/20 13:15
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0.0
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Override
    public List<Permission> findUserPermission(Long id) {
        return baseMapper.findPermissionByUser(id);
    }
}