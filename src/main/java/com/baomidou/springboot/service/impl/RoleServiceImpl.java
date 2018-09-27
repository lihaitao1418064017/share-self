package com.baomidou.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.springboot.domain.po.Role;
import com.baomidou.springboot.mapper.RoleMapper;
import com.baomidou.springboot.service.IRoleService;
import org.springframework.stereotype.Service;

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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}