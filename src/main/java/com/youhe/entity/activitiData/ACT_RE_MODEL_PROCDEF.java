package com.youhe.entity.activitiData;

import java.io.Serializable;

/**
 * Created by xiaoqiang on 2018/12/11.
 */
public class ACT_RE_MODEL_PROCDEF implements Serializable {


    private static final long serialVersionUID = 8373469172327401795L;

    private String resId; //   ACT_RE_MODEL  表的ID
    private String resName; // ACT_RE_MODEL  表的NAME

    //ACT_RE_PROCDEF
    private String id_; //   id
    private int rev_; // 版本号
    private String category_; //  流程命名空间   流程定义的Namespace就是类别
    private String name_; //流程名称
    private String key_;// 流程编号
    private int version_;// 流程版本
    private String deployment_id_;  //部署ID

    private String resource_name_; //资源文件名称   流程bpmn文件名称
    private String dgrm_resource_name_;  //图片资源文件名称  png流程图片名称

    private String description_;//描述信息

    private int has_start_from_key_; //是否从key启动  start节点是否存在formKey 0否  1是

    private int suspension_state_;// 是否挂起  1激活 2挂起


    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getId_() {
        return id_;
    }

    public void setId_(String id_) {
        this.id_ = id_;
    }

    public int getRev_() {
        return rev_;
    }

    public void setRev_(int rev_) {
        this.rev_ = rev_;
    }

    public String getCategory_() {
        return category_;
    }

    public void setCategory_(String category_) {
        this.category_ = category_;
    }

    public String getName_() {
        return name_;
    }

    public void setName_(String name_) {
        this.name_ = name_;
    }

    public String getKey_() {
        return key_;
    }

    public void setKey_(String key_) {
        this.key_ = key_;
    }

    public int getVersion_() {
        return version_;
    }

    public void setVersion_(int version_) {
        this.version_ = version_;
    }

    public String getDeployment_id_() {
        return deployment_id_;
    }

    public void setDeployment_id_(String deployment_id_) {
        this.deployment_id_ = deployment_id_;
    }

    public String getResource_name_() {
        return resource_name_;
    }

    public void setResource_name_(String resource_name_) {
        this.resource_name_ = resource_name_;
    }

    public String getDgrm_resource_name_() {
        return dgrm_resource_name_;
    }

    public void setDgrm_resource_name_(String dgrm_resource_name_) {
        this.dgrm_resource_name_ = dgrm_resource_name_;
    }

    public String getDescription_() {
        return description_;
    }

    public void setDescription_(String description_) {
        this.description_ = description_;
    }

    public int getHas_start_from_key_() {
        return has_start_from_key_;
    }

    public void setHas_start_from_key_(int has_start_from_key_) {
        this.has_start_from_key_ = has_start_from_key_;
    }

    public int getSuspension_state_() {
        return suspension_state_;
    }

    public void setSuspension_state_(int suspension_state_) {
        this.suspension_state_ = suspension_state_;
    }
}
