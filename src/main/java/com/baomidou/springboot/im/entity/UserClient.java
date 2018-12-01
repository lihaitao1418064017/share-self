package com.baomidou.springboot.im.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.springboot.content.entity.SuperEntity;
import lombok.Data;

/**
 * Description:用户创建的群组
 *
 * @Author lht
 * @Date 2018/11/26 下午9:43
 **/
@Data
@TableName("im_user")
public class UserClient extends SuperEntity<UserClient> {


    /**
     * 用户id
     */
    private String userId;
    /**
     * 登陆用户名
     */
    private String loginname;
    /**
     * 登陆密码
     */
    private String password;

    /**
     * 令牌
     */
    private String token;

}
