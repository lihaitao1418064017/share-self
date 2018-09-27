package com.baomidou.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.springboot.domain.dto.Apply;

import java.io.Serializable;

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