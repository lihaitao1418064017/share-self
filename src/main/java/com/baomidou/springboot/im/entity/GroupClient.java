package com.baomidou.springboot.im.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.springboot.content.entity.SuperEntity;
import lombok.Data;

import java.util.List;

/**
 * Description:用户创建的群组
 *
 * @Author lht
 * @Date 2018/11/26 下午9:43
 **/
@Data
@TableName("im_group")
public class GroupClient extends SuperEntity<GroupClient> {

    private String group_id;
    private String name;
    private String avatar;
    private Integer online;
    @TableField(exist = false)
    private List<UserClient> users;

    protected Long createTime;
    protected Integer cmd;
    /**
     * 群主
     */
    private String groupOwner;


}
