package com.baomidou.springboot.mapper;


import com.baomidou.springboot.SuperMapper;
import com.baomidou.springboot.domain.Article;
import com.baomidou.springboot.domain.User;
import com.baomidou.springboot.vo.ArticleVO;
import org.apache.ibatis.annotations.Param;

import java.sql.Wrapper;
import java.util.List;

/**
* @Description:    文章的mapper
* @Author:         LiHaitao
* @CreateDate:     2018/8/4 15:32
* @UpdateUser:
* @UpdateDate:     2018/8/4 15:32
* @UpdateRemark:
* @Version:        1.0.0
*/
public interface ArticleMapper extends SuperMapper<Article> {

    /**
     * 根据条件查询文章
     */
    List<ArticleVO> selectByWrapper(@Param("ew") Wrapper wrapper);


    ArticleVO selectByArticlePrimaryKey(@Param("articleId")Long id);

    List<ArticleVO> selectByUserId(@Param("userId") Long id);




}