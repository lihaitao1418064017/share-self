package com.baomidou.springboot;

import com.baomidou.springboot.im.ImServerStart;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis-plus Spring Boot 测试 Demo<br>
 * 文档：http://mp.baomidou.com<br>
 */
@EnableTransactionManagement
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.baomidou.springboot.config",
        "com.baomidou.springboot.content.controller",
        "com.baomidou.springboot.content.service",
        "com.baomidou.springboot.im.controller",
        "com.baomidou.springboot.im.service",
        "com.baomidou.springboot.auth.controller",
        "com.baomidou.springboot.auth.service"})
@MapperScan(basePackages = {"com.baomidou.springboot.content.mapper","com.baomidou.springboot.im.mapper","com.baomidou.springboot.auth.mapper"})
public class Application {

    protected final static Logger logger = LoggerFactory.getLogger(Application.class);


    public static void main(String[] args) throws Exception {
        ImServerStart.chatStart();
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
        logger.info("PortalApplication is success!");
        System.err.println("The application entry is. http://localhost:8080/wap/login.html");
    }

}
