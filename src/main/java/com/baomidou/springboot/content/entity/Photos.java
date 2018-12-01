package com.baomidou.springboot.content.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
* @Description:    照片
* @Author:         LiHaitao
* @CreateDate:     2018/8/4 15:13
* @UpdateUser:
* @UpdateDate:     2018/8/4 15:13
* @UpdateRemark:
* @Version:        1.0.0
*/
@Data
@TableName("photos")
public class Photos extends SuperEntity<Photos> {

    /**
     * 照片名称
     */
    private String url;

    /**
     * 文章
     */
    private Long articleId;

}
