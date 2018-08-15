package com.baomidou.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.springboot.entity.enums.ArticleType;
import lombok.Data;

import java.util.Date;


/**
* @Description:    文章类
* @Author:         LiHaitao
* @CreateDate:     2018/8/4 15:07
* @UpdateUser:
* @UpdateDate:     2018/8/4 15:07
* @UpdateRemark:
* @Version:        1.0.0
*/
@Data
@TableName("article")
public class Article extends SuperEntity<Article> {
    /**
     * 文章标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 时间
     */
    private Date date=new Date();

    /**
     * 文章类型
     */
    private ArticleType articleType;

    /**
     * 推荐
     */
    private Integer recommend;


    /**
     * 文章作者
     */
    private User user;
    private Long userId;



}

