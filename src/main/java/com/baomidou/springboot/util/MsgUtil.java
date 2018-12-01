package com.baomidou.springboot.util;

import cn.hutool.json.JSONUtil;
import com.baomidou.springboot.im.entity.Msg;
import com.baomidou.springboot.config.ServerConfig;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.utils.lock.SetWithLock;
import org.tio.websocket.common.WsResponse;


import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
* @Description:    消息工具类
* @Author:         LiHaitao
* @CreateDate:     2018/8/14 22:06
* @UpdateUser:
* @UpdateDate:     2018/8/14 22:06
* @UpdateRemark:
* @Version:        1.0.0
*/

public class MsgUtil {

    public static boolean existsUser(String userId,ChannelContext channelContext) {
        SetWithLock<ChannelContext> set = channelContext.getGroupContext().users.find(channelContext.getGroupContext(),userId);
//      SetWithLock<ChannelContext> set = Aio.getChannelContextsByUserid(channelContext.getGroupContext(),userId);/**检查用户是否在线*/
        if(set == null || set.size() < 1) {
            return false;
        }
        return true;
    }
    /**
     * 发送到指定用户
     * @param userId
     * @param message
     */
    public static void sendToUser(String userId, Msg message, ChannelContext channelContexta) {
        SetWithLock<ChannelContext> toChannleContexts =channelContexta.getGroupContext().users.find(channelContexta.getGroupContext(), userId);
        if(toChannleContexts == null || toChannleContexts.size() < 1) {
            return;
        }
        ReentrantReadWriteLock.ReadLock readLock = toChannleContexts.getLock().readLock();
        readLock.lock();
        try{
            Set<ChannelContext> channels = toChannleContexts.getObj();
            for(ChannelContext channelContext : channels){
                send(channelContext, message);
            }
        }finally{
            readLock.unlock();
        }
    }

    /**
     * 功能描述：[发送到群组(所有不同协议端)]
     * @param group
     * @param msg
     */
    public static void sendToGroup(String group, Msg msg,ChannelContext channelContexta){
        if(msg == null) {
            return;
        }
        SetWithLock<ChannelContext> withLockChannels = channelContexta.getGroupContext().users.find(channelContexta.getGroupContext(),group);
        if(withLockChannels == null) {
            return;
        }
        ReentrantReadWriteLock.ReadLock readLock = withLockChannels.getLock().readLock();
        readLock.lock();
        try{
            Set<ChannelContext> channels = withLockChannels.getObj();
            if(channels != null && channels.size() > 0){
                for(ChannelContext channelContext : channels){
                    send(channelContext,msg);
                }
            }
        }finally{
            readLock.unlock();
        }
    }

    /**
     * 发送到指定通道;
     * @param channelContext
     * @param msg
     */
    public static void send(ChannelContext channelContext, Msg msg){
        if(channelContext == null) {
            return;
        }
        WsResponse response = WsResponse.fromText(JSONUtil.toJsonStr(msg), ServerConfig.CHARSET);
        Aio.sendToId(channelContext.getGroupContext(), channelContext.getId(), response);
    }
}
