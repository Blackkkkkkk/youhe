package com.youhe.controller.sys.department;

import com.youhe.biz.department.DepartmentBiz;
import com.youhe.biz.user.UserBiz;
import com.youhe.controller.loginController.LoginController;
import com.youhe.entity.department.Department;

import com.youhe.entity.user.User;
import com.youhe.utils.R;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.activiti.engine.impl.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
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
@RequestMapping(value = "/department")
public class DepartmentController {

    @Autowired
    private DepartmentBiz departmentBiz;

    @Autowired
    private UserBiz userBiz;


    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/index")
    public String index() {

        System.out.println("123");
        return "sys/dept/department";
    }


    /**
     * 上级部门Id(管理员则为0)
     */
    @RequestMapping("/info")
    @ResponseBody
    //@RequiresPermissions("sys:dept:list")
    public Map<String, Long> info() {
        Map<String, Long> map = new HashMap<String, Long>();
        long deptId = 0;
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        if (!shiroUser.getUserAccount().equals("admin")) {

            User user = new User();
            user.setUserAccount(shiroUser.getUserAccount());

            if (!CollectionUtils.isEmpty(userBiz.findOnlyUserList(user))) {
                user = userBiz.findOnlyUserList(user).get(0);
                deptId = Long.parseLong(user.getDepartmentId());
            }

        }
        map.put("deptId", deptId);
        return map;
    }


    @RequestMapping("/list")
    @ResponseBody
    public List<Department> list(Department department) {
        List<Department> deptList = departmentBiz.findDepartMentList(department);

        return deptList;
    }


    /**
     * 选择部门(添加、修改菜单)
     */
    @RequestMapping("/select")
    @ResponseBody
    public Map<String, List<Department>> select(Department department) {
        Map<String, List<Department>> map = new HashMap<String, List<Department>>();
        List<Department> deptList = departmentBiz.findDepartMentList(department);

        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        //添加一级部门
        if (shiroUser.getUserAccount().equals("admin")) {
            Department root = new Department();
            root.setDeptId(0L);
            root.setName("一级部门");
            root.setParentId(-1L);
            root.setOpen(true);
            deptList.add(root);
        }
        map.put("deptList", deptList);
        return map;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{deptId}")
    @ResponseBody
    public R info(@PathVariable("deptId") Long deptId) {
        Department department = new Department();
        department.setDeptId(deptId);

        try {
            department = departmentBiz.findDepartMentList(department).get(0);
        } catch (Exception e) {
            log.info("/info/{deptId}: 查询为空");
        }


        return R.ok().put("dept", department);
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    @ResponseBody
    public R save(@RequestBody Department dept) {
        departmentBiz.add(dept);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public R update(@RequestBody Department dept) {
        departmentBiz.update(dept);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/del")
    @ResponseBody
    public R del(Department dept) {
        departmentBiz.del(dept);
        return R.ok();
    }

}
