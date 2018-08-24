package com.baomidou.springboot.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.springboot.common.ConstantsPub;
import com.baomidou.springboot.domain.User;
import com.baomidou.springboot.domain.entity.Permission;
import com.baomidou.springboot.domain.entity.Role;
import com.baomidou.springboot.domain.entity.UserRole;
import com.baomidou.springboot.service.IPermissionService;
import com.baomidou.springboot.service.IRoleService;
import com.baomidou.springboot.service.IUserRoleService;
import com.baomidou.springboot.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasAuthenticationException;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description ShiroCasRealm:自定义Realm
 * @Author LiHaitao
 * @Date 2018/8/19 20:31
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
@Slf4j
public class ShiroCasRealm extends CasRealm {

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
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("ShiroCasRealm.doGetAuthorizationInfo()");
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
   * @UpdateUser:
   * @UpdateRemark:
   */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //重写的CasRealm的doGetAuthenticationInfo，只是加了盐，
        CasToken casToken = (CasToken)authenticationToken;
        if (authenticationToken == null) {
            return null;
        } else {
            String ticket = (String)casToken.getCredentials();
            if (!StringUtils.hasText(ticket)) {
                return null;
            } else {
                TicketValidator ticketValidator = this.ensureTicketValidator();
                try {
                    Assertion casAssertion = ticketValidator.validate(ticket, this.getCasService());
                    AttributePrincipal casPrincipal = casAssertion.getPrincipal();
                    String userId = casPrincipal.getName();
                    log.debug("Validate ticket : {} in CAS server : {} to retrieve user : {}", new Object[]{ticket, this.getCasServerUrlPrefix(), userId});
                    Map<String, Object> attributes = casPrincipal.getAttributes();
                    casToken.setUserId(userId);
                    String rememberMeAttributeName = this.getRememberMeAttributeName();
                    String rememberMeStringValue = (String)attributes.get(rememberMeAttributeName);
                    boolean isRemembered = rememberMeStringValue != null && Boolean.parseBoolean(rememberMeStringValue);
                    if (isRemembered) {
                        casToken.setRememberMe(true);
                    }
                    List<Object> principals = CollectionUtils.asList(new Object[]{userId, attributes});
                    PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, this.getName());
                    //进行加盐
                    SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(principalCollection, ticket);
                    simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(ConstantsPub.salt));
                    // 获取登录用户名，验证
                    String name = (String) simpleAuthenticationInfo.getPrincipals().getPrimaryPrincipal();
                    QueryWrapper queryWrapper=new QueryWrapper();
                    queryWrapper.eq("name",name);
                    User user=userService.selectOne(queryWrapper);
                    if(user==null){
                        return null;
                    }
                    //放入session
                    SecurityUtils.getSubject().getSession().setAttribute("user", user);
                    return simpleAuthenticationInfo;
                } catch (TicketValidationException var14) {
                    throw new CasAuthenticationException("Unable to validate ticket [" + ticket + "]", var14);
                }
            }
        }




/*
        //CasRealm实现了单点认证。调用父类方法，无法加盐
        AuthenticationInfo authc = super.doGetAuthenticationInfo(authenticationToken);
        // 获取登录用户名
        String name = (String) authc.getPrincipals().getPrimaryPrincipal();
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("name",name);
        User user=userService.selectOne(queryWrapper);
        if(user==null){
            return null;
        }

        SecurityUtils.getSubject().getSession().setAttribute("user", name);
        return authc;*/

/*      //shiro认证
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
        return simpleAuthenticationInfo;*/
    }
}
