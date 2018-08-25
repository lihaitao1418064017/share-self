package com.baomidou.springboot.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description ShiroConfig:shiro配置
 * @Author LiHaitao
 * @Date 2018/8/19 20:23
 * @UpdateUser
 * @UpdateDescrip  去掉CasRealm的认证方式
 * @UpdateDate   2018/8/25 13:37
 * @Version 1.0.0
 **/
@Configuration
public class ShiroConfig {

    private static final transient Logger log = LoggerFactory.getLogger(ShiroConfig.class);

    /**
    * @Description:   过滤器
    * @Author:         Lihaitao
    * @Date:       2018/8/24 13:43
    * @UpdateUser: lihaitao
    * @UpdateRemark:
    */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();

        // SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

       /* // 登陆页面
        shiroFilterFactoryBean.setLoginUrl("/admin/login.html");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/admin/index");
        // 未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/500.html");*/



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
        securityManager.setRealm(shiroRealm());
        //设置缓存
        securityManager.setCacheManager(ehCacheManager());
        //设置记住我管理器
        securityManager.setRememberMeManager(rememberMeManager());

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
    public ShiroRealm shiroRealm() {
        ShiroRealm realm = new ShiroRealm();
        realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }
    /**
    * @Description:   cookie管理对象
    * @Author:         Lihaitao
    * @Date:       2018/8/25 20:33
    * @UpdateUser:
    * @UpdateRemark:
    */
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }
   /**
   * @Description:    cookie对象
   * @Author:         Lihaitao
   * @Date:       2018/8/25 20:33
   * @UpdateUser:
   * @UpdateRemark:
   */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }


    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        return sessionManager;
    }
    @Bean
    public SessionDAO sessionDAO(){
        //LocalSessionDAO sessionDao = new LocalSessionDAO();
        EnterpriseCacheSessionDAO sessionDao = new EnterpriseCacheSessionDAO();
        sessionDao.setCacheManager(ehCacheManager());
        sessionDao.setActiveSessionsCacheName("shiro-activeSessionCache");
        sessionDao.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
        return sessionDao;
    }
    public SimpleCookie sessionIdCookie(){
        return new SimpleCookie("share.session.id");
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
        HashedCredentialsMatcher hashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher(ehCacheManager());
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
    @Bean(name = "lifecycleBeanPostProcessor")
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
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启shiro aop注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    public KickoutSessionControlFilter 	kickoutSessionControlFilter(){
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
        //这里我们还是用之前shiro使用的redisManager()实现的cacheManager()缓存管理
        //也可以重新另写一个，重新配置缓存时间之类的自定义缓存属性
        kickoutSessionControlFilter.setCacheManager(ehCacheManager());
        //用于根据会话ID，获取会话进行踢出操作的；
        kickoutSessionControlFilter.setSessionManager(sessionManager());
        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；踢出顺序。
        kickoutSessionControlFilter.setKickoutAfter(false);
        //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
        kickoutSessionControlFilter.setMaxSession(1);
        //被踢出后重定向到的地址；
        kickoutSessionControlFilter.setKickoutUrl("/login");
        return kickoutSessionControlFilter;
    }

}
