package com.baomidou.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
* @Description:    对文章的评论
* @Author:         LiHaitao
* @CreateDate:     2018/8/4 15:08
* @UpdateUser:
* @UpdateDate:     2018/8/4 15:08
* @UpdateRemark:
* @Version:        1.0.0
*/
@Data
@TableName("comments")
public class Comments extends SuperEntity<Comments> {

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论人
     */
    private User user;
    private Long userId;

    /**
     * 评论的文章
     */
    private Article article;
    private Long articleId;

    /**
     * 评论时间
     */
    private Date date=new Date();

    /**
     * 赞数
     */
    private Integer praise;



}
