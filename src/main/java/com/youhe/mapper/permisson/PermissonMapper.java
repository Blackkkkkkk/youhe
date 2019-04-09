package com.youhe.mapper.permisson;

import com.youhe.entity.department.Department;
import com.youhe.entity.permission.Permission;
import com.youhe.entity.permission.Permission_Role;
import com.youhe.entity.role.Role;
import com.youhe.entity.role.User_Role;

import java.util.List;


public interface PermissonMapper {


    //查找角色表
    List<Permission> findPermissionList(Permission permission);

    //查询角色权限表
    List<Permission_Role> find_permission_roleList(Permission_Role permission_role);

    //新增权限
    void save_permission_role(Permission_Role permission_role);

    //删除权限
    void del_permission_role(Permission_Role permission_role);

    //新增菜单
    void add(Permission permission);

    //修改菜单
    void update(Permission permission);

    //查找菜单列表
    List<Permission> findDepartMentList(Permission permission);

    //删除菜单
    void del(Permission permission);


}
