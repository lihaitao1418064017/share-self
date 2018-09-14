package com.baomidou.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.springboot.domain.Msg;
import com.baomidou.springboot.domain.entity.Role;
import com.baomidou.springboot.mapper.MsgMapper;
import com.baomidou.springboot.mapper.RoleMapper;
import com.baomidou.springboot.service.IMsgService;
import com.baomidou.springboot.service.IRoleService;
import org.springframework.stereotype.Service;

/**
* @Description:
* @Author:         LiHaitao
* @CreateDate:     2018/9/14 15:32
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0.0
*/
@Service
public class MsgServiceImpl extends ServiceImpl<MsgMapper, Msg> implements IMsgService {

}