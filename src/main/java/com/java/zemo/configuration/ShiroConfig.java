package com.java.zemo.configuration;

import com.java.zemo.shiro.LoginAuthorizingRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 10450
 * @description TODO
 * @date 2021/7/22 17:56
 */
@Configuration
public class ShiroConfig {
    //配置realm
    @Bean
    public Realm realm(){
        return  new LoginAuthorizingRealm();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //这里定义用户未认证时跳转的路径
        shiroFilterFactoryBean.setLoginUrl("/admin/401");
        //这里是用户登录成功后跳转的路径
        shiroFilterFactoryBean.setSuccessUrl("/admin/index");
        //这里是用户没有访问权限跳转的路径
        // 但是这个配置无效，只有perms，roles，ssl，rest，port才是属于AuthorizationFilter，
        // 而anon，authcBasic，authc，user是AuthenticationFilter
        shiroFilterFactoryBean.setUnauthorizedUrl("/admin/unAuth");

        //自定义拦截器限制并发人数,参考博客：
//        LinkedHashMap<String, Filter> filtersMap = new LinkedHashMap<>();
        //限制同一帐号同时在线的个数
        //filtersMap.put("kickout", kickoutSessionControlFilter());
        //统计登录人数
//        shiroFilterFactoryBean.setFilters(filtersMap);

        // 配置访问权限 必须是LinkedHashMap，因为它必须保证有序
        // 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 --> : 这是一个坑，一不小心代码就不好使了
        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
        Map<String,String> filterChainDefinitionMap=new LinkedHashMap<>();
        filterChainDefinitionMap.put("/admin/login","anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");

        //解锁用户专用 测试用的
        filterChainDefinitionMap.put("/unlockAccount","anon");
        filterChainDefinitionMap.put("/Captcha.jpg","anon");
        //logout是shiro提供的过滤器
        filterChainDefinitionMap.put("/logout", "logout");

        filterChainDefinitionMap.put("/admin/unAuth","anon");
        filterChainDefinitionMap.put("/admin/401","anon");


        //其他资源都需要认证  authc 表示需要认证才能进行访问 user表示配置记住我或认证通过可以访问的地址
        //如果开启限制同一账号登录,改为 .put("/**", "kickout,user");
        filterChainDefinitionMap.put("/**","authc");
//        filterChainDefinitionMap.put("/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }
    //    将realm加入管理器中

    /**
     * 配置核心安全事务管理器
     * @return
     */
    @Bean(name="securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager=new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm());
        return defaultWebSecurityManager;
    }


    /**
     * @Title: authorizationAttributeSourceAdvisor
     * @Description:开启shiro提供的权限相关的注解
     * @param securityManager
     * @return AuthorizationAttributeSourceAdvisor
     **/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }


}
