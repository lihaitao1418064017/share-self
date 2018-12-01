package com.baomidou.springboot.im.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.List;

/**
 * @Description FriendGroup:群组
 * @Author LiHaitao
 * @Date 2018/9/18 15:56
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
@Data
@TableName("friend_group")
public class FriendGroup {

    private String id;

    /**
     * 群主，创建者
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
     * 群标识码
     */
    private String code;




}
