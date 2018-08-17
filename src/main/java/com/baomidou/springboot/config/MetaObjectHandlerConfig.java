package com.baomidou.springboot.config;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

/**
* @Description:    实体自动填充类
* @Author:         LiHaitao
* @CreateDate:     2018/8/15 17:36
* @UpdateUser:
* @UpdateDate:     2018/8/15 17:36
* @UpdateRemark:
* @Version:        1.0.0
*/
@Component
public class MetaObjectHandlerConfig extends MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    System.out.println("插入方法实体填充");
    setFieldValByName("testDate", new Date(), metaObject);
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    System.out.println("更新方法实体填充");
  }
}
