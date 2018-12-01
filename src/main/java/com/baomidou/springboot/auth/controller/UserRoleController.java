package com.baomidou.springboot.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.springboot.auth.entity.UserRole;
import com.baomidou.springboot.response.ResponseMessage;
import com.baomidou.springboot.auth.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description UserRoleController:
 * @Author LiHaitao
 * @Date 2018/8/20 14:13
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
@RestController
@RequestMapping("/user_role")
public class UserRoleController extends ApiController {

    @Autowired
    private IUserRoleService userRoleService;

    @GetMapping("getByUserId")
    public ResponseMessage<List<UserRole>> get(@RequestParam Long id){
        QueryWrapper<UserRole> queryWrapper=new QueryWrapper<UserRole>();
        queryWrapper.eq("user_id",id);
        return ResponseMessage.ok(userRoleService.selectList(queryWrapper));
    }
}


