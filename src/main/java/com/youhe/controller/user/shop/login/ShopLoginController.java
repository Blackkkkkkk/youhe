package com.youhe.controller.user.shop.login;

import com.youhe.biz.order.AddressBiz;
import com.youhe.biz.user.UserBiz;
import com.youhe.entity.order.Address;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 商城登录
 *
 * @author Kalvin
 */
@RestController
@RequestMapping(value = "touristShop")
public class ShopLoginController {

    @Autowired
    private UserBiz userBiz;

    @Autowired
    private AddressBiz addressBiz;

    private final static Logger LOGGER = LoggerFactory.getLogger(ShopLoginController.class);

    @GetMapping(value = "login")
    public ModelAndView index() {
        return new ModelAndView("user/shop/login/shop_login");
    }

    /**
     * 登录接口
     *
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
     *
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


    @GetMapping(value = "userInfo")
    public R userInfo() {

        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        User user = new User();

        user.setUid(shiroUser.getUid());
        // user.setType(2);

        List<User> list = userBiz.findOnlyUserList(user);
        if (!CollectionUtils.isEmpty(list)) {
            user = list.get(0);
        }

        return R.ok().put("user", user);
    }


    @PostMapping(value = "updateUserInfo")
    public R updateUserInfo(User user) {
        try {
            userBiz.update(user);
            List<User> list = userBiz.findOnlyUserList(user);
            if (!CollectionUtils.isEmpty(list)) {
                user = list.get(0);
            }
            return R.ok("修改成功").put("user", user);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return R.error("修改失败，请联系管理员！");
        }


    }

    @PostMapping(value = "updateUserPassword")
    public R updateUserPassword(User user) {
        Boolean status = false;

        try {
            String password = user.getUserPassword();
            String newPassword = user.getNewPassword();

            ShiroUser shiroUser = ShiroUserUtils.getShiroUser();

            List<User> list = userBiz.findOnlyUserList(user);
            if (!CollectionUtils.isEmpty(list)) {
                user = list.get(0);
            }

            if (ShiroUserUtils.checkPasswordByMeixiang(user, password)) {
                user.setUserPassword(newPassword);
                ShiroUserUtils.encryptPassword(user);
                userBiz.update(user);
                return R.ok("修改成功！");
            } else {
                return R.error("旧密码输入有误，请重新输入！");

            }
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return R.error("修改失败，请联系管理员！");
        }

    }

    @PostMapping(value = "addressSave")
    public R addressSave(Address address) {

        try {
            ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            address.setUserId(Integer.parseInt(shiroUser.getUid() + ""));
            addressBiz.save(address);

            address = new Address();
            address.setUserId(Integer.parseInt(shiroUser.getUid() + ""));
            List<Address> list = addressBiz.addreddList(address);
            return R.ok("新增成功").put("list", list);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return R.error("新增失败");
        }

    }


    @PostMapping(value = "addressUpdate")
    public R addressUpdate(Address address) {
        try {
            ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            address.setUserId(Integer.parseInt(shiroUser.getUid() + ""));
            addressBiz.updates(address);
            address = new Address();
            address.setUserId(Integer.parseInt(shiroUser.getUid() + ""));
            List<Address> list = addressBiz.addreddList(address);
            return R.ok("修改成功").put("list", list);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return R.error("修改失败");
        }
    }


    @GetMapping(value = "addressList")
    public R addressList(Address address) {
        try {
            ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
            address.setUserId(Integer.parseInt(shiroUser.getUid() + ""));
            List<Address> list = addressBiz.addreddList(address);
            return R.ok().put("list", list);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            return R.error();
        }

    }

}
