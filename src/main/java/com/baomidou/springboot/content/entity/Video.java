package com.baomidou.springboot.content.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
* @Description:    视频
* @Author:         LiHaitao
* @CreateDate:     2018/8/4 15:13
* @UpdateUser:
* @UpdateDate:     2018/8/4 15:13
* @UpdateRemark:
* @Version:        1.0.0
*/

@Data
@TableName("video")
public class Video extends SuperEntity<Video> {
    private static final long serialVersionUID = 5952689219411916553L;

    /**
     * 文章
     */
    private Long articleId;

    /**
     * 视频地址
     */
    private String url;

}
