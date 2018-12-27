package com.youhe.entity.role;

import com.youhe.entity.SysBaseEntity;
import com.youhe.entity.permission.Permission;
import com.youhe.entity.user.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Role extends SysBaseEntity {

    private String rid;
    private String rname;//角色名
    private int available;//是否可用
    //上级角色ID，一级部门为0
    private Long parentId;
    //排序
    private Integer orderNum;

    /**
     * ztree属性
     */
    private Boolean open;


    //上级角色名称
    private String parentName;

    // 角色对应的权限列表
    private List<Long> permissiondList;


    public List<Long> getPermissiondList() {
        return permissiondList;
    }

    public void setPermissiondList(List<Long> permissiondList) {
        this.permissiondList = permissiondList;
    }

    private Set<User> users = new HashSet<>();

    private Set<Permission> permissions = new HashSet<>();


    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
