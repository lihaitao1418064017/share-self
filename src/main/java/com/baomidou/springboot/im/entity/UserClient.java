package com.baomidou.springboot.im.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.springboot.content.entity.SuperEntity;
import lombok.Data;
import org.jim.common.packets.Group;
import org.jim.common.packets.User;

import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * Description:用户创建的群组
 *
 * @Author lht
 * @Date 2018/11/26 下午9:43
 **/
@Data
@TableName("im_user")
public class UserClient extends SuperEntity<UserClient> {

    private String nick;
    private String avatar;
    private String status;
    private String sign;
    private String terminal;

    @TableField(exist = false)
    private List<GroupClient> friends;
    @TableField(exist = false)
    private List<GroupClient> groups;

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

    /**
     * 用户好友
     */
    @TableField(exist = false)
    private List<Friend> friendList;

}
