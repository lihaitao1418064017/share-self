package com.baomidou.springboot.im.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.springboot.im.entity.MessageClient;
import com.baomidou.springboot.im.mapper.MessageMapper;
import com.baomidou.springboot.im.service.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @Author lht
 * @Date 2018/11/27 下午10:10
 **/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, MessageClient> implements IMessageService {
}
