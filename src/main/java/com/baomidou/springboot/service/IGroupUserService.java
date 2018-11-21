package com.baomidou.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.springboot.domain.GroupUser;
import org.tio.core.ChannelContext;

/**
* @Description:
* @Author:         LiHaitao
* @CreateDate:     2018/9/27 9:45
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0.0
*/
public interface IGroupUserService extends IService<GroupUser> {


    /**
     * 为用户绑定所有群组id，以便接受群组消息
     */
    public void bingAllGroup(String userId, ChannelContext channelContext);

}