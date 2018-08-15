package com.baomidou.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiAssert;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.ApiResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageHelper;

import com.baomidou.springboot.entity.Article;
import com.baomidou.springboot.entity.User;
import com.baomidou.springboot.entity.enums.ArticleType;
import com.baomidou.springboot.response.ErrorCode;
import com.baomidou.springboot.response.ResponseMessage;
import com.baomidou.springboot.service.IArticleService;
import com.baomidou.springboot.service.IUserService;
import com.baomidou.springboot.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RestController
@RequestMapping("/article")
public class ArticleController extends ApiController {

    @Autowired
    private IArticleService articleService;
    @Autowired
    private IUserService userService;

    private static final String ARTICLE_KEY="article_page";

   /* @Autowired
    private ICache cache;*/

    /**
     * <p>
     * 测试通用 Api Controller 逻辑
     * </p>
     * 测试地址：
     */
    @GetMapping("/api")
    public ApiResult<String> testError(String test) {

        ApiAssert.isNull(ErrorCode.TEST, test);
        return success(test);
    }

    /**
     * 添加
     */
    @PostMapping("/add")
    public ResponseMessage<Boolean> addUser(@RequestBody ArticleVO vo, @RequestParam("id")String id) {
        User user=userService.selectById(id);
        vo.setUser(user);
        return ResponseMessage.ok(articleService.insert(modelToEntity(vo)));
    }

    /**
     * 删除
     */
    @RequestMapping("/{id}")
    public ResponseMessage<Boolean> deleteById(@PathVariable("id")String id){
        return ResponseMessage.ok(articleService.deleteById(Long.parseLong(id)));
    }

    /**
     * 更新
     */
    @RequestMapping("/update")
    public ResponseMessage<Boolean> update(@RequestBody ArticleVO articleVO,@RequestParam("id")String id){
       articleVO.setId(Long.parseLong(id));
       return ResponseMessage.ok(articleService.updateById(modelToEntity(articleVO)));
    }


    /**
     * 分页查询
     */
    @GetMapping("/page")
    public ResponseMessage page(@RequestParam("pageSize")Integer pageSize, @RequestParam("pageNo")Integer pageNo) {
        QueryWrapper queryWrapper=new QueryWrapper();
        IPage<Article> page= articleService.selectPage(new Page<Article>(pageNo-1, pageSize), queryWrapper);
        return ResponseMessage.ok(page);
    }


    /**
     * 根据XX查询匹配的所有，不分页
     */
    @GetMapping("/like_title")
    public ResponseMessage<List<ArticleVO>> getArticleByWrapper(@RequestParam String title) {
        return ResponseMessage.ok(entityToModelList(articleService.selectList(new QueryWrapper<Article>()
                .lambda().like(Article::getTitle, title))));
    }


    /**
     * ThreadLocal 模式分页
     */
    @GetMapping("/page_helper")
    public IPage pagehelper(Page page) {
        PageHelper.setPage(page);
        page.setRecords(articleService.selectList(null));
        //获取总数并释放资源 也可以 PageHelper.getTotal()
        page.setTotal(PageHelper.freeTotal());
        return page;
    }

    /**
     * 测试事物
     * 启动  Application 加上 @EnableTransactionManagement 注解其实可无默认貌似就开启了<br>
     * 需要事物的方法加上 @Transactional 必须的哦！！
     */
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/test_transactional")
    public void testTransactional(@RequestBody ArticleVO articleVO) {
        articleService.insert(modelToEntity(articleVO));
        System.out.println(" 这里手动抛出异常，自动回滚数据");
        throw new RuntimeException();
    }


    private Article modelToEntity(ArticleVO vo){
        Article article=new Article();
        article.setArticleType(ArticleType.getByTypeName(vo.getArticleType()));
        article.setContent(vo.getContent());
        article.setDate(vo.getDate());
        article.setRecommend(vo.getRecommend());
        article.setTitle(vo.getTitle());
        article.setUser(vo.getUser());
        article.setId(vo.getId());
        return article;
    }
    private ArticleVO entityToModel(Article article){
        ArticleVO articleVO=new ArticleVO();
        articleVO.setArticleType(article.getArticleType().getTypeName());
        articleVO.setContent(article.getContent());
        articleVO.setDate(article.getDate());
        articleVO.setId(article.getId());
        articleVO.setRecommend(article.getRecommend());
        articleVO.setTitle(article.getTitle());
        articleVO.setUser(article.getUser());
        return articleVO;
    }


    private List<ArticleVO> entityToModelList(List<Article> list){
        List<ArticleVO> list1=new ArrayList<>();
        list.forEach(en->{
            list1.add(entityToModel(en));
        });
        return list1;
    }

}
