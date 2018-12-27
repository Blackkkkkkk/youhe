package com.youhe.controller.sys.role;

import com.youhe.biz.role.RoleBiz;
import com.youhe.biz.user.UserBiz;
import com.youhe.controller.loginController.LoginController;
import com.youhe.controller.sys.department.DepartmentController;
import com.youhe.entity.department.User_Department;
import com.youhe.entity.role.Role;
import com.youhe.entity.role.User_Role;
import com.youhe.serviceImpl.Controller.roleController.RoleControllerImpl;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoqiang on 2018/12/19.
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController {


    @Autowired
    private RoleBiz roleBiz;

    @Autowired
    private UserBiz userBiz;

    @Autowired
    private RoleControllerImpl roleControllerImpl;


    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/index")
    public String index() {

        return "sys/role/roleManage";
    }


    /**
     * 上级角色Id(管理员则为0)
     */
    @RequestMapping("/info")
    @ResponseBody
    //@RequiresPermissions("sys:dept:list")
    public Map<String, Long> info() {

        DepartmentController departmentController = new DepartmentController();

        return departmentController.info();
    }

    /**
     * @param role 获取角色
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<Role> list(Role role) {
        List<Role> roleList = roleBiz.findRoleList(role);

        return roleList;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{deptId}")
    @ResponseBody
    public R info(@PathVariable("deptId") Long deptId) {
        Role role = new Role();
        role.setRid(deptId + "");

        try {
            role = roleBiz.findRoleList(role).get(0);
        } catch (Exception e) {
            log.info("/info/{deptId}: 查询为空");
        }

        return R.ok().put("dept", role);
    }


    /**
     * 选择部门(添加、修改菜单)
     */
    @RequestMapping("/select")
    @ResponseBody
    public Map<String, List<Role>> select(Role role) {
        Map<String, List<Role>> map = new HashMap<String, List<Role>>();
        List<Role> deptList = roleBiz.findRoleList(role);

        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();

        /*
        //添加一级部门
        if (shiroUser.getUserAccount().equals("admin")) {
            Role root = new Role();
            root.setRid("0");
            root.setParentName("暂无上级部门");
            root.setParentId(0L);
            root.setOpen(true);
            deptList.add(root);
        }*/
        map.put("deptList", deptList);
        return map;
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @ResponseBody
    public R save(@RequestBody Role role) {
        try {
            roleControllerImpl.save(role);
            return R.ok();
        }catch (Exception e){
            log.info("/role/update: 角色保存失败:" + e.toString());
            return R.error("保存失败！");
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public R update(@RequestBody Role role) {

        try {
            roleControllerImpl.update(role);
            return R.ok();
        }catch (Exception e){

            log.info("/role/update: 角色更新失败:" + e.toString());
            return R.error("更新失败！");
        }
    }



    /**
     * 删除
     */
    @RequestMapping("/del")
    @ResponseBody
    public R del(Role role) {

        try {
            roleControllerImpl.del(role);

        } catch (Exception e) {

            log.info("/user/del: 用户删除失败:" + e.toString());
            return R.error("删除用户失败");
        }

        return R.ok();
    }


}
