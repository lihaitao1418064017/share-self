package com.baomidou.springboot.im.vo;

import com.baomidou.springboot.im.entity.UserClient;
import lombok.Data;

/**
 * Description:
 *
 * @Author lht
 * @Date 2018/12/8 下午6:24
 **/
@Data
public class FriendVO {

    private Long id;
    private String user;

    private UserClient friend;


}
