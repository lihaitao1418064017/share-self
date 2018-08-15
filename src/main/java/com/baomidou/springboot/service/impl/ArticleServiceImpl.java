package com.baomidou.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.springboot.entity.Article;
import com.baomidou.springboot.entity.User;
import com.baomidou.springboot.mapper.ArticleMapper;
import com.baomidou.springboot.mapper.UserMapper;
import com.baomidou.springboot.service.IArticleService;
import com.baomidou.springboot.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * User 表数据服务层接口实现类
 *
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {


}