package com.youhe.utils.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class InitUsernamePasswordToken extends UsernamePasswordToken {
    private int actionType; //登录模式 1PC  2微信   3 短信登录
    private String salt;//盐值


public void InitUsernamePasswordToken(){}

    public InitUsernamePasswordToken(String userAccount, String userPassword,
                                     int actionType, String salt) {
        super(userAccount, userPassword);
        this.actionType = actionType;
        this.salt = salt;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }


    public void clear() {
    this.actionType=0;
    this.salt = null;
    }


    public String getPass(){

        return  String.valueOf(this.getPassword());
    }
}
