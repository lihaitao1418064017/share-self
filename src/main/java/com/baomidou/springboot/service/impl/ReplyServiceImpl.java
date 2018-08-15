package com.baomidou.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.springboot.entity.Reply;
import com.baomidou.springboot.mapper.ReplyMapper;
import com.baomidou.springboot.service.IReplyService;
import org.springframework.stereotype.Service;

/**
* @Description:    回复评论业务层实现类
* @Author:         LiHaitao
* @CreateDate:     2018/8/6 19:28
* @UpdateUser:
* @UpdateDate:     2018/8/6 19:28
* @UpdateRemark:
* @Version:        1.0.0
*/
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements IReplyService {

}