package com.baomidou.springboot.config.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.springboot.common.ConstantsPub;
import com.baomidou.springboot.domain.User;
import com.baomidou.springboot.domain.entity.Permission;
import com.baomidou.springboot.domain.entity.Role;
import com.baomidou.springboot.service.IUserService;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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
 * @Description MyShiroRealm:自定义Realm
 * @Author LiHaitao
 * @Date 2018/8/19 20:31
 * @UpdateUser
 * @UpdateDescrip
 * @UpdateDate
 * @Version 1.0.0
 **/
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IRoleService roleService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("MyShiroRealm.doGetAuthorizationInfo()");

        // 获取用户信息
        User user = (User) principalCollection.getPrimaryPrincipal();

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        // 查询用户拥有那些权限
        List<Permission> permissions = permissionService.findUserPermission(user.getId());
        List<Role> roles=roleService.findUserRole(user.getId());
        //添加角色
        List<String> roleList=new ArrayList<>();
        roles.forEach(role -> {
            roleList.add(role.getName());
        });
        List<String> permissionList = new ArrayList<String>();
        // 添加权限代码
        for (Permission item: permissions) {
            if (StringUtil.isNotEmpty(item.getPermCode()))
                permissionList.add(item.getPermCode());
        }
        authorizationInfo.addStringPermissions(permissionList);
        authorizationInfo.addRoles(roleList);

        return authorizationInfo;


    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
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
        return simpleAuthenticationInfo;
    }
}
