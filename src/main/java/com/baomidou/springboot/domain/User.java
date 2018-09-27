package com.baomidou.springboot.domain;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.springboot.domain.enums.UserRoleEnum;
import lombok.Data;

/**
 * 用户表
 */
@Data
@TableName("user")
@SuppressWarnings("serial")
public class User extends SuperEntity<User> {

    /**
     * 姓名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 头像
     */
    private String headshot;
    /**
     * 个性签名
     */
    private String signature;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户角色
     */
    private UserRoleEnum role;
    /**
     * 喜欢的内容
     */
    private Integer love;
    /**
     * 关注
     */
    private Integer focus;
    /**
     * 文章总数
     */
    private Integer articleSum;
    /**
     * 籍贯
     */
    private String address;
    /**
     * 邮箱
     */
    private String email;




}
