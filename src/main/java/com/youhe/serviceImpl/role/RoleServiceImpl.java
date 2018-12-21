package com.youhe.serviceImpl.role;

import com.youhe.biz.role.RoleBiz;
import com.youhe.biz.user.UserBiz;
import com.youhe.entity.role.Role;
import com.youhe.entity.role.User_Role;
import com.youhe.entity.user.User;
import com.youhe.service.role.RoleService;
import com.youhe.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleBiz roleBiz;


    //查找用户表（包含角色和部门的权限）
    @Override
    public List<Role> findRoleList(Role role) {

        return roleBiz.findRoleList(role);
    }

    // 更新 用户-角色表
    @Override
    public void update_user_Role(User_Role user_role) {
        roleBiz.update_user_Role(user_role);
    }

    // 保存 用户-角色表
    @Override
    public void save_user_Role(User_Role user_role) {
        roleBiz.save_user_Role(user_role);
    }


    //删除用户-角色表
    public void del_user_Role(User_Role user_role) {
        roleBiz.del_user_Role(user_role);
    }
}
