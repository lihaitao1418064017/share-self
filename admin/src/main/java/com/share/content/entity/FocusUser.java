package com.share.content.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description 用户关注的人
 * @Author LiHaitao
 * @Date 2018/8/16 10:30
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
@Data
@TableName("focus_user")
public class FocusUser extends SuperEntity<FocusUser> {

    /**
     * 关注人
     */
    private Long userId;

    /**
     * 被关注人
     */
    private Long focusUserId;
}

