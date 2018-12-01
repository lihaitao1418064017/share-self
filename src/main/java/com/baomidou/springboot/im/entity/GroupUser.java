package com.baomidou.springboot.im.entity;

import com.baomidou.springboot.content.entity.SuperEntity;
import lombok.Data;

/**
 * @Description GroupUserMapper:好友和群组
 * @Author LiHaitao
 * @Date 2018/9/21 13:49
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
@Data
public class GroupUser extends SuperEntity<GroupUser> {


    private Long userId;

    private String groupId;

}
