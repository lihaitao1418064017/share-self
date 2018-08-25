package com.baomidou.springboot.config.jwt;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
/**
* @Description:    jwt配置类
* @Author:         LiHaitao
* @CreateDate:     2018/8/25 21:47
* @UpdateUser:
* @UpdateDate:
* @UpdateRemark:
* @Version:        1.0.0
*/
@Configuration
public class JwtConfig {
	
 	@Bean
    public FilterRegistrationBean basicFilterRegistrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());  
          
        List<String> urlPatterns = new ArrayList<>();  
        urlPatterns.add("*.auth");  
        registrationBean.setUrlPatterns(urlPatterns);  
        return registrationBean;  
    }  
}
