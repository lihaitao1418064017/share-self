package com.baomidou.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.springboot.domain.entity.RolePermission;
import com.baomidou.springboot.response.ResponseMessage;
import com.baomidou.springboot.service.IRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* @Description:    RolePermissionController：角色权限前端控制器
* @Author:         LiHaitao
* @CreateDate:     2018/8/20 14:26
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0.0
*/
@RestController
@RequestMapping("/role_permission")
public class RolePermissionController extends ApiController {

    @Autowired
    private IRolePermissionService rolePermissionService;

    @GetMapping("/get")
    public ResponseMessage<List<RolePermission>> get(@RequestParam Long id){
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("role_id",id);
        return ResponseMessage.ok(rolePermissionService.selectList(queryWrapper));
    }
}
