package com.baomidou.springboot.vo;

import com.baomidou.springboot.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class ArticleVO {

    private Long id;
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
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date=new Date();

    /**
     * 文章类型
     */
    private String articleType;

    /**
     * 推荐
     */
    private Integer recommend;


    /**
     * 文章作者
     */
    private User user;

}
