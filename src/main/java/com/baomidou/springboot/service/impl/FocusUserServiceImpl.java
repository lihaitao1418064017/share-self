package com.baomidou.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.springboot.domain.FocusUser;
import com.baomidou.springboot.domain.Photos;
import com.baomidou.springboot.mapper.FocusUserMapper;
import com.baomidou.springboot.mapper.PhotosMapper;
import com.baomidou.springboot.service.IFocusUserService;
import com.baomidou.springboot.service.IPhotosService;
import org.springframework.stereotype.Service;

/**
* @Description:
* @Author:         LiHaitao
* @CreateDate:     2018/8/16 21:04
* @UpdateUser:
* @UpdateDate:     2018/8/16 21:04
* @UpdateRemark:
* @Version:        1.0.0
*/
@Service
public class FocusUserServiceImpl extends ServiceImpl<FocusUserMapper, FocusUser> implements IFocusUserService {

}