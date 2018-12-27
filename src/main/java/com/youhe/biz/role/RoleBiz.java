package com.youhe.biz.role;


import com.youhe.entity.role.Role;
import com.youhe.entity.role.User_Role;
import com.youhe.entity.user.User;
import com.youhe.mapper.role.RoleMapper;
import com.youhe.mapper.user.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class RoleBiz {
    private Logger log = LoggerFactory.getLogger(RoleBiz.class);

    @Autowired
    private RoleMapper roleMapper;


    //查找用户表
    public List<Role> findRoleList(Role role) {
        return roleMapper.findRoleList(role);
    }

    //新增角色表
    public void add(Role role) {
        roleMapper.add(role);
    }

    //修改角色表
    public void update(Role role) {
        roleMapper.update(role);
    }

    //删除角色
    public void del(Role role) {
        roleMapper.del(role);
    }

    // 更新 用户-角色表
    public void update_user_Role(User_Role user_role) {
        roleMapper.update_user_Role(user_role);
    }

    // 保存 用户-角色表
    public void save_user_Role(User_Role user_role) {
        roleMapper.save_user_Role(user_role);
    }

    //删除用户-角色表
    public void del_user_Role(User_Role user_role) {
        roleMapper.del_user_Role(user_role);
    }

}
