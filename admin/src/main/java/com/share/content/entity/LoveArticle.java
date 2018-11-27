package com.share.content.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description 用户喜欢的文章
 * @Author LiHaitao
 * @Date 2018/8/16 10:29
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
@Data
@TableName("love_article")
public class LoveArticle extends SuperEntity<LoveArticle> {

    /**
     * 被关注文章id
     */
    private Long articleId;

    /**
     * 关注用户
     */
    private Long userId;
}
