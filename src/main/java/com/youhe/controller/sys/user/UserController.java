package com.youhe.controller.sys.user;

import com.youhe.biz.department.DepartmentBiz;
import com.youhe.biz.role.RoleBiz;
import com.youhe.biz.user.UserBiz;
import com.youhe.common.Constant;
import com.youhe.controller.loginController.LoginController;
import com.youhe.entity.collection.Collections;
import com.youhe.entity.department.User_Department;
import com.youhe.entity.role.Role;
import com.youhe.entity.role.User_Role;
import com.youhe.entity.user.User;

import com.youhe.mapper.collection.CollectionsMapper;
import com.youhe.mapper.user.UserMapper;
import com.youhe.serviceImpl.Controller.userController.UserControllerImpl;

import com.youhe.utils.R;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoqiang on 2018/12/19.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Resource
    private ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    private DepartmentBiz departmentBiz;

    @Autowired
    private UserBiz userBiz;

    @Autowired
    private RoleBiz roleBizl;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CollectionsMapper collectionsMapper;


    @Autowired
    private UserControllerImpl userController;

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/index")
    public String index() {

        return "sys/user/userManage";
    }

    @RequestMapping(value = "/updateUser")
    public String updateUser() {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Map<String, Object> vars = new HashMap<>();
        User userNmae=userMapper.findUser(userAccount);
        vars.put("user",userNmae);
//        thymeleafViewResolver.setStaticVariables(vars);
        return "sys/user/updateUser";
    }

    //收藏标签
    @RequestMapping(value = "/findCollections")
    @ResponseBody
    public ModelAndView findCollections() {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Long uid=shiroUser.getUid();
        String uids=String.valueOf(uid);
        ModelAndView models = new ModelAndView();
//        Map<String, Object> vars = new HashMap<>();
        List<Collections> collectionList = collectionsMapper.findCollections(uids);
//        vars.put("collectionList",collectionList);
        models.addObject("collectionList", collectionList);
//        return "sys/user/collections";
        models.setViewName("sys/user/collections");
        return models;
    }

//天气预报
    @RequestMapping(value = "/forecast")
    public String forecast() {

        return "sys/user/forecast";
    }


    //桌面设置
    @RequestMapping(value = "/desktop")
    public String desktop() {
        return "sys/user/desktop";
    }

    //快捷意见
    @RequestMapping(value = "/opinion")
    public String opinion() {

        System.out.println("123");
        return "sys/user/opinionSet";
    }

//修改个人资料
    @RequiresPermissions(value = "sys:info")
    @RequestMapping(value = "/updates")
    @ResponseBody
    public R updates(String userName,String email,String phone,Integer uid) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Map<String, Object> vars = new HashMap<>();
        userBiz.updates(userName,email,phone,uid);
        return R.ok();
    }

    //跳转到修改密码
    @RequestMapping(value = "/changePassword")
    public String changePassword( String oldpassword,String password,String passwordAgin,Integer uid) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Map<String, Object> vars = new HashMap<>();
        User userNmae=userMapper.findUser(userAccount);
        vars.put("user",userNmae);
        return "sys/user/changePassword";
    }


    //修改密码

    @RequiresPermissions(value = "sys:changePassword")
    @RequestMapping(value = "/updatePassword")
    @ResponseBody
    public R updatePassword(String oldpassword,String password,Integer uid) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        User user=new User();
        //数据库实际的密码
        User userTemp=userMapper.findUser(userAccount);
        String oldpass=userTemp.getUserPassword();
        System.out.println("数据库实际的密码"+oldpass);
        user.setUserAccount(userAccount);
        user.setUserPassword(password);
        user.setUid(Long.valueOf(uid));
       //输入的新密码跟数据库的实际密码进行判断   如果一致修改成功  如果不一致修改失败
        String credentialsSalt = userTemp.getUserAccount() + userTemp.getSalt();
        String oldPassword = new SimpleHash(Constant.HASH_ALGORITHM, oldpassword,
                ByteSource.Util.bytes(credentialsSalt), Constant.HASH_INTERATIONS).toHex();
        if(oldPassword.equals(userTemp.getUserPassword())){
            System.out.println("成功");
            ShiroUserUtils.encryptPassword(user);
            userBiz.update(user);
            return R.ok();

        }
        return R.error("原密码输入错误");

