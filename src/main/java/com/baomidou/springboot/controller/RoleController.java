package com.baomidou.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.springboot.domain.po.Role;
import com.baomidou.springboot.response.ResponseMessage;
import com.baomidou.springboot.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description RoleController:角色前端控制器
 * @Author LiHaitao
 * @Date 2018/8/20 13:29
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
@RestController
@RequestMapping("/role")
public class RoleController extends ApiController {

    @Autowired
    private IRoleService roleService;
    @GetMapping("/get")
    public ResponseMessage<List<Role>> get(){
        return ResponseMessage.ok(roleService.selectList(new QueryWrapper<Role>()));
    }
}
