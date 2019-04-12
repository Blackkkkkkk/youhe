package com.youhe.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youhe.entity.department.Department;
import com.youhe.entity.department.User_Department;
import com.youhe.entity.user.User;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserMapper extends BaseMapper<User> {

    //根据用户账号查询用户信息
    User findByUserName(String userName);

    //查找用户表
    List<User> findUserList(User user);

    //更新用户
    void update(User user);

    //查找用户表
    List<User> findOnlyUserList(User user);

    //保存用户
    void save(User user);

    //删除用户
    void del(User user);

  //根据用户id查询用户名称
    User findName(String userId);

    //查询出子菜单
    List<Department> findNames();
    //查询出父级菜单的用户
    List<User_Department> findNameOne(String dept_id);
    //中间表关联信息
    List<User_Department> findNameTwo (String dept_id);
    //查询出用户
    List<User> findNameThree(String sys_user_id);
}
