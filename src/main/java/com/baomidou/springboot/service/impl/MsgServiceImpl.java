package com.baomidou.springboot.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.springboot.common.ConstantsPub;
import com.baomidou.springboot.domain.FriendGroup;
import com.baomidou.springboot.domain.GroupUser;
import com.baomidou.springboot.domain.dto.Msg;
import com.baomidou.springboot.mapper.MsgMapper;
import com.baomidou.springboot.service.IFriendGroupService;
import com.baomidou.springboot.service.IGroupUserService;
import com.baomidou.springboot.service.IMsgService;
import com.baomidou.springboot.util.IDGenerator;
import com.baomidou.springboot.util.MsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

import javax.xml.ws.Action;
import java.util.List;

/**
* @Description:
* @Author:         LiHaitao
* @CreateDate:     2018/9/14 15:32
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0.0
*/
@Service
@Slf4j
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg> implements IMsgService {

    @Autowired
    private IMsgService msgService;
    @Autowired
    private IFriendGroupService friendGroupService;

    @Autowired
    private IGroupUserService groupUserService;

    @Override
    public Boolean sendOfflineMessage(ChannelContext channelContext,String userId) {
        /**当用户上线建立连接后首先判读是否有消息，如果有的话，发送*/
        QueryWrapper<Msg> queryWrapper=new QueryWrapper<Msg>();
        queryWrapper.eq("to",userId);
        queryWrapper.eq("type", ConstantsPub.PERSON_MSG);
        queryWrapper.orderByAsc("time");
        List<Msg> msgs=msgService.selectList(queryWrapper);
        if (msgs.isEmpty()){
            log.info("该用户没有离线消息："+userId);
        }
        msgs.forEach(msg -> MsgUtil.sendToUser(userId,msg,channelContext));
        log.info("发送离线消息");
        return  msgService.delete(queryWrapper);
    }

    @Override
    public Boolean sendOnlineMessage(ChannelContext channelContext,String text) {
        String jsonStr = JSONUtil.toJsonStr(text);
        Msg msg= JSON.parseObject(jsonStr,Msg.class);
        msg.setTime(System.currentTimeMillis());
        log.info("消息对象：{}"+msg.toString());

        int type=msg.getType();
        switch (type) {
            case ConstantsPub.PERSON_MSG: {/**点对点消息*/
                personToPerson(channelContext, msg);
            }
            break;
            case ConstantsPub.GROUP_MSG: {/**群发消息*/
                /**1，群组存在*/
                MsgUtil.sendToGroup(msg.getGroupId(), msg, channelContext);
            }
            break;
            case ConstantsPub.APPLY_GROUP_MSG: {/**添加群组*/
                applyGroup(channelContext);
            }
            case ConstantsPub.APPLY_PERSON_MSG: {/**添加群成员*/
                applyPerson(channelContext, msg);
            }
            break;
            default: {
                log.info("消息类型不明确");
                return false;
            }

        }

        return true;
    }

    private void applyPerson(ChannelContext channelContext, Msg msg) {
        GroupUser groupUser = new GroupUser();
        groupUser.setUserId(Long.parseLong(msg.getTo()));
        groupUser.setGroupId(msg.getGroupId());
        if (groupUserService.insert(groupUser)) {
            MsgUtil.sendToUser(msg.getTo(), msg, channelContext);
        }
    }


    private void applyGroup(ChannelContext channelContext) {
        /**群不存在，*/
        String id = IDGenerator.UUID.generate();
        FriendGroup friendGroup = new FriendGroup();
        friendGroup.setGroupName("群组" + id.substring(1, 5));//群名由用户修改
        friendGroup.setCode(id);
        friendGroup.setId(id);
        /**建立群組*/
        if (friendGroupService.insert(friendGroup)) {
            Aio.bindGroup(channelContext, id);
        }
    }

    private void personToPerson(ChannelContext channelContext, Msg msg) {
        /**判断接受者是否在线*/
        if (MsgUtil.existsUser(msg.getTo(), channelContext)) {
            MsgUtil.sendToUser(msg.getTo(), msg, channelContext);
            log.info("用户在线，直接发送消息给用户");
        } else {/**如果用户不在线，将消息保存到DB中，当用户在线后发送*/
            /**用接受者的id作为标识*/
            msgService.insert(msg);
            log.info("用户不在线，将消息放入DB");
        }
    }
}