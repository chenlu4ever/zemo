package com.java.zemo.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author 10450
 * @description TODO
 * @date 2021/7/22 17:57
 */
public class LoginAuthorizingRealm extends AuthorizingRealm {


    @Override
    //身份认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("============用户认证==============");
        //验证账号密码是否正确,这样简单定义了一个账号，模拟数据库中的用户，这里存放了两个用户
        Map<String,String> userInfo=new HashMap<>();
        userInfo.put("admin","123");
        userInfo.put("user","1234");
        //这里强转的类型不一定要是UsernamePasswordToken ，具体要看你在登录接口中所传的对象类型
        UsernamePasswordToken usernamePasswordToken=  (UsernamePasswordToken)token;
        String username = usernamePasswordToken.getUsername();
        //判断内容是否为空
        if (StringUtils.isEmpty(username)){
            throw new AccountException("用户名不能为空");
        }

        //判断用户是否存在，
        //这里的思路是：判断数据库中是否存在该用户
        //至于密码校验，我们只需要在下面传递给shiro的认证器，它会帮我们做校验
        String password= userInfo.get("admin");
        if (StringUtils.isEmpty(password)){
            throw new UnknownAccountException("用户不存在");
        }
        //这里有三个参数，第一个是账号，第二个是密码，第三个是用户名
        //这里的密码需要传递用户的正确密码，这样shiro才能帮我们做校验
        SimpleAuthenticationInfo simpleAuthenticationInfo=new SimpleAuthenticationInfo(username,password,"admin");

        return simpleAuthenticationInfo;
    }

    @Override
    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("============用户授权==============");
        String username  =(String) getAvailablePrincipal(principals);
        //系统角色
        Set<String> roles=new HashSet<>();
        roles.add("超级管理员");
        //角色所用户的权限
        Set<String> permissions=new HashSet<>();
        //这里定义了一个list的权限，这个可以不一定要这样写，只要跟后面再controller中的注解一致即可
        permissions.add("admin:shiro:list");
        //为当前用户添加角色和权限
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.setRoles(roles);
        info.setStringPermissions(permissions);
        return info;
    }

}
