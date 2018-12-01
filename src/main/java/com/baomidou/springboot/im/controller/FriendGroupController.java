package com.baomidou.springboot.im.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.springboot.im.entity.FriendGroup;
import com.baomidou.springboot.response.ResponseMessage;
import com.baomidou.springboot.im.service.IFriendGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friendgroup")
public class FriendGroupController {

    @Autowired
    private IFriendGroupService friendGroupService;

    /**
     * 删除 群组
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseMessage<Boolean> delete(@PathVariable String id){
        return ResponseMessage.ok(friendGroupService.deleteById(id));
    }

    /**
     * 修改群组信息
     * @param friendGroup
     * @return
     */
    @PutMapping("/update")
    public ResponseMessage<Boolean> update(@RequestBody FriendGroup friendGroup){
        return ResponseMessage.ok(friendGroupService.updateById(friendGroup));
    }

    @GetMapping("/get/{id}")
    public ResponseMessage<List<FriendGroup>>  getFrientGroups(@PathVariable String id){
        QueryWrapper<FriendGroup> queryWrapper=new QueryWrapper<FriendGroup>();
        queryWrapper.eq("id",id);
        return ResponseMessage.ok(friendGroupService.selectList(queryWrapper));

    }


}
