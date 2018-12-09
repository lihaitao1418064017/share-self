package com.baomidou.springboot.im.controller;

import com.baomidou.springboot.im.entity.GroupUser;
import com.baomidou.springboot.im.service.IGroupUserService;
import com.baomidou.springboot.im.vo.GroupUserVO;
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
 * @Date 2018/12/8 下午7:29
 **/
@RestController
@RequestMapping("groupuser")
public class GroupUserController {

    @Autowired
    private IGroupUserService groupUserService;


    @GetMapping("/{userId}")
    public ResponseMessage<List<GroupUserVO>> getGroups(@PathVariable Long userId){
        List<GroupUserVO> groupUserVOS=groupUserService.selectGroupsByUserId(userId);
        groupUserVOS.forEach(groupUserVO -> {
            groupUserVO.getGroupClient().setAvatar(ImgMnUtil.nextImg());
        });
        return ResponseMessage.ok(groupUserVOS);
    }
}
