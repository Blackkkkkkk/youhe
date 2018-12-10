package com.youhe.entity.user;

import com.youhe.entity.SysBaseEntity;
import com.youhe.entity.role.Role;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class User  implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long uid;
    private String userAccount;//账号
    private String userName;//姓名
    private String phone;  //手机号码
    private String email;//邮箱
    private Date registerDate; //注册时间
    private Long locked;//状态
    private String userPassword;// 密码
    private String salt;// 密码干扰


    private Set<Role> roles = new HashSet<>();


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
