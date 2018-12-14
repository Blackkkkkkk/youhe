package com.youhe.utils.shiro;

import com.youhe.entity.user.User;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Created by xiaoqiang on 2018/12/10.
 */
public class ShiroUser  implements Serializable {
    private static final long serialVersionUID = -1373760761780840081L;


    private Long uid;
    private String userAccount;//账号
    private String userName;//姓名
    private String phone;  //手机号码
    private String email;//邮箱
    private Date registerDate; //注册时间
    private Long locked;//状态
    private String userPassword;// 密码
    private String salt;// 密码干扰



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

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        return userAccount.toString();
    }

    /**
     * 重载hashCode,只计算loginName;
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(userAccount);
    }

    /**
     * 重载equals,只计算loginName;
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ShiroUser other = (ShiroUser) obj;
        if (userAccount == null) {
            if (other.userAccount != null) {
                return false;
            }
        } else if (!userAccount.equals(other.userAccount)) {
            return false;
        }
        return true;
    }

}
