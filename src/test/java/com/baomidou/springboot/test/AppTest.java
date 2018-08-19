package com.baomidou.springboot.test;

import cn.hutool.system.UserInfo;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
* @Description:    shiro
* @Author:         LiHaitao
* @CreateDate:     2018/8/19 11:51
* @UpdateUser:
* @UpdateDate:     2018/8/19 11:51
* @UpdateRemark:
* @Version:        1.0.0
*/
public class AppTest {

    SimpleAccountRealm accountRealm=new SimpleAccountRealm();

    @Before
    public void before(){
        accountRealm.addAccount("lihaitao","123456","admin","userAdmin");//可变参数，多个角色添加
    }

    @Test
    public void test(){

        DefaultSecurityManager defaultSecurityManager=new DefaultSecurityManager();
        defaultSecurityManager.setRealm(accountRealm);
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        //获取主体
        Subject subject=SecurityUtils.getSubject();
        UsernamePasswordToken token=new UsernamePasswordToken("lihaitao","123456");
        subject.login(token);
        System.out.println(subject.isAuthenticated());
        /*subject.logout();
        System.out.println(subject.isAuthenticated());
        */
        subject.checkRole("admin");
        subject.checkRoles("admin","userAdmin");
    }
}
