package com.baomidou.springboot.domain.dto;

/**
* @Description:    申请类型
* @Author:         LiHaitao
* @CreateDate:     2018/9/21 13:23
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0.0
*/
public interface ApplyType {
    int friend = 1;   /**好友申请*/
    int group = 2;    /**群组申请*/
    int system = 3;   /**系统申请*/
}
