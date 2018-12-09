package com.baomidou.springboot.im.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.springboot.im.entity.GroupUser;
import com.baomidou.springboot.im.mapper.GroupUserMapper;
import com.baomidou.springboot.im.service.IGroupUserService;
import com.baomidou.springboot.im.vo.GroupUserVO;
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

    @Override
    public List<GroupUserVO> selectGroupsByUserId(Long userId) {

        return baseMapper.selectGroupsByUserId(userId);
    }
}