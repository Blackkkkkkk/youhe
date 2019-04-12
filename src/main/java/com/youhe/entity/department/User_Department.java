package com.youhe.entity.department;

import com.youhe.entity.SysBaseEntity;
import com.youhe.entity.user.User;

import java.util.List;

/**
 * Created by xiaoqiang on 2018/12/19.
 */
public class User_Department extends SysBaseEntity {

    //部门-用户ID
    private Long id;

    private String sys_user_id;

    private String sys_department_id;

    private String userDepartmentName;


    private List<User> user;



    public List<User> getUser() {
        return user;
    }


    public void setUser(List<User> user) {
        this.user = user;
    }

    public String getUserDepartmentName() {
        return userDepartmentName;
    }

    public void setUserDepartmentName(String userDepartmentName) {
        this.userDepartmentName = userDepartmentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSys_user_id() {
        return sys_user_id;
    }

    public void setSys_user_id(String sys_user_id) {
        this.sys_user_id = sys_user_id;
    }

    public String getSys_department_id() {
        return sys_department_id;
    }

    public void setSys_department_id(String sys_department_id) {
        this.sys_department_id = sys_department_id;
    }
}
