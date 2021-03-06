package com.youhe.controller.loginController;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.youhe.biz.permisson.PermissonBiz;
import com.youhe.biz.user.UserBiz;
import com.youhe.entity.permission.Permission;
import com.youhe.entity.user.User;
import com.youhe.mapper.user.UserMapper;
import com.youhe.utils.shiro.InitUsernamePasswordToken;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * <p>
 * Title:LoginController
 * </p>
 * <p>
 * Description:用户登陆
 * </p>
 * <p>
 * Company:xxxx
 * </p>
 *
 * @author 谢伟强
 * @version 1.0
 */

@Controller
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @Resource
    private ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    private PermissonBiz permissonBiz;
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/")
    public String login() {
        Subject subject = SecurityUtils.getSubject();
        boolean authenticated = subject.isAuthenticated();
        if (authenticated) {
            return "redirect:index";
        }
        return "login/login";
    }

    @RequestMapping(value = "/login")
    public String index(Model model, User user) {

        Subject subject = SecurityUtils.getSubject();
        try {
            User userInfo = userBiz.getOne(new QueryWrapper<User>().lambda().eq(User::getUserAccount, user.getUserAccount()));

            InitUsernamePasswordToken initUsernamePasswordToken = new InitUsernamePasswordToken(user.getUserAccount(), user.getUserPassword(), 1, userInfo.getSalt());
            subject.login(initUsernamePasswordToken);   //完成登录
            model.addAttribute("message", "0");

        } catch (AuthenticationException e) {
            String error = e.getClass().toString();
            if (error.contains("UnknownAccountException")) {

                model.addAttribute("message", "1");
            }
            if (error.contains("IncorrectCredentialsException")) {
                model.addAttribute("message", "2");
            }
            if (error.contains("DisabledAccountException")) {
                model.addAttribute("message", "3");
            }
            if (error.contains("AuthenticationException")) {

                model.addAttribute("message", "4");
            }
            log.error(error + ">>>>>>>>>>>>登录出错");
        }

        return "redirect:index";
    }


    @RequestMapping(value = "/index")
    public String index() {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Map<String, Object> vars = new HashMap<>();
        List<Permission> mentList = permissonBiz.selectMentLists();
        User user=userMapper.findUser(userAccount);
        vars.put("user",user);
        vars.put("mentlist",mentList);
        thymeleafViewResolver.setStaticVariables(vars);
        log.debug(shiroUser.getUserName() + ">>>>>>>>>>>>登录成功");
        return "index/index";
    }


    @RequestMapping(value = "/bootm.do")
    public String bootm() {
        return "index/bootm";
    }


    @RequestMapping(value = "/logout")
    public String logout(Model model) {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.getSession() != null) {
            ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            if (shiroUser != null) {
                currentUser.logout();
                log.debug(shiroUser.getUserName() + ">>>>>>>>>>>>退出登录");
            }
        }
        return "redirect:";
    }


    //国际化切换
    @RequestMapping(value = "/changeLang")
    @ResponseBody
    public String il8nChange(String lang, HttpServletRequest request) {

        if ("zh_cn".equals(lang)) {
            Locale locale = new Locale("zh", "CN");
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
            request.getSession().setAttribute("lan", "zh_CN");
        } else if ("en_us".equals(lang)) {
            Locale locale = new Locale("en", "US");
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
            request.getSession().setAttribute("lan", "en_US");
        } else {
            request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, LocaleContextHolder.getLocale());
            request.getSession().setAttribute("lan", "zh_CN");
        }
        return JSON.toJSONString("success");
    }


    @RequestMapping(value = "/skin-config")
    public String skin() {
        return "skin-config";
    }


}
