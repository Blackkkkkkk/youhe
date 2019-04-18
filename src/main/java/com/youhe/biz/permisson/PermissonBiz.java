package com.youhe.biz.permisson;


import com.youhe.entity.department.Department;
import com.youhe.entity.department.User_Department;
import com.youhe.entity.permission.Permission;
import com.youhe.entity.permission.Permission_Role;
import com.youhe.mapper.department.DepartmentMapper;
import com.youhe.mapper.permisson.PermissonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Service
public class PermissonBiz {
    private Logger log = LoggerFactory.getLogger(PermissonBiz.class);

    @Autowired
    private PermissonMapper permissonMapper;

    //查找部门列表
    public List<Permission> findPermissionList(Permission permission) {
        log.debug("findPermissionList:");
        return permissonMapper.findPermissionList(permission);
    }

    //查询角色权限表
    public List<Permission_Role> find_permission_roleList(Permission_Role permission_role) {
        return permissonMapper.find_permission_roleList(permission_role);
    }

    //新增权限
    public void save_permission_role(Permission_Role permission_role) {
        permissonMapper.save_permission_role(permission_role);
    }

    //删除权限
    public void del_permission_role(Permission_Role permission_role) {
        permissonMapper.del_permission_role(permission_role);
    }


    //新增菜单
    public void add(Permission permission) {
        log.debug("add:");
        permissonMapper.add(permission);
    }

    //修改部门
    public void update(Permission permission) {
        log.debug("update:");
        permissonMapper.update(permission);
    }

    //查找菜单列表
    public List<Permission> findDepartMentList(Permission permission) {
        log.debug("findDepartMentList:");
        return permissonMapper.findDepartMentList(permission);
    }

    //删除部门
    public void del(Permission permission) {
        log.debug("del:");
        permissonMapper.del(permission);
    }

    public List<Permission> selectMentLists(){
        return permissonMapper.selectMentList();
    }

}
