package com.youhe.biz.user;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youhe.entity.user.User;
import com.youhe.mapper.user.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;


@Service
public class UserBiz extends ServiceImpl<UserMapper, User> {
    private Logger log = LoggerFactory.getLogger(UserBiz.class);

    @Autowired
    private UserMapper userMapper;


    //根据用户账号查询用户信息
    public User findByUserName(String userName) {

        return userMapper.findByUserName(userName);
    }


    //查找用户表（包含角色和部门的权限）
    public List<User> findUserList(User user) {
        return userMapper.findUserList(user);
    }


    public void update(User user) {
        userMapper.update(user);
    }


    //查找用户表
    public List<User> findOnlyUserList(User user) {
        return userMapper.findOnlyUserList(user);
    }

    //保存用户
    /*public boolean save(User user) {
//        userMapper.save(user);
        return super.saveOrUpdate(user);
    }*/


    //删除用户
    public void del(User user) {
        super.removeById(user.getUid());
//        userMapper.del(user);
    }

    //修改
    public void updates(String userName,String email,String phone,Integer uid) {
        userMapper.updates(userName,email,phone,uid);
    }




}
