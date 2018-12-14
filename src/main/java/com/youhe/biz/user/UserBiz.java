package com.youhe.biz.user;


import com.youhe.entity.user.User;
import com.youhe.mapper.user.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserBiz {
    private Logger log = LoggerFactory.getLogger(UserBiz.class);

    @Autowired
    private UserMapper userMapper;


    //根据用户账号查询用户信息
    public User findByUserName(String userName) {

        return userMapper.findByUserName(userName);
    }


    //查找用户表
    public List<User> findUserList(User user) {
        return userMapper.findUserList(user);
    }


    public void update(User user) {
        userMapper.update(user);
    }



}
