package com.youhe.serviceImpl.Controller.roleController;

import com.youhe.biz.department.DepartmentBiz;
import com.youhe.biz.permisson.PermissonBiz;
import com.youhe.biz.role.RoleBiz;
import com.youhe.biz.user.UserBiz;
import com.youhe.entity.permission.Permission_Role;
import com.youhe.entity.role.Role;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class RoleControllerImpl {


    @Autowired
    private RoleBiz roleBiz;

    @Autowired
    private PermissonBiz permissonBiz;


    //保存角色
    public void save(Role role) {

        roleBiz.add(role);
        ((RoleControllerImpl) AopContext.currentProxy()).savePer(role);


    }

    //修改角色
    public void update(Role role) {

        roleBiz.update(role);
        ((RoleControllerImpl) AopContext.currentProxy()).savePer(role);
    }

    //删除角色
    public void del(Role role) {

        roleBiz.del(role);

        Permission_Role permission_role = new Permission_Role();

        ((RoleControllerImpl) AopContext.currentProxy()).delPer(permission_role, role);

    }


    //保存角色权限  先删除后保存
    public void savePer(Role role) {

        Permission_Role permission_role = new Permission_Role();

        ((RoleControllerImpl) AopContext.currentProxy()).delPer(permission_role, role);

        List<Long> permissiondList = role.getPermissiondList();

        for (Long pe : permissiondList) {
            permission_role.setSys_permission_id(Integer.parseInt(pe + ""));
            permissonBiz.save_permission_role(permission_role);
        }
    }

    //删除角色权限
    public void delPer(Permission_Role permission_role, Role role) {
        //先删除该角色的所有权限
        permission_role.setSys_role_id(Integer.parseInt(role.getRid()));
        permissonBiz.del_permission_role(permission_role);
    }

}
