package com.youhe.initBean.shiro;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.youhe.utils.shiro.AuthRealm;
import com.youhe.utils.shiro.CredentialsMatcher;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.shiro.mgt.SecurityManager;


import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;


/**
 * shiro的配置类
 *
 * @author Administrator
 */
@Configuration
public class ShiroConfiguration {



    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(manager);




        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        //配置登录的url和登录成功的url
        bean.setLoginUrl("/");
        bean.setSuccessUrl("/index");
        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("login/login.html*", "anon"); //表示可以匿名访问
        filterChainDefinitionMap.put("/login/**", "anon"); //匿名访问静态资源

        //静态资源
        // filterChainDefinitionMap.put("/**", "anon");
        filterChainDefinitionMap.put("/bootstrap/css/*", "anon"); //匿名访问静态资源
        filterChainDefinitionMap.put("/bootstrap/js/*", "anon"); //匿名访问静态资源
        filterChainDefinitionMap.put("/bootstrap/fonts/*", "anon"); //匿名访问静态资源
        filterChainDefinitionMap.put("/bootstrap/font-awesome/*", "anon"); //匿名访问静态资源
        filterChainDefinitionMap.put("/bootstrap/img/*", "anon"); //匿名访问静态资源
        filterChainDefinitionMap.put("/bootstrap/locales/*", "anon"); //匿名访问静态资源

        filterChainDefinitionMap.put("/*", "authc");//表示需要认证才可以访问
        filterChainDefinitionMap.put("/**", "authc");//表示需要认证才可以访问
        filterChainDefinitionMap.put("/*.*", "authc");


        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    //配置核心安全事务管理器
    @Bean(name = "securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm) {
        System.err.println("--------------shiro已经加载----------------");
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(authRealm);
        return manager;
    }

    //配置自定义的权限登录器
    @Bean(name = "authRealm")
    public AuthRealm authRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
        AuthRealm authRealm = new AuthRealm();
        authRealm.setCredentialsMatcher(matcher);
        return authRealm;
    }

    //配置自定义的密码比较器
    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }

    //处理shiro 页面标签问题
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }


    @Bean("authorizer")
    public Authorizer authorizer(Realm... realms) {
        ModularRealmAuthorizer authenticator = new ModularRealmAuthorizer();
        Collection<Realm> crealms = new ArrayList<>(realms.length);
        for (Realm oneRealm : realms) {
            crealms.add(oneRealm);
        }
        authenticator.setRealms(crealms);
        return authenticator;
    }

    @Bean("authenticator")
    public Authenticator authenticator(Realm... realms) {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        Collection<Realm> crealms = new ArrayList<>(realms.length);
        for (Realm oneRealm : realms) {
            crealms.add(oneRealm);
        }
        authenticator.setRealms(crealms);
        return authenticator;
    }


}