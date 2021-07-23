package com.java.zemo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 10450
 * @description TODO
 * @date 2021/7/23 10:58
 */
@RestController
@RequestMapping("admin/")
public class ShiroController {
    @RequestMapping("login")
    public String login(String username, String password) {
        //获取主体
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username, password));
        } catch (UnknownAccountException e) {
            return "账号不存在";
        } catch (AccountException e) {
            return "账号或密码不正确";
        } catch (IncorrectCredentialsException e) {
            return "账号或密码不正确";
        }

        return "登陆成功";
    }

    @RequestMapping("401")
    public String toLogin() {
        return "401页面,您先去登陆吧！";
    }

    @RequestMapping("index")
    public String index() {
        return "index主页";
    }

    @RequiresPermissions("admin:shiro:list")
    @RequestMapping("list")
    public String list() {
        return "list";
    }

    @RequiresPermissions("admin:shiro:add")
    @RequestMapping("add")
    public String add() {
        return "add";
    }

    @RequestMapping("unAuth")
    public String unAuth() {
        return "未认证";
    }
}
