package com.youhe.controller.user.shop.login;

import com.youhe.biz.user.UserBiz;
import com.youhe.entity.user.User;
import com.youhe.utils.R;
import com.youhe.utils.shiro.InitUsernamePasswordToken;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 商城登录
 * @author Kalvin
 */
@RestController
@RequestMapping(value = "touristShop")
public class ShopLoginController {

    @Autowired
    private UserBiz userBiz;

    private final static Logger LOGGER = LoggerFactory.getLogger(ShopLoginController.class);

    @GetMapping(value = "login")
    public ModelAndView index() {
        return new ModelAndView("user/shop/login/shop_login");
    }

    /**
     * 登录接口
     * @param user
     * @return
     */
    @PostMapping(value = "login")
    public R login(User user) {
        Subject subject = SecurityUtils.getSubject();
        try {
            User userLogin = new User();

            userLogin.setUserAccount(user.getUserAccount());
            userBiz.findUserList(userLogin);

            List<User> list = userBiz.findOnlyUserList(userLogin);
            if (!CollectionUtils.isEmpty(list)) {
                userLogin = list.get(0);
            }
            InitUsernamePasswordToken initUsernamePasswordToken = new InitUsernamePasswordToken(userLogin.getUserAccount(), user.getUserPassword(), 1, userLogin.getSalt());
            subject.login(initUsernamePasswordToken);   //完成登录

        } catch (AuthenticationException e) {
            return R.error("登录时发生异常" + e.getMessage());
        }
        return R.ok();
    }

    @GetMapping(value = "checkLogin")
    public R checkLogin() {
        Subject subject = SecurityUtils.getSubject();
        return R.ok().put("isLogin", subject.isAuthenticated());
    }

    /**
     * 退出登录
     * @return
     */
    @PostMapping(value = "loinOut")
    public R loginOut() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.getSession() != null) {
            ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            if (shiroUser != null) {
                currentUser.logout();
                LOGGER.debug(shiroUser.getUserName() + ">>>>>>>>>>>>退出登录");
                return R.ok();
            }
        }
        return R.error();
    }

}
