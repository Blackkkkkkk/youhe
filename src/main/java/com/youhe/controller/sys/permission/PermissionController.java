package com.youhe.controller.sys.permission;

import com.youhe.biz.permisson.PermissonBiz;
import com.youhe.biz.role.RoleBiz;
import com.youhe.biz.user.UserBiz;
import com.youhe.controller.loginController.LoginController;
import com.youhe.controller.sys.department.DepartmentController;
import com.youhe.entity.permission.Permission;
import com.youhe.entity.permission.Permission_Role;
import com.youhe.entity.role.Role;
import com.youhe.utils.R;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoqiang on 2018/12/19.
 */
@Controller
@RequestMapping(value = "/permission")
public class PermissionController {


    @Autowired
    private PermissonBiz permissonBiz;


    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    /**
     * @param permission 获取权限列表
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<Permission> list(Permission permission) {

        List<Permission> permissionsList = permissonBiz.findPermissionList(permission);

        return permissionsList;
    }

    /**
     * @param permission_role 获取角色权限
     * @return
     */
    @RequestMapping("/permission_Rolelist")
    @ResponseBody
    public List<Long> permission_Rolelist(Permission_Role permission_role) {


        List<Permission_Role> permission_roleList = permissonBiz.find_permission_roleList(permission_role);

        List<Long> list = new ArrayList<Long>();


        for (Permission_Role plist : permission_roleList) {
            System.out.println(plist.getSys_permission_id());
            list.add(Long.parseLong(plist.getSys_permission_id() + ""));
        }

        return list;
    }

}
