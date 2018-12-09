package com.baomidou.springboot.im.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.springboot.im.entity.Friend;
import com.baomidou.springboot.im.mapper.FriendMapper;
import com.baomidou.springboot.im.service.IFriendService;
import com.baomidou.springboot.im.vo.FriendVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 *
 * @Author lht
 * @Date 2018/12/1 下午7:02
 **/
@Service
public class FriendServiceImpl extends ServiceImpl<FriendMapper, Friend> implements IFriendService {
    @Override
    public List<FriendVO> selectFriendsByUserId(String userId) {
        return baseMapper.selectFriendsByUserId(userId);
    }
}
