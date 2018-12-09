package com.baomidou.springboot.im.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.springboot.im.entity.GroupUser;
import com.baomidou.springboot.im.vo.GroupUserVO;
import org.tio.core.ChannelContext;

import java.util.List;

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

    List<GroupUserVO> selectGroupsByUserId(Long userId);
}