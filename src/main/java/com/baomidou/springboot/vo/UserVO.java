package com.baomidou.springboot.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserVO {

    private Long id;
    /**
     * 名称
     */
    private String name;
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
     *个性签名
     */
    private String signature;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 电话
     */
    private String phone;


    private String role;


    /**
     * 喜欢
     */
    private Integer love;

    /**
     * 关注
     */
    private Integer focus;

    /**
     * 文章数量
     */
    private Integer articleSum;


    /**
     * 籍贯
     */
    private String address;
}
