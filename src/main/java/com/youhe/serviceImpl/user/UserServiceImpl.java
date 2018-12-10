package com.youhe.serviceImpl.user;

import com.youhe.entity.user.User;
import com.youhe.mapper.user.UserMapper;
import com.youhe.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public User findByUserName(String userAccount) {
        return userMapper.findByUserName(userAccount);

    }

    //查找用户表
    @Override
    public List<User> findUserList(User user) {

        return userMapper.findUserList(user);
    }

    //更新用户
    public void update(User user) {
        userMapper.update(user);
    }

}
