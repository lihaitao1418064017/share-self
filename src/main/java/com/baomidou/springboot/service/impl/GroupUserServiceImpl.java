package com.baomidou.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.springboot.domain.GroupUser;
import com.baomidou.springboot.domain.dto.Apply;
import com.baomidou.springboot.domain.dto.ApplyType;
import com.baomidou.springboot.mapper.ApplyMapper;
import com.baomidou.springboot.mapper.GroupUserMapper;
import com.baomidou.springboot.service.IApplyService;
import com.baomidou.springboot.service.IGroupUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

import java.util.List;

/**
* @Description:
* @Author:         LiHaitao
* @CreateDate:     2018/9/27 9:46
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0.0
*/
@Service
@Slf4j
public class GroupUserServiceImpl extends ServiceImpl<GroupUserMapper, GroupUser> implements IGroupUserService {

    @Autowired
    private IGroupUserService groupUserService;

    @Override
    public void bingAllGroup(String userId, ChannelContext channelContext) {
        QueryWrapper<GroupUser> queryWrapper=new QueryWrapper<GroupUser>();
        queryWrapper.eq("user_id",userId);
        List<GroupUser> list=groupUserService.selectList(queryWrapper);
        if (list.isEmpty()){
            log.info("该用户没有群组："+userId);
            return;
        }
        list.forEach(groupUser -> Aio.bindGroup(channelContext,groupUser.getGroupId()));
        log.info("绑定群组通道完成："+list.size());
    }
}