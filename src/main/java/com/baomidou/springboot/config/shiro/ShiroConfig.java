package com.baomidou.springboot.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description ShiroConfig:shiro配置
 * @Author LiHaitao
 * @Date 2018/8/19 20:23
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
@Configuration
public class ShiroConfig {

    private static final transient Logger log = LoggerFactory.getLogger(ShiroConfig.class);
    // cas server地址
    public static final String casServerUrlPrefix = "http://127.0.0.1";
    // Cas登录页面地址
    public static final String casLoginUrl = casServerUrlPrefix + "/login";
    // Cas登出页面地址
    public static final String casLogoutUrl = casServerUrlPrefix + "/logout";
    // 当前工程对外提供的服务地址
    public static final String shiroServerUrlPrefix = "http://127.0.0.1:8080";
    // casFilter UrlPattern
    public static final String casFilterUrlPattern = "/index";

    /**
    * @Description:   过滤器
    * @Author:         Lihaitao
    * @Date:       2018/8/24 13:43
    * @UpdateUser:
    * @UpdateRemark:
    */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager,CasFilter casFilter) {

        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();

        // SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

       /* // 登陆页面
        shiroFilterFactoryBean.setLoginUrl("/admin/login.html");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/admin/index");
        // 未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/500.html");*/

        // 拦截器.
        Map<String,Filter> filters = new LinkedHashMap<String,Filter>();
        filters.put("casFilter",casFilter);
        shiroFilterFactoryBean.setFilters(filters);


        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
//        filterChainDefinitionMap.put("/admin/logout", "logout");
        // 过滤链
       /* filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/plugins/**", "anon");
        filterChainDefinitionMap.put("/500.html", "perms");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/admin/mylogin", "anon");
        filterChainDefinitionMap.put("/admin/register.html", "anon"); // 注册界面
        filterChainDefinitionMap.put("/admin/register", "anon"); // 注册提交数据
        filterChainDefinitionMap.put("/admin/sencCode", "anon"); // 发送邮箱验证码
        filterChainDefinitionMap.put("/admin/isUsername/**", "anon"); // 判断用户名是否存在
        filterChainDefinitionMap.put("/admin/isEmail/**", "anon"); // 判断邮箱是否存在
*/
//        filterChainDefinitionMap.put("/**", "authc");
        /**
         * anon:所有url都都可以匿名访问;
         * authc: 需要认证才能进行访问;
         * user:配置记住我或认证通过可以访问；
         */
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

   /**
   * @Description:    安全管理器
   * @Author:         Lihaitao
   * @Date:       2018/8/24 13:43
   * @UpdateUser:
   * @UpdateRemark:
   */
    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();

        //设置自定义realm
        securityManager.setRealm(myShiroRealm());
        //设置缓存
        securityManager.setCacheManager(ehCacheManager());
        //现cas的remember me的功能
        securityManager.setSubjectFactory(new CasSubjectFactory());
        return securityManager;
    }

    /**
    * @Description:   Realm的配置
    * @Author:         Lihaitao
    * @Date:       2018/8/24 13:43
    * @UpdateUser:
    * @UpdateRemark:
    */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public ShiroCasRealm myShiroRealm() {
        ShiroCasRealm realm = new ShiroCasRealm();
        // cas登录服务器地址前缀
        realm.setCasServerUrlPrefix(ShiroConfig.casServerUrlPrefix);
        // 客户端回调地址，登录成功后的跳转地址(自己的服务地址)
        realm.setCasService(ShiroConfig.shiroServerUrlPrefix + ShiroConfig.casFilterUrlPattern);
        // 登录成功后的默认角色，此处默认为user角色
        realm.setDefaultRoles("user");
        //设置加密凭证
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }

    /**
    * @Description:    单点登出listener
    * @Author:         Lihaitao
    * @Date:       2018/8/24 15:48
    * @UpdateUser:
    * @UpdateRemark:
    */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)// 优先级
    public ServletListenerRegistrationBean<?> singleSignOutHttpSessionListener(){
        ServletListenerRegistrationBean bean = new ServletListenerRegistrationBean();
        bean.setListener(new SingleSignOutHttpSessionListener());
        bean.setEnabled(true);
        return bean;
    }

    /**
    * @Description:    单点登出过滤器
    * @Author:         Lihaitao
    * @Date:       2018/8/24 15:48
    * @UpdateUser:
    * @UpdateRemark:
    */
    @Bean
    public FilterRegistrationBean singleSignOutFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setName("singleSignOutFilter");
        bean.setFilter(new SingleSignOutFilter());
        bean.addUrlPatterns("/*");
        bean.setEnabled(true);
        return bean;
    }
    /**
    * @Description:    注册DelegatingFilterProxy
    * @Author:         Lihaitao
    * @Date:       2018/8/24 16:12
    * @UpdateUser:
    * @UpdateRemark:
    */
    @Bean
    public FilterRegistrationBean delegatingFilterProxy() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }


    /**
    * @Description:    cas过滤器
    * @Author:         Lihaitao
    * @Date:       2018/8/24 16:16
    * @UpdateUser:
    * @UpdateRemark:
    */
    @Bean(name = "casFilter")
    public CasFilter getCasFilter() {
        CasFilter casFilter = new CasFilter();
        casFilter.setName("casFilter");
        casFilter.setEnabled(true);
        casFilter.setFailureUrl("");// 认证失败后的页面
        casFilter.setLoginUrl("");
        return casFilter;
    }



    /**
   * @Description:    凭证匹配；利用MD5加盐，进行一次散列
   * @Author:         Lihaitao
   * @Date:       2018/8/24 13:43
   * @UpdateUser:
   * @UpdateRemark:
   */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1);
        return hashedCredentialsMatcher;
    }



      /**
      * @Description:  配置缓存
      * @Author:         Lihaitao
      * @Date:       2018/8/24 16:12
      * @UpdateUser:
      * @UpdateRemark:
      */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public EhCacheManager ehCacheManager(){
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:config/ehcache-shiro.xml");
        return cacheManager;
    }

    /**
    * @Description:   shiro生命周期处理器
    * @Author:         Lihaitao
    * @Date:       2018/8/24 16:10
    * @UpdateUser:
    * @UpdateRemark:
    */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
    * @Description:    开启shiro注解
    * @Author:         Lihaitao
    * @Date:       2018/8/24 16:11
    * @UpdateUser:
    * @UpdateRemark:
    */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * shiro注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


}
