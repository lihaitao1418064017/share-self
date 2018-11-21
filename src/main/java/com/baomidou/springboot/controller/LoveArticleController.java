package com.baomidou.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.springboot.domain.LoveArticle;
import com.baomidou.springboot.response.ResponseMessage;
import com.baomidou.springboot.service.IArticleService;
import com.baomidou.springboot.service.ILoveArticleService;
import com.baomidou.springboot.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
* @Description:
* @Author:         LiHaitao
* @CreateDate:     2018/8/16 21:12
* @UpdateUser:
* @UpdateDate:     2018/8/16 21:12
* @UpdateRemark:
* @Version:        1.0.0
*/
@RestController
@RequestMapping("/love")
public class LoveArticleController extends ApiController {

    @Autowired
    private ILoveArticleService loveArticleService;
    @Autowired
    private IArticleService articleService;

    /**
    * @Description:    根据用户id查询用户关注的文章
    * @Author:         Lihaitao
    * @Date:       2018/8/16 21:14
    * @UpdateUser:
    * @UpdateRemark:
    */
    @GetMapping("/getALl")
    public ResponseMessage<List<ArticleVO>> getAll(@RequestParam Long id){
        QueryWrapper<LoveArticle> queryWrapper=new QueryWrapper<LoveArticle>();
        queryWrapper.eq("user_id",id);
        List<LoveArticle> loveArticles=loveArticleService.selectList(queryWrapper);
        List<ArticleVO>  list=new ArrayList<>();
        loveArticles.forEach(loveArticle -> {
          list.add(articleService.selectByPrimaryKey(loveArticle.getArticleId()));
        });
        return ResponseMessage.ok(list);
    }

}
