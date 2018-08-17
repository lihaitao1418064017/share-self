package com.baomidou.springboot.domain;

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
@SuppressWarnings("serial")
@Data
@TableName("video")
public class Video extends SuperEntity<Video> {

    /**
     * 文章
     */
    private Long articleId;

    /**
     * 视频地址
     */
    private String url;

}
