package com.baomidou.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
* @Description:    评论回复
* @Author:         LiHaitao
* @CreateDate:     2018/8/4 15:13
* @UpdateUser:
* @UpdateDate:     2018/8/4 15:13
* @UpdateRemark:
* @Version:        1.0.0
*/
@Data
@TableName("reply")
public class Reply extends SuperEntity<Reply> {

    /**
     * 文章
     */
    private Article article;
    private Long articleId;

    /**
     * 父评论id
     */
    private Comments parentComments;
    private Long parentId;

    /**
     * 子评论id
     */
    private Comments childComments;
    private Long childId;


}
