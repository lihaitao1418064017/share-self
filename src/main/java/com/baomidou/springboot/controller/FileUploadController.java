package com.baomidou.springboot.controller;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.springboot.response.ResponseMessage;
import com.baomidou.springboot.service.IPhotosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
* @Description:    文件上传接口
* @Author:         LiHaitao
* @CreateDate:     2018/8/4 20:43
* @UpdateUser:
* @UpdateDate:     2018/8/4 20:43
* @UpdateRemark:
* @Version:        1.0.0
*/
@RestController
@RequestMapping({"/file"})
public abstract class FileUploadController {

    //允许上传的图片文件格式
    private static String[] photoFiles = {".gif", ".png", ".jpg", ".jpeg"};
    //允许上传的视频文件格式
    private static String[] videoFiles={".mp4", ".flv", ".avi", ".mpg", ".wmv",
            ".3gp", ".mov", ".asf", ".asx", ".vob", ".wmv9", ".rm", ".rmvb"};
    //文件保存路劲
    @Value("${share.file.storage.path}")
    private String fileStoragePath;

    String f="";
    @Autowired
    private IPhotosService photosService;

    @Autowired
    protected MultipartProperties multipartProperties;

    public FileUploadController() {
    }


    /**
    * @Description:     上传图片,个人头像
    * @Author:         Lihaitao
    * @Date:       2018/8/4 22:03
    * @UpdateUser:
    * @UpdateRemark:
    */
    @PostMapping({"/upload_photo"})
    public ResponseMessage<String> uploadPhoto(@RequestParam("file") MultipartFile file) {
        List<String> list=Arrays.asList(photoFiles);
        for (String each:list) {
            if (each.equals(getFileExtension(file))){
                 return ResponseMessage.ok(fileTransferTo(file)); }
        }
        return ResponseMessage.error("Photo format not allowed!");
    }
    /**
    * @Description:    上传视频
    * @Author:         Lihaitao
    * @Date:       2018/8/6 15:37
    * @UpdateUser:
    * @UpdateRemark:
    */
    @PostMapping("/upload_video")
    public ResponseMessage<String> uploadVideo(@RequestParam("file")MultipartFile file){
        List<String> list=Arrays.asList(videoFiles);
        for (String each:list) {
            if (each.equals(getFileExtension(file))){
                return ResponseMessage.ok(fileTransferTo(file)); }
        }
        return ResponseMessage.error("Video format not allowed!");
    }




    /**
     * @Description: 多图片上传，文章图片，返回图片的名称list
     * @Author:         LiHaitao
     * @Date:       2018/8/4 22:07
     * @UpdateUser:
     * @UpdateRemark:
     */
    @PostMapping({"/multi_upload"})
    public ResponseMessage<List<String>> multiUpload(@RequestParam("file") List<MultipartFile> files) {
       return ResponseMessage.ok( files.stream().map((file) -> {
           return fileTransferTo(file);
       }).collect(Collectors.toList()));
    }


    /**
    * @Description: 获取文件后缀名: .jpg...
    * @Author:         Lihaitao
    * @Date:       2018/8/4 21:38
    * @UpdateUser:
    * @UpdateRemark:
    */
    protected String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName.substring(fileName.lastIndexOf("."));
    }
    /**
    * @Description:    单个文件上传方法，将文件写入磁盘后，返回文件的新名称
    * @Author:         Lihaitao
    * @Date:       2018/8/4 22:37
    * @UpdateUser:
    * @UpdateRemark:
    */
    private String fileTransferTo(MultipartFile file){
        String fileNameExtension = this.getFileExtension(file);
        String newFileName= RandomUtil.simpleUUID()+fileNameExtension;
        try{
            file.transferTo(new File(fileStoragePath,newFileName));
        }catch (IOException e){
        }
        return newFileName;
    }
}

