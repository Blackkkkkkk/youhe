package com.youhe.controller.loginController;

import com.youhe.biz.test.TestBiz;
import com.youhe.entity.user.User;
import com.youhe.mapper.test.TestMapper;
import com.youhe.service.test.TestService;
import com.youhe.service.user.UserService;
import com.youhe.utils.shiro.AuthRealm;
import com.youhe.utils.shiro.InitUsernamePasswordToken;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import com.youhe.utils.workflow.ExportFlow;
import org.activiti.engine.ProcessEngine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


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


    @Autowired
    private UserService userService;

    @Autowired
    ProcessEngine processEngine;


    @RequestMapping(value = "/")
    public String login() {


        return "login/login";
    }

    @RequestMapping(value = "/login")
    public String index(Model model, User user) {

        //  ShiroUserUtils.encryptPassword(user);
        // userService.update(user);


        Subject subject = SecurityUtils.getSubject();
        try {
            User userLogin = new User();

            userLogin.setUserAccount(user.getUserAccount());
            userService.findUserList(userLogin);

            List<User> list = userService.findUserList(userLogin);
            if (!CollectionUtils.isEmpty(list)) {
                userLogin = list.get(0);

            }

            InitUsernamePasswordToken initUsernamePasswordToken = new InitUsernamePasswordToken(userLogin.getUserAccount(), userLogin.getUserPassword(), 1, userLogin.getSalt());


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

        ExportFlow export = new ExportFlow();

      //  export.Export(processEngine,"4");

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
        return "redirect:login/login";
    }

}
