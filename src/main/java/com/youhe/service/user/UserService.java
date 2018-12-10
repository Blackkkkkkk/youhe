package com.youhe.service.user;

import com.youhe.entity.user.User;

import java.util.List;

public interface UserService {

    //根据用户账号查询用户信息
    User findByUserName(String userAccount);

    //查找用户表
    List<User> findUserList(User user);

    //更新用户
    void update(User user);
}
