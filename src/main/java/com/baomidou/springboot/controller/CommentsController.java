package com.baomidou.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.ApiAssert;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.ApiResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageHelper;
import com.baomidou.springboot.entity.Comments;
import com.baomidou.springboot.response.ErrorCode;
import com.baomidou.springboot.response.ResponseMessage;
import com.baomidou.springboot.service.ICommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
* @Description:    评论接口
* @Author:         LiHaitao
* @CreateDate:     2018/8/5 15:16
* @UpdateUser:
* @UpdateDate:     2018/8/5 15:16
* @UpdateRemark:
* @Version:        1.0.0
*/
@RestController
@RequestMapping("/comments")
public class CommentsController extends ApiController {

    @Autowired
    private ICommentsService commentsService;



    /**
     * <p>
     * 测试通用 Api Controller 逻辑
     * </p>
     * 测试地址：
     * http://localhost:8080/user/api
     * http://localhost:8080/user/api?test=mybatisplus
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
    public ResponseMessage<Boolean> addUser(@RequestBody Comments comments) {
        return ResponseMessage.ok(commentsService.insert(comments));
    }
    /**
     * 删除
     */
    @RequestMapping("/{id}")
    public ResponseMessage<Boolean> deleteById(@PathVariable("id")String id){
        return ResponseMessage.ok(commentsService.deleteById(Long.parseLong(id)));
    }

    /**
     * 更新
     */
    @RequestMapping("/update")
    public ResponseMessage<Boolean> update(@RequestBody Comments comments,@RequestParam("id")String id){
       comments.setId(Long.parseLong(id));
       return ResponseMessage.ok(commentsService.updateById(comments));
    }


    /**
     * 分页查询
     */
    @GetMapping("/page")
    public ResponseMessage test(@RequestParam("pageSize")Integer pageSize, @RequestParam("pageNo")Integer pageNo) {
        QueryWrapper queryWrapper=new QueryWrapper();
        return ResponseMessage.ok(commentsService.selectPage(new Page<Comments>(pageNo-1, pageSize), queryWrapper));
    }







    /**
     * ThreadLocal 模式分页
     * http://localhost:8080/user/page_helper?size=2&current=1
     */
    @GetMapping("/page_helper")
    public IPage pagehelper(Page page) {
        PageHelper.setPage(page);
        page.setRecords(commentsService.selectList(null));
        //获取总数并释放资源 也可以 PageHelper.getTotal()
        page.setTotal(PageHelper.freeTotal());
        return page;
    }

    /**
     * 测试事物
     * http://localhost:8080/user/test_transactional<br>
     * 访问如下并未发现插入数据说明事物可靠！！<br>
     * http://localhost:8080/user/test<br>
     * <br>
     * 启动  Application 加上 @EnableTransactionManagement 注解其实可无默认貌似就开启了<br>
     * 需要事物的方法加上 @Transactional 必须的哦！！
     */
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/test_transactional")
    public void testTransactional(@RequestBody Comments comments) {
        commentsService.insert(comments);
        System.out.println(" 这里手动抛出异常，自动回滚数据");
        throw new RuntimeException();
    }



}
