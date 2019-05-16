package com.youhe.controller.sys.permission;

import com.youhe.biz.permisson.PermissonBiz;
import com.youhe.biz.user.UserBiz;
import com.youhe.controller.loginController.LoginController;
import com.youhe.entity.permission.Permission;
import com.youhe.entity.permission.Permission_Role;
import com.youhe.entity.user.User;
import com.youhe.utils.R;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;


import javax.annotation.Resource;
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

    @Resource
    private ThymeleafViewResolver thymeleafViewResolver;
    @Autowired
    private PermissonBiz permissonBiz;
    @Autowired
    private UserBiz userBiz;


    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/index")
    public String index() {
        System.out.println("123");
        return "sys/menu/permission";
    }



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

    /**
     * 上级部门Id(管理员则为0)
     */
    @RequestMapping("/info")
    @ResponseBody
    //@RequiresPermissions("sys:dept:list")
    public Map<String, Long> info() {
        Map<String, Long> map = new HashMap<String, Long>();
        long pId = 0;
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        if (!shiroUser.getUserAccount().equals("admin")) {

            User user = new User();
            user.setUserAccount(shiroUser.getUserAccount());

            if (!CollectionUtils.isEmpty(userBiz.findOnlyUserList(user))) {
                user = userBiz.findOnlyUserList(user).get(0);
                pId = Long.parseLong(user.getDepartmentId());
            }
        }
        map.put("pId", pId);
        return map;
    }



    /**
     * 选择部门(添加、修改菜单)
     */
    @RequestMapping("/select")
    @ResponseBody
    public Map<String, List<Permission>> select(Permission permission) {
        Map<String, List<Permission>> map = new HashMap<String, List<Permission>>();
        List<Permission> permissionList = permissonBiz.findPermissionList(permission);

        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        //添加一级部门
        if (shiroUser.getUserAccount().equals("admin")) {
            Permission root = new Permission();
            root.setPid(0L);
            root.setPname("一级菜单");
            root.setParentid(-1L);
            root.setOpen(true);
            permissionList.add(root);
        }
        map.put("permissionList", permissionList);
        return map;
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @ResponseBody
    public R save(@RequestBody Permission dept) {
        permissonBiz.add(dept);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public R update(@RequestBody Permission dept) {
        permissonBiz.update(dept);
        Map<String, Object> vars = new HashMap<>();
        List<Permission> mentList = permissonBiz.selectMentLists();
        vars.put("mentlist",mentList);
        thymeleafViewResolver.setStaticVariables(vars);
        return R.ok();
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{pid}")
    @ResponseBody
    public R info(@PathVariable("pid") Long pid) {
        Permission permission = new Permission();
        permission.setPid(pid);
        try {
            permission = permissonBiz.findDepartMentList(permission).get(0);
        } catch (Exception e) {
            log.info("/info/{deptId}: 查询为空");
        }


        return R.ok().put("dept", permission);
    }

    /**
     * 删除
     */
    @RequestMapping("/del")
    @ResponseBody
    public R del(Permission dept) {
        permissonBiz.del(dept);
        Map<String, Object> vars = new HashMap<>();
        List<Permission> mentList = permissonBiz.selectMentLists();
        vars.put("mentlist",mentList);
        thymeleafViewResolver.setStaticVariables(vars);
        return R.ok();
    }

}
