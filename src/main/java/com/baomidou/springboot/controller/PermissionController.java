package com.baomidou.springboot.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.springboot.domain.entity.Permission;
import com.baomidou.springboot.response.ResponseMessage;
import com.baomidou.springboot.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description PermissionController:权限前端控制器
 * @Author LiHaitao
 * @Date 2018/8/20 13:28
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
@RestController
@RequestMapping("/permission")
public class PermissionController extends ApiController {

    @Autowired
    private IPermissionService permissionService;

    @GetMapping("/get")
    public ResponseMessage<List<Permission>> get(){
        return ResponseMessage.ok(permissionService.selectList(null));
    }
}
