package com.baomidou.springboot.im.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.springboot.im.entity.Friend;
import com.baomidou.springboot.im.mapper.FriendMapper;
import com.baomidou.springboot.im.service.IFriendService;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @Author lht
 * @Date 2018/12/1 下午7:02
 **/
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements IFriendService {
}
