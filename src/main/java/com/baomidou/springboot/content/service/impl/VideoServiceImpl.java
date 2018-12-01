package com.baomidou.springboot.content.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.springboot.content.entity.Video;
import com.baomidou.springboot.content.mapper.VideoMapper;
import com.baomidou.springboot.content.service.IVideoService;
import org.springframework.stereotype.Service;

/**
* @Description:    视频业务层实现类
* @Author:         LiHaitao
* @CreateDate:     2018/8/6 15:47
* @UpdateUser:
* @UpdateDate:     2018/8/6 15:47
* @UpdateRemark:
* @Version:        1.0.0
*/
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements IVideoService {

}