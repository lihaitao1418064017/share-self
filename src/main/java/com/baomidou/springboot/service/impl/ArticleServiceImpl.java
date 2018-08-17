package com.baomidou.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.springboot.domain.Article;
import com.baomidou.springboot.mapper.ArticleMapper;
import com.baomidou.springboot.service.IArticleService;
import com.baomidou.springboot.vo.ArticleVO;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;

/**
 *
 * User 表数据服务层接口实现类
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