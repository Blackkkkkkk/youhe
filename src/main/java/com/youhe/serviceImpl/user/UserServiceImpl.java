package com.youhe.serviceImpl.user;

import com.youhe.biz.user.UserBiz;
import com.youhe.entity.user.User;
import com.youhe.mapper.user.UserMapper;
import com.youhe.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserBiz userBiz;


    @Override
    public User findByUserName(String userAccount) {
        return userBiz.findByUserName(userAccount);

    }

    //查找用户表
    @Override
    public List<User> findUserList(User user) {

        return userBiz.findUserList(user);
    }

    //更新用户
    public void update(User user) {
        userBiz.update(user);
    }

}
