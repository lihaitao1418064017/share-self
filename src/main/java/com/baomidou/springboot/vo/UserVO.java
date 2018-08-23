package com.baomidou.springboot.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class UserVO {

    private Long id;
    /**
     * 名称
     */
    @NotBlank(message = "The name cannot be empty")
    @Length(min=5, max=20, message="Names are between 5 and 20 in length")
    private String name;
    /**
     * 年龄
     */
    private Integer age;

    /**
     * 生日
     */
    @DateTimeFormat
    private Date birthday;


    /**
     * 头像:后期加上系统分配的默认头像
     */

    private String headshot;
    /**
     *个性签名
     */
    private String signature;
    /**
     * 昵称
     */
    @NotBlank(message = "The nickname cannot be empty")
    private String nickname;

    /**
     * 电话
     */
    @NotBlank(message = "The phone cannot be empty")
    @Length(min = 11,max = 11,message = "The phone number must be 11 digits long")
    @Size(min = 0,max = 9)
    private String phone;
    /**
     * 密码
     */
    @NotNull(message = "The password cannot be empty")
    @Length(max = 30,min = 11,message = "Names are between 11 and 30 in length")
    private String password;

    /**
     * 邮箱
     */
    @NotBlank(message = "The email cannot be empty")
    private String email;
    /**
     * 会员角色
     */
    private String role;
    /**
     * 喜欢
     */
    private Integer love=0;

    /**
     * 关注
     */
    private Integer focus=0;

    /**
     * 文章数量
     */
    private Integer articleSum=0;
    /**
     * 籍贯
     */
    private String address;
}
