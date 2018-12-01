package com.baomidou.springboot.im.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.springboot.im.entity.GroupClient;
import com.baomidou.springboot.im.mapper.GroupMapper;
import com.baomidou.springboot.im.service.IGroupService;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @Author lht
 * @Date 2018/11/27 下午10:10
 **/
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, GroupClient> implements IGroupService {
}
