package com.baomidou.springboot.content.vo;

import com.baomidou.springboot.auth.entity.User;
import lombok.Data;

import java.util.Date;

/**
 * @Description 评论
 * @Author LiHaitao
 * @Date 2018/8/16 21:30
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
@Data
public class CommentsVO {


    private Long id;

    /**
     * 评论的文章
     */
//    private Article article;


    /**
     * 艾特谁的评论
     */
    private User parentUser;

    /**
     * 评论人
     */
    private User currentUser;


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
