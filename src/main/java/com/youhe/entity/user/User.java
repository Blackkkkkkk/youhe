package com.youhe.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youhe.entity.SysBaseEntity;
import com.youhe.entity.role.Role;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@TableName(value = "sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long uid;
    private String userAccount;//账号
    private String userName;//姓名
    private String phone;  //手机号码
    private String email;//邮箱
    private Date registerDate; //注册时间
    private Long locked;//状态 账号是否锁定，1：锁定，0未锁定
    private String userPassword;// 密码
    private String salt;// 密码干扰

    private String roleId;//角色id

    private String departmentId;//部门 id

    private int orderNum; // 排序


    //查询属性
    @TableField(exist = false)
    private String roleName;//角色名
    @TableField(exist = false)
    private String departmentName;// 部门名字

    @TableField(exist = false)
    //ztree属性
    private String userDepartmentName;//用户和部门公用字段

    @TableField(exist = false)
    private Set<Role> roles = new HashSet<>();

    public String getUserDepartmentName() {
        return userDepartmentName;
    }

    public void setUserDepartmentName(String userDepartmentName) {
        this.userDepartmentName = userDepartmentName;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }


    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Long getLocked() {
        return locked;
    }

    public void setLocked(Long locked) {
        this.locked = locked;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
