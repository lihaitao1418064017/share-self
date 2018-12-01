package com.baomidou.springboot.content.entity;

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
     * 评论的文章
     */
    private Long articleId;


    /**
     * 艾特谁的评论
     */
    private Long parentId;

    /**
     * 评论人
     */
//    private User user;
    private Long userId;


    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private Date date=new Date();

    /**
     * 赞数
     */
    private Integer praise;




}
