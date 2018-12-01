package com.baomidou.springboot.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.springboot.content.entity.Article;
import com.baomidou.springboot.content.mapper.ArticleMapper;
import com.baomidou.springboot.content.service.IArticleService;
import com.baomidou.springboot.content.vo.ArticleVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * UserClient 表数据服务层接口实现类
 *
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {


    @Override
    public List<ArticleVO> selectByWrapper(Wrapper wrapper) {
        return baseMapper.selectByWrapper(wrapper);
    }

    @Override
    public ArticleVO selectByPrimaryKey(Long id) {
        return baseMapper.selectByArticlePrimaryKey(id);
    }

    @Override
    public List<ArticleVO> selectByUserId(Long id) {
        return baseMapper.selectByUserId(id);
    }
}