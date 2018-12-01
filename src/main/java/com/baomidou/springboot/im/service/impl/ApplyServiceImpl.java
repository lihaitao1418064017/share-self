package com.baomidou.springboot.im.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.springboot.im.entity.Apply;
import com.baomidou.springboot.im.entity.ApplyType;
import com.baomidou.springboot.im.mapper.ApplyMapper;
import com.baomidou.springboot.im.service.IApplyService;
import org.springframework.stereotype.Service;

/**
* @Description:
* @Author:         LiHaitao
* @CreateDate:     2018/9/18 18:51
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0.0
*/
@Service
public class ApplyServiceImpl extends ServiceImpl<ApplyMapper, Apply> implements IApplyService {

    @Override
    public String getContent(int type, String remark) {
        if(type == ApplyType.friend){
            return "申请添加你为好友";
        }
        if(type==ApplyType.group){
            return "申请加入群";
        }
        if(type==ApplyType.system){
            return remark;
        }
        return "";
    }




}