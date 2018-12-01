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
@TableName("im_group")
public class Group extends SuperEntity<Group> {


    /**
     * 群id
     */
    private String groupId;
    /**
     * 群组名
     */
    private String groupName;
    /**
     * 群组头像
     */
    private String groupHeadShot;
    /**
     * 备注
     */
    private String extras;
    /**
     * 群主
     */
    private String groupOwner;


}
