package com.baomidou.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.springboot.domain.Comments;
import com.baomidou.springboot.response.ResponseMessage;
import com.baomidou.springboot.service.ICommentsService;
import com.baomidou.springboot.vo.CommentsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* @Description:    评论接口
* @Author:         LiHaitao
* @CreateDate:     2018/8/5 15:16
* @UpdateUser:
* @UpdateDate:     2018/8/16 21:43
* @UpdateRemark:
* @Version:        1.0.0
*/
@RestController
@RequestMapping("/comments")
public class CommentsController extends ApiController {

    @Autowired
    private ICommentsService commentsService;

    /**
     * 添加
     */
    @PostMapping("/add")
    public ResponseMessage<Boolean> addComments(@RequestBody Comments comments) {
        return ResponseMessage.ok(commentsService.insert(comments));
    }
    /**
     * 删除
     */
    @RequestMapping("/{id}")
    public ResponseMessage<Boolean> deleteById(@PathVariable("id")Long id){
        return ResponseMessage.ok(commentsService.deleteById(id));
    }

    /**
     * 查询所有
     */
    @GetMapping("/getAll")
    public ResponseMessage<List<CommentsVO>> getAll(@RequestParam Long id){
        return ResponseMessage.ok(commentsService.getAll(id));
    }




    /**
     * 分页查询
     */
    @GetMapping("/page")
    public ResponseMessage test(@RequestParam("pageSize")Integer pageSize, @RequestParam("pageNo")Integer pageNo) {
        QueryWrapper queryWrapper=new QueryWrapper();
        return ResponseMessage.ok(commentsService.selectPage(new Page<Comments>(pageNo-1, pageSize), queryWrapper));
    }







}
