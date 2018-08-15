package com.baomidou.springboot.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.springboot.entity.Comments;
import com.baomidou.springboot.mapper.CommenstMapper;
import com.baomidou.springboot.service.ICommentsService;
import org.springframework.stereotype.Service;

/**
* @Description:    评论业务层实现
* @Author:         LiHaitao
* @CreateDate:     2018/8/5 15:16
* @UpdateUser:
* @UpdateDate:     2018/8/5 15:16
* @UpdateRemark:
* @Version:        1.0.0
*/
@Service
public class CommentsServiceImpl extends ServiceImpl<CommenstMapper, Comments> implements ICommentsService {

}