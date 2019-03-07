package com.youhe.controller.sys.websocketController;

import com.youhe.biz.department.DepartmentBiz;
import com.youhe.biz.role.RoleBiz;
import com.youhe.biz.user.UserBiz;
import com.youhe.controller.loginController.LoginController;
import com.youhe.entity.department.User_Department;
import com.youhe.entity.role.Role;
import com.youhe.entity.role.User_Role;
import com.youhe.entity.user.User;
import com.youhe.initBean.websocket.WebSocketServer;
import com.youhe.serviceImpl.Controller.userController.UserControllerImpl;
import com.youhe.utils.R;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoqiang on 2018/12/19.
 */
@Controller
@RequestMapping(value = "/checkcenter")
public class WebsocketController {

    //页面请求
    @GetMapping("/socket/{cid}")
    public ModelAndView socket(@PathVariable String cid) {
        ModelAndView mav = new ModelAndView("/socket");
        mav.addObject("cid", cid);
        return mav;
    }

    //推送数据接口
    @ResponseBody
    @RequestMapping("/socket/push/{cid}")
    public R pushToWeb(@PathVariable String cid, String message) {
        try {
            WebSocketServer.sendInfo(message, cid);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error(cid + "#" + e.getMessage());
        }
        return R.ok(cid);
    }


}
