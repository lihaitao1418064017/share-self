package com.baomidou.springboot.controller;

import com.baomidou.springboot.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @Description CurrentUserController
 * @Author LiHaitao
 * @Date 2018/8/23 18:04
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
public class CurrentUserController {

    /**
     * 登录用户名
     */
    public String getCurrentLoginUsername() {
        Subject currentUser = SecurityUtils.getSubject();
        User user = currentUser.getPrincipals().oneByType(User.class);
        return user.getName();
    }

    /**
     * 登陆用户id
     * @return
     */
    public Long getCurrentLoginId(){
        Subject currentUser = SecurityUtils.getSubject();
        User user = currentUser.getPrincipals().oneByType(User.class);
        return user.getId();
    }

    /**
     * 登录用户对象
     */
    public User getCurrentUser() {
        Subject currentUser = SecurityUtils.getSubject();
        User user = currentUser.getPrincipals().oneByType(User.class);
        return user;
    }

}
