package com.youhe.entity.activitiData.act_re_model;

import com.youhe.entity.SysBaseEntity;

import java.util.Date;

public class ACT_RE_MODEL extends SysBaseEntity {

    private String ID_;  //主键
    private int REV_;//乐观锁
    private String NAME_;//名称
    private String KEY_;//标识符

    private String CATEGORY_;//分类

    private Date CREATE_TIME_; // 创建时间
    private Date LAST_UPDATE_TIME_; // 最后更新时间

    private int VERSION_;//版本

    private String META_INFO_;//元数据

    private String DEPLOYMENT_ID_;//部署id

    private String EDITOR_SOURCE_VALUE_ID_; //设计器原始信息

    private String EDITOR_SOURCE_EXTRA_VALUE_ID_;//设计器扩展信息

    private String TENANT_ID_; //多租户

    public String getID_() {
        return ID_;
    }

    public void setID_(String ID_) {
        this.ID_ = ID_;
    }

    public int getREV_() {
        return REV_;
    }

    public void setREV_(int REV_) {
        this.REV_ = REV_;
    }

    public String getNAME_() {
        return NAME_;
    }

    public void setNAME_(String NAME_) {
        this.NAME_ = NAME_;
    }

    public String getKEY_() {
        return KEY_;
    }

    public void setKEY_(String KEY_) {
        this.KEY_ = KEY_;
    }

    public String getCATEGORY_() {
        return CATEGORY_;
    }

    public void setCATEGORY_(String CATEGORY_) {
        this.CATEGORY_ = CATEGORY_;
    }

    public Date getCREATE_TIME_() {
        return CREATE_TIME_;
    }

    public void setCREATE_TIME_(Date CREATE_TIME_) {
        this.CREATE_TIME_ = CREATE_TIME_;
    }

    public Date getLAST_UPDATE_TIME_() {
        return LAST_UPDATE_TIME_;
    }

    public void setLAST_UPDATE_TIME_(Date LAST_UPDATE_TIME_) {
        this.LAST_UPDATE_TIME_ = LAST_UPDATE_TIME_;
    }

    public int getVERSION_() {
        return VERSION_;
    }

    public void setVERSION_(int VERSION_) {
        this.VERSION_ = VERSION_;
    }

    public String getMETA_INFO_() {
        return META_INFO_;
    }

    public void setMETA_INFO_(String META_INFO_) {
        this.META_INFO_ = META_INFO_;
    }

    public String getDEPLOYMENT_ID_() {
        return DEPLOYMENT_ID_;
    }

    public void setDEPLOYMENT_ID_(String DEPLOYMENT_ID_) {
        this.DEPLOYMENT_ID_ = DEPLOYMENT_ID_;
    }

    public String getEDITOR_SOURCE_VALUE_ID_() {
        return EDITOR_SOURCE_VALUE_ID_;
    }

    public void setEDITOR_SOURCE_VALUE_ID_(String EDITOR_SOURCE_VALUE_ID_) {
        this.EDITOR_SOURCE_VALUE_ID_ = EDITOR_SOURCE_VALUE_ID_;
    }

    public String getEDITOR_SOURCE_EXTRA_VALUE_ID_() {
        return EDITOR_SOURCE_EXTRA_VALUE_ID_;
    }

    public void setEDITOR_SOURCE_EXTRA_VALUE_ID_(String EDITOR_SOURCE_EXTRA_VALUE_ID_) {
        this.EDITOR_SOURCE_EXTRA_VALUE_ID_ = EDITOR_SOURCE_EXTRA_VALUE_ID_;
    }

    public String getTENANT_ID_() {
        return TENANT_ID_;
    }

    public void setTENANT_ID_(String TENANT_ID_) {
        this.TENANT_ID_ = TENANT_ID_;
    }
}
