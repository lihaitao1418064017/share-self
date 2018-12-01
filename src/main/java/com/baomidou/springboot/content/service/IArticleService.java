package com.baomidou.springboot.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.springboot.content.entity.Article;
import com.baomidou.springboot.content.vo.ArticleVO;

import java.sql.Wrapper;
import java.util.List;


/**
* @Description:
* @Author:         LiHaitao
* @CreateDate:     2018/8/4 15:36
* @UpdateUser:
* @UpdateDate:     2018/8/4 15:36
* @UpdateRemark:
* @Version:        1.0.0
*/
public interface IArticleService extends IService<Article> {


    List<ArticleVO> selectByWrapper(Wrapper wrapper);

    ArticleVO selectByPrimaryKey(Long id);

    List<ArticleVO> selectByUserId(Long id);
}