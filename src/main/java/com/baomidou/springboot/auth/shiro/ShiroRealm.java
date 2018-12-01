package com.baomidou.springboot.auth.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.springboot.common.ConstantsPub;
import com.baomidou.springboot.auth.entity.User;
import com.baomidou.springboot.auth.entity.Permission;
import com.baomidou.springboot.auth.entity.Role;
import com.baomidou.springboot.auth.entity.UserRole;
import com.baomidou.springboot.auth.service.IPermissionService;
import com.baomidou.springboot.auth.service.IRoleService;
import com.baomidou.springboot.auth.service.IUserRoleService;
import com.baomidou.springboot.auth.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description ShiroRealm:自定义Realm
 * @Author LiHaitao
 * @Date 2018/8/19 20:31
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
@Slf4j
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleService roleService;

    /**
    * @Description:   授权:继承CasRealm后与普通的Realm一样
    * @Author:         Lihaitao
    * @Date:       2018/8/24 13:42
    * @UpdateUser:
    * @UpdateRemark:
    */
    /**
     * 此方法调用  hasRole,hasPermission的时候才会进行回调.
     *
     * 权限信息.(授权):
     * 1、如果用户正常退出，缓存自动清空；
     * 2、如果用户非正常退出，缓存自动清空；
     * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。
     * （需要手动编程进行实现；放在service进行调用）
     * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例，
     * 调用clearCached方法；
     * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("ShiroRealm.doGetAuthorizationInfo()");
        //显式退出
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            doClearCache(principalCollection);
            SecurityUtils.getSubject().logout();
            return null;
        }
        // 获取用户信息
        User user = (User) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        // 查询用户拥有那些权限
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",user.getId());
        List<Permission> permissions = permissionService.findUserPermission(user.getId());
        //查询用户拥有哪些角色
        List<UserRole> userRoles=userRoleService.selectList(queryWrapper);
        List<Role> roles=new ArrayList<>();
                userRoles.forEach(userRole -> {
            roles.add(roleService.selectById(userRole.getRoleId()));
        });

        //添加角色
        List<String> roleList=new ArrayList<>();
        roles.forEach(role -> {
            roleList.add(role.getName());
        });

        // 添加权限代码
        List<String> permissionList = new ArrayList<String>();
        permissions.forEach(permission -> {
            permissionList.add(permission.getName());
        });
        authorizationInfo.addStringPermissions(permissionList);
        authorizationInfo.addRoles(roleList);
        return authorizationInfo;
    }

   /**
   * @Description:    认证
   * @Author:         Lihaitao
   * @Date:       2018/8/24 13:42
   * @UpdateUser:  2018/8/25 13:30
   * @UpdateRemark:
   */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //shiro认证
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        log.info("验证当前Subject时获取到token为：" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
        String username= (String) authenticationToken.getPrincipal();
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("name",username);
        User user=userService.selectOne(queryWrapper);
        if(user==null){
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(),
                ByteSource.Util.bytes(ConstantsPub.salt),getName());
        //将用户信息放入session
        SecurityUtils.getSubject().getSession().setAttribute("user", user);
        return simpleAuthenticationInfo;


/*
        //CasRealm实现了单点认证。调用父类方法，无法加盐
        AuthenticationInfo authc = super.doGetAuthenticationInfo(authenticationToken);
        // 获取登录用户名
        String name = (String) authc.getPrincipals().getPrimaryPrincipal();
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("name",name);
        UserClient user=userService.selectOne(queryWrapper);
        if(user==null){
            return null;
        }

        SecurityUtils.getSubject().getSession().setAttribute("user", name);
        return authc;*/


    }
}
