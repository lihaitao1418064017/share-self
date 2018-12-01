package com.baomidou.springboot.content.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageHelper;
import com.baomidou.springboot.content.entity.Article;
import com.baomidou.springboot.cache.ICache;
import com.baomidou.springboot.response.ResponseMessage;
import com.baomidou.springboot.content.service.IArticleService;
import com.baomidou.springboot.content.service.IPhotosService;
import com.baomidou.springboot.auth.service.IUserService;
import com.baomidou.springboot.content.service.IVideoService;
import com.baomidou.springboot.content.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
* @Description:    article接口
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


//    @Autowired
//    private IUserService userService;

    @Autowired
    private IPhotosService photosService;

    @Autowired
    private IVideoService videoService;


    private static final String ARTICLE_KEY="article_page";//文章key

    @Autowired
    private ICache cache;//cache


    /**
     * 添加
     */
    @PostMapping("/add")
    public ResponseMessage<Boolean> addArticle(@RequestBody Article article, @RequestParam("id")String id) {
        return ResponseMessage.ok(articleService.insert(article));
    }

    /**
     * 删除
     */
    @RequestMapping("/{id}")
    public ResponseMessage<Boolean> deleteById(@PathVariable("id")Long id){
        return ResponseMessage.ok(articleService.deleteById(id));
    }

    /**
     * 更新
     */
    @RequestMapping("/update")
    public ResponseMessage<Boolean> update(@RequestBody ArticleVO articleVO,@RequestParam("id")Long id){
       return ResponseMessage.ok(articleService.updateById(modelToEntity(articleVO)));
    }
    /**
     * 根据id查询文章信息
     */
    @GetMapping("/get")
    public ResponseMessage<ArticleVO> getById(@RequestParam()Long id){
        return ResponseMessage.ok(articleService.selectByPrimaryKey(id));
    }
    /**
     * 根据文章作者查询
     */
    @GetMapping("/getByUser")
    public ResponseMessage<List<ArticleVO>> getByUser(@RequestParam()Long id){
        return ResponseMessage.ok(articleService.selectByUserId(id));
    }
    /**
     * 根据条件查询所有文章
     */
    @GetMapping("/getAll")
    public ResponseMessage<List<ArticleVO>> getAll(){
        Wrapper wrapper=new QueryWrapper();
        return ResponseMessage.ok(articleService.selectByWrapper(wrapper));
    }


    /**
     * 分页查询
     */
    @GetMapping("/page")
    public ResponseMessage<IPage<ArticleVO>> page(@RequestParam("pageSize")Integer pageSize, @RequestParam("pageNo")Integer pageNo) {
        QueryWrapper<Article> queryWrapper1=new QueryWrapper<Article>();
        IPage<Article> page= articleService.selectPage(new Page<Article>(pageNo-1, pageSize), queryWrapper1);
        IPage<ArticleVO> page1=pageToPageVO(page);
        page1.getRecords().forEach(articleVO -> {
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("article_id",articleVO.getId());
            articleVO.setPhotosList(photosService.selectList(queryWrapper));
            articleVO.setVideosList(videoService.selectList(queryWrapper));
        });
        return ResponseMessage.ok(page1);
    }

    private IPage<ArticleVO> pageToPageVO(IPage<Article> page){
        IPage<ArticleVO> page1=new Page<>();
        page1.setRecords(entityToModelList(page.getRecords()));
        page1.setCurrent(page.getCurrent());
        page1.setSize(page.getSize());
        page1.setTotal(page.getTotal());

        return page1;
    }

    /**
     * 根据XX标题查询匹配的所有，不分页
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
        page.setRecords(articleService.selectList(new QueryWrapper<Article>()));
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
        article.setArticleType(vo.getArticleType());
        article.setContent(vo.getContent());
        article.setDate(vo.getDate());
        article.setRecommend(vo.getRecommend());
        article.setTitle(vo.getTitle());
        article.setUserId(vo.getUser().getId());
        article.setId(vo.getId());
        return article;
    }
    private ArticleVO entityToModel(Article article){
        ArticleVO articleVO=new ArticleVO();
        articleVO.setArticleType(article.getArticleType());
        articleVO.setContent(article.getContent());
        articleVO.setDate(article.getDate());
        articleVO.setId(article.getId());
        articleVO.setRecommend(article.getRecommend());
        articleVO.setTitle(article.getTitle());
//        articleVO.setUser(userService.selectById(article.getUserId()));
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
