package com.youhe.entity.shop;

import com.youhe.entity.SysBaseEntity;

import java.io.Serializable;
import java.util.List;

public class Commodity implements Serializable {

    private static final long serialVersionUID = 3271541341702660411L;
    private String cid;
    private String cname; //商品角色名
    private int available;//是否可用
    //上级角色ID,一级部门为0
    private Long parentId;
    //排序
    private Integer orderNum;

    private String son;// 是否存在子菜单


    /**
     * ztree属性
     */
    private Boolean open;


    //上级角色名称
    private String parentName;

    // 角色对应的权限列表
    private List<Long> permissiondList;


    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<Long> getPermissiondList() {
        return permissiondList;
    }

    public void setPermissiondList(List<Long> permissiondList) {
        this.permissiondList = permissiondList;
    }

    public String getSon() {
        return son;
    }

    public void setSon(String son) {
        this.son = son;
    }
}
