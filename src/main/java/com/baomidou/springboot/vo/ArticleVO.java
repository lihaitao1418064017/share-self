package com.baomidou.springboot.vo;

import com.baomidou.springboot.domain.Photos;
import com.baomidou.springboot.domain.User;
import com.baomidou.springboot.domain.Video;
import com.baomidou.springboot.domain.enums.ArticleType;
import lombok.Data;

import java.util.Date;
import java.util.List;


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
    private Date date;

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


    /**
     * 文章图片
     */
    private List<Photos> photosList;

    /**
     * 视频
     */
    private List<Video> videosList;
}
