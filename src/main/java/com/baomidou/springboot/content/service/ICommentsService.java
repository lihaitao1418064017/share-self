package com.baomidou.springboot.content.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.springboot.content.entity.Comments;
import com.baomidou.springboot.content.vo.CommentsVO;

import java.util.List;

/**
* @Description:    评论业务层接口
* @Author:         LiHaitao
* @CreateDate:     2018/8/5 15:14
* @UpdateUser:
* @UpdateDate:     2018/8/16 21:39
* @UpdateRemark:
* @Version:        1.0.0
*/
public interface ICommentsService extends IService<Comments> {

    List<CommentsVO> getAll(Long id);

}