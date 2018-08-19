package com.baomidou.springboot.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.springboot.domain.Photos;
import com.baomidou.springboot.response.ResponseMessage;
import com.baomidou.springboot.service.IArticleService;
import com.baomidou.springboot.service.IPhotosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
* @Description:    文章的图片上传接口
* @Author:         LiHaitao
* @CreateDate:     2018/8/4 22:48
* @UpdateUser:
* @UpdateDate:     2018/8/4 22:48
* @UpdateRemark:
* @Version:        1.0.0
*/
@RestController
@RequestMapping("/article_photos")
public class PhotoArticleController extends ApiController {

    @Autowired
    private IPhotosService photosService;

    @Autowired
    private IArticleService articleService;

   /**
    * @Description:    添加图片
    *
    * 关于这个接口的参数传递方法：ajax传入方法：
    *
     var strList = new Array();
     strList.push("field1");
     strList.push("field2");
     data:{"listParam" : strList},
    * @Author:    Lihaitao
    * @Date:       2018/8/4 22:49
    * @UpdateUser:
    * @UpdateRemark:
    */

    @RequestMapping("/add")
    public ResponseMessage addPhotoByArticle(@RequestParam("listParam[]") List<String> list, @RequestParam("id")Long id){
       list.forEach(photo->{
           Photos photos=new Photos();
           photos.setUrl(photo);
           photos.setArticleId(id);
           photosService.insert(photos);
       });
        return ResponseMessage.ok();
    }


    @RequestMapping("/update")
    public ResponseMessage update(@RequestBody Photos photos){
        return ResponseMessage.ok(photos.updateById());
    }
}
