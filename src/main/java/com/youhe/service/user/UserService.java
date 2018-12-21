package com.youhe.service.user;

import com.youhe.entity.role.User_Role;
import com.youhe.entity.user.User;

import java.util.List;

public interface UserService {

    //根据用户账号查询用户信息
    User findByUserName(String userAccount);

    //查找用户表（包含角色和部门的权限）
    List<User> findUserList(User user);

    //更新用户
    void update(User user);

    //查找用户表
    List<User> findOnlyUserList(User user);

    //注册用户
    void save(User user);

    //删除用户
    void del(User user);


}
