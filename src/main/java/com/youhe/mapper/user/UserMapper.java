package com.youhe.mapper.user;

import com.youhe.entity.user.User;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserMapper {

    //根据用户账号查询用户信息
    User findByUserName(String userName);

    //查找用户表
    List<User> findUserList(User user);

    //更新用户
    void update(User user);

}
