package com.baomidou.springboot.content.mapper;


import com.baomidou.springboot.SuperMapper;
import com.baomidou.springboot.content.entity.Comments;
import com.baomidou.springboot.content.vo.CommentsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @Description:    评论数据访问层
* @Author:         LiHaitao
* @CreateDate:     2018/8/5 15:13
* @UpdateUser:     lht
* @UpdateDate:     2018-8-16 21:36
* @UpdateRemark:
* @Version:        1.0.0
*/
public interface CommenstMapper extends SuperMapper<Comments> {


    List<CommentsVO>  selectByArticleId(@Param("articleId")Long id);



}