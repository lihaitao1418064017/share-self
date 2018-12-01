package com.baomidou.springboot.test;

import com.baomidou.springboot.auth.entity.User;
import com.baomidou.springboot.util.JsonUtil;
import org.junit.Test;

/**
 * @Description Main:
 * @Author LiHaitao
 * @Date 2018/10/31 13:29
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
public class Main {




    @Test
    public void test(){
        User user=new User();
        user.setEmail("982342487@qq.com");
        user.setName("lihaitao");
        String userJson=JsonUtil.getJsonFromObject(user);
        System.out.println(userJson);
        System.out.println(JsonUtil.getObjectFromJson(userJson,User.class).toString());


    }
}
