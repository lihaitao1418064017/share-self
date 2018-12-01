package com.baomidou.springboot.im.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.springboot.im.entity.UserClient;
import com.baomidou.springboot.im.mapper.UserClientMapper;

import com.baomidou.springboot.im.service.IUserClientService;
import org.springframework.stereotype.Service;


/**
 * Description:
 *
 * @Author lht
 * @Date 2018/11/27 下午10:10
 **/
@Service
public class UserClientServiceImpl extends ServiceImpl<UserClientMapper, UserClient> implements IUserClientService {
}
