package com.baomidou.springboot.im.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.springboot.im.entity.Apply;

/**
* @Description:
* @Author:         LiHaitao
* @CreateDate:     2018/9/18 18:50
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0.0
*/
public interface IApplyService extends IService<Apply> {

    /**
     * 获取申请信息
     */
   String getContent(int type,String remark);
}