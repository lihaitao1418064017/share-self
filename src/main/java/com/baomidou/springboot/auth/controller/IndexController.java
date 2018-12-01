package com.baomidou.springboot.auth.controller;

import com.baomidou.springboot.common.ConstantsPub;
import com.baomidou.springboot.auth.entity.User;
import com.baomidou.springboot.content.entity.enums.UserRoleEnum;
import com.baomidou.springboot.controller.CurrentUserController;
import com.baomidou.springboot.response.ResponseMessage;
import com.baomidou.springboot.auth.service.IUserService;
import com.baomidou.springboot.auth.vo.UserVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description IndexController:登录注册接口
 * @Author LiHaitao
 * @Date 2018/8/23 15:08
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
@RequestMapping("/index")
@RestController
public class IndexController extends CurrentUserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/register")
    public ResponseMessage reg(@RequestBody UserVO userVO) {
        Md5Hash md5Hash=new Md5Hash(userVO.getPassword(), ConstantsPub.salt);
        userVO.setPassword(md5Hash.toString());
        return ResponseMessage.ok(userService.insert(modelToEntity(userVO)));
    }

    @RequestMapping("/login")
    public ResponseMessage login(String name,String password,String code,String rememberMe){
        UsernamePasswordToken token=new UsernamePasswordToken(name,password);
        Subject subject= SecurityUtils.getSubject();
        token.setRememberMe(rememberMe==null?false:true);
        try{
            subject.login(token);
        }catch (AuthenticationException e){
            String simpleName = e.getClass().getSimpleName();
            if ("UnknownAccountException".equals(simpleName)) {
                return ResponseMessage.error("The user does not exist");
            } else if("IncorrectCredentialsException".equals(simpleName)){
               return ResponseMessage.error("Incorrect password");
            }
        }
        boolean authenticated = subject.isAuthenticated();
        if (authenticated) {
            return ResponseMessage.ok("Login successfully");
        }
        return ResponseMessage.error("Login failure");

    }
    @RequestMapping("/logout")
    public ResponseMessage logout() {
        // 注销登录
        SecurityUtils.getSubject().logout();
        return ResponseMessage.ok();
    }


    private User modelToEntity(UserVO vo){
        User user=new User();
        user.setId(vo.getId());
        user.setAge(vo.getAge());
        user.setName(vo.getName());
        user.setBirthday(vo.getBirthday());
        user.setHeadshot(vo.getHeadshot());
        user.setPassword(vo.getPassword());
        user.setEmail(vo.getEmail());
        user.setNickname(vo.getNickname());
        user.setPhone(vo.getPhone());
        user.setRole(UserRoleEnum.getByRoleName(vo.getRole()));
        user.setSignature(vo.getSignature());
        user.setAddress(vo.getAddress());
        user.setLove(vo.getLove());
        user.setArticleSum(vo.getArticleSum());
        user.setFocus(vo.getFocus());
        return user;
    }

}