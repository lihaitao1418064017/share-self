package com.baomidou.springboot.im.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.springboot.im.entity.Friend;
import com.baomidou.springboot.im.vo.FriendVO;

import java.util.List;

/**
 * Description:
 *
 * @Author lht
 * @Date 2018/12/1 下午7:01
 **/
public interface IFriendService extends IService<Friend> {


    List<FriendVO> selectFriendsByUserId(String userId);
}
