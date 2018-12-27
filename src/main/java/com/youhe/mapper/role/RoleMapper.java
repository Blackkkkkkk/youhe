package com.youhe.mapper.role;

import com.youhe.entity.department.User_Department;
import com.youhe.entity.role.Role;
import com.youhe.entity.role.User_Role;
import com.youhe.entity.user.User;

import java.util.List;


public interface RoleMapper {


    //查找角色表
    List<Role> findRoleList(Role role);

    //新增角色表
    void add(Role role);

    //修改角色表
    void update(Role role);

    //删除角色
    void del(Role role);

    // 更新 用户-角色表
    void update_user_Role(User_Role user_role);

    // 保存 用户-角色表
    void save_user_Role(User_Role user_role);


    //删除用户-角色表
    void del_user_Role(User_Role user_role);


}
