package com.baomidou.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.springboot.domain.FocusUser;
import com.baomidou.springboot.domain.LoveArticle;
import com.baomidou.springboot.mapper.FocusUserMapper;
import com.baomidou.springboot.mapper.LoveArticleMapper;
import com.baomidou.springboot.service.IFocusUserService;
import com.baomidou.springboot.service.ILoveArticleService;
import org.springframework.stereotype.Service;

/**
* @Description:
* @Author:         LiHaitao
* @CreateDate:     2018/8/16 21:05
* @UpdateUser:
* @UpdateDate:     2018/8/16 21:05
* @UpdateRemark:
* @Version:        1.0.0
*/
@Service
public class LoveArticleServiceImpl extends ServiceImpl<LoveArticleMapper, LoveArticle> implements ILoveArticleService {

}