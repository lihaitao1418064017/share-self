package com.baomidou.springboot.config;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.springboot.cache.ICache;
import com.baomidou.springboot.cache.redisImpl.CacheImpl;
import com.baomidou.springboot.im.processor.LoginServiceProcessor;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

import javax.annotation.PostConstruct;

/**
* @Description:    配置类
* @Author:         LiHaitao
* @CreateDate:     2018/8/15 17:33
* @UpdateUser:
* @UpdateDate:     2018/8/15 17:33
* @UpdateRemark:
* @Version:        1.0.0
*/


@Configuration
@MapperScan(basePackages = {"com.baomidou.springboot.content.mapper*","com.baomidou.springboot.im.mapper*","com.baomidou.springboot.auth.mapper*"})//这个注解，作用相当于下面的@Bean MapperScannerConfigurer，2者配置1份即可
public class CommonsConfig {

    /**
     * ArticleCache对象注入
     */
//    @Bean
//    public GuavaArticleCache articleCache(){
//
//        return new GuavaArticleCache();
//    }

    /**
     * 注入redis缓存接口
     * @return
     */
    @Bean
    public ICache cache(){
        return  new CacheImpl();
    }

    @Bean
    public LoginServiceProcessor loginServiceProcessor(){
        return new LoginServiceProcessor();
    }


    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 开启 PageHelper 的支持
        paginationInterceptor.setLocalPage(true);
        /*
         * 【测试多租户】 SQL 解析处理拦截器<br>
         * 这里固定写成住户 1 实际情况你可以从cookie读取，因此数据看不到 【 麻花藤 】 这条记录（ 注意观察 SQL ）<br>
         */
        List<ISqlParser> sqlParserList = new ArrayList<>();
        TenantSqlParser tenantSqlParser = new TenantSqlParser();
        tenantSqlParser.setTenantHandler(new TenantHandler() {
            @Override
            public Expression getTenantId() {
                return new LongValue(1L);
            }

            @Override
            public String getTenantIdColumn() {
                return "tenant_id";
            }

            @Override
            public boolean doTableFilter(String tableName) {
                // 这里可以判断是否过滤表
                /*if ("user".equals(tableName)) {
                    return true;
                }*/
                return false;
            }
        });

        sqlParserList.add(tenantSqlParser);
        paginationInterceptor.setSqlParserList(sqlParserList);
//        paginationInterceptor.setSqlParserFilter(new ISqlParserFilter() {
//            @Override
//            public boolean doFilter(MetaObject metaObject) {
//                MappedStatement ms = PluginUtils.getMappedStatement(metaObject);
//                // 过滤自定义查询此时无租户信息约束【 麻花藤 】出现
//                if ("com.baomidou.springboot.auth.mapper.UserClientMapper.selectListBySQL".equals(ms.getId())) {
//                    return true;
//                }
//                return false;
//            }
//        });
        return paginationInterceptor;
    }

    /**
     * 相当于顶部的：
     * {@code @MapperScan("com.baomidou.springboot.mapper*")}
     * 这里可以扩展，比如使用配置文件来配置扫描Mapper的路径
     */
//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer() {
//        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
//        scannerConfigurer.setBasePackage("com.baomidou.springboot");
//        return scannerConfigurer;
//    }

   /* @Bean
    public H2KeyGenerator getH2KeyGenerator() {
        return new H2KeyGenerator();
    }*/
}
