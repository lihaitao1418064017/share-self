package com.baomidou.springboot.domain.dto;

import com.baomidou.springboot.domain.User;
import lombok.Data;

import java.util.List;

/**
 * @Description FriendGroupDTO:用户群组
 * @Author LiHaitao
 * @Date 2018/9/21 13:30
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
@Data
public class FriendGroupDTO {

    /**
     * 用户,此群组属于哪个用户
     */
    private Long userId;


    /**
     * 群名称
     */
    private String groupName;

    /**
     * 群介绍
     */
    private String groupIntroduce;

    /**
     * 群头像
     */
    private String groupAvatar;


    /**
     * 群内人员
     */
    private List<User> userList;

}
