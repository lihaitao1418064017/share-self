//package com.baomidou.springboot.cache;
//
//import com.baomidou.springboot.content.entity.Article;
//import com.baomidou.springboot.content.service.IArticleService;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
//* @Description:    文章的缓存
//* @Author:         LiHaitao
//* @CreateDate:     2018/8/10 13:30
//* @UpdateUser:
//* @UpdateDate:
//* @UpdateRemark:
//* @Version:        1.0.0
//*/
//public class GuavaArticleCache extends GuavaAbstractLoadingCache<String,Article> {
//    @Autowired
//    private IArticleService articleService;
//
//    public GuavaArticleCache() {
//        //设置过期时间等
//        this.setMaximumSize(1000);
//    }
//
//    @Override
//    protected Article fetchData(String key) {
//       return articleService.selectById(Long.parseLong(key));
//    }
//}