//        if (ShiroUserUtils.checkPasswordByMeixiang(userTemp,oldpassword)){
//            userBiz.update(user);
//            System.out.println("修改成功");
//        }else {
//            return R.error("与原密码不一致");
//        }
//        Map<String, Object> vars = new HashMap<>();
//        userBiz.updates(userName,email,phone,uid);
//        return R.ok();
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
        if (shiroUser.getUserAccount() != "admin") {
            /*
            List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());
            Long parentId = null;
            for (SysDeptEntity sysDeptEntity : deptList) {
                if (parentId == null) {
                    parentId = sysDeptEntity.getParentId();
                    continue;
                }

                if (parentId > sysDeptEntity.getParentId().longValue()) {
                    parentId = sysDeptEntity.getParentId();
                }
            }
            deptId = parentId;*/
        }
        map.put("deptId", deptId);
        return map;
    }


    @RequestMapping("/list")
    @ResponseBody
    public List<User> list(User user) {
        List<User> userList = userBiz.findOnlyUserList(user);

        return userList;
    }

    /**
     * 验证登录账号是否存在
     */


    @RequestMapping("/checkUserAccount")
    @ResponseBody
    public Map<String, Boolean> checkUserAccount(User user) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        Boolean valid = true;
        List<User> userList = userBiz.findOnlyUserList(user);


        if (!CollectionUtils.isEmpty(userList)) {
            valid = false;
        }
        map.put("valid", valid);

        return map;
    }


    /**
     * 选择角色(添加、修改菜单)
     */
    @RequestMapping("/select")
    @ResponseBody
    public Map<String, List<Role>> select(Role role) {


        Map<String, List<Role>> map = new HashMap<String, List<Role>>();
        List<Role> roleList = roleBizl.findRoleList(role);
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        //添加一级部门
        if (shiroUser.getUserAccount().equals("admin")) {
            Role root = new Role();
            root.setRid("0");
            root.setRname("全部角色");
            root.setParentId(-1L);
            root.setOpen(true);
            roleList.add(root);
        }

        map.put("roleList", roleList);
        return map;
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{uid}")
    @ResponseBody
    public R info(@PathVariable("uid") Long uid) {

        User user = new User();
        user.setUid(uid);
        try {

            user = userBiz.findOnlyUserList(user).get(0);
        } catch (Exception e) {
            log.info("/info/{deptId}: 查询为空:" + e.toString());
        }
        return R.ok().put("user", user);
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    @ResponseBody
    public R save(@RequestBody User user) {

        HashMap<String, String> hashMap = new HashMap<String, String>();


        ShiroUserUtils.encryptPassword(user);

        try {
            userController.controllerSave(user);
            hashMap.put("code", "0");
            return R.ok();
        } catch (Exception e) {
            System.out.println(e.toString());
            //  status = false;
            hashMap.put("code", "500");
            hashMap.put("msg", "注册失败");
            return R.error("注册失败");
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public R update(@RequestBody User user) {
        ShiroUserUtils.encryptPassword(user);
        userBiz.update(user);

        User_Role user_role = new User_Role();
        user_role.setSys_user_id(user.getUid() + "");
        user_role.setSys_role_id(user.getRoleId() + "");
        roleBizl.update_user_Role(user_role);

        User_Department user_department = new User_Department();
        user_department.setSys_user_id(user.getUid() + "");
        user_department.setSys_department_id(user.getDepartmentId());
        departmentBiz.update_user_department(user_department);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/del")
    @ResponseBody
    public R del(User user) {

        try {
            userBiz.del(user);

            User_Department user_department = new User_Department();
            user_department.setSys_user_id(user.getUid() + "");
            departmentBiz.del_user_departmentt(user_department);


            User_Role user_role = new User_Role();
            user_role.setSys_user_id(user.getUid() + "");
            roleBizl.del_user_Role(user_role);


        } catch (Exception e) {

            log.info("/user/del: 用户删除失败:" + e.toString());
            return R.error("删除用户失败");
        }

        return R.ok();
    }

}
