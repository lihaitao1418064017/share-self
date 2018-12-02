package com.baomidou.springboot.im.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.springboot.im.entity.UserClient;
import com.baomidou.springboot.im.service.IUserClientService;
import com.baomidou.springboot.response.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 *
 * @Author lht
 * @Date 2018/12/1 下午9:05
 **/
@RestController
@RequestMapping("/userclient")
public class UserClientController {

    @Autowired
    private IUserClientService userClientService;

    @RequestMapping("/login")
    public ResponseMessage<UserClient> login(@RequestParam String loginname, @RequestParam String password){
        QueryWrapper<UserClient> queryWrapper=new QueryWrapper();
        queryWrapper.eq("loginname",loginname);
        queryWrapper.eq("password",password);
        UserClient userClient=userClientService.selectOne(queryWrapper);
        if (userClient!=null){
            return ResponseMessage.ok(userClient);
        }
        return ResponseMessage.error("login failure");
    }

    @PostMapping("/add")
    public ResponseMessage<Boolean> login(@RequestBody UserClient userClient){


        return ResponseMessage.ok(userClientService.insert(userClient));
    }
}
