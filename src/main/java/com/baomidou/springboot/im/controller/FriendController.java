package com.baomidou.springboot.im.controller;

import com.baomidou.springboot.im.service.IFriendService;
import com.baomidou.springboot.im.vo.FriendVO;
import com.baomidou.springboot.response.ResponseMessage;
import com.baomidou.springboot.util.ImgMnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description:
 *
 * @Author lht
 * @Date 2018/12/8 下午6:16
 **/

@RestController
@RequestMapping("/friend")
public class FriendController{

    @Autowired
    private IFriendService friendService;
        @GetMapping("/{userId}")
        public ResponseMessage<List<FriendVO>> getUsers(@PathVariable String userId){
            List<FriendVO> friends=friendService.selectFriendsByUserId(userId);
            friends.forEach(friendVO -> {
                friendVO.getFriend().setAvatar(ImgMnUtil.nextImg());
            });
            return ResponseMessage.ok(friends);
        }

}
