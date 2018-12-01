package com.baomidou.springboot.im.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.springboot.content.entity.SuperEntity;
import lombok.Data;

/**
 * Description:
 *
 * @Author lht
 * @Date 2018/12/1 下午6:56
 **/
@Data
@TableName("im_friend")
public class Friend extends SuperEntity<Friend>
{


    /**
     * 用户
     */
    private String userId;


    /**
     * 好友
     */
    private String friendId;
}
