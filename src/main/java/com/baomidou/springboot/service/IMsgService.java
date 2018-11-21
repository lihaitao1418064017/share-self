package com.baomidou.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.springboot.domain.dto.Msg;
import org.tio.core.ChannelContext;


/**
* @Description:
* @Author:         LiHaitao
* @CreateDate:     2018/9/14 15:32
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0.0
*/
public interface IMsgService extends IService<Msg> {

    /**
     * 用户上线后为用户推送离线消息
     */
    public Boolean  sendOfflineMessage(ChannelContext channelContext,String userId);


    /**
     * 在线消息处理
     */
    public Boolean sendOnlineMessage(ChannelContext channelContext,String text);


}