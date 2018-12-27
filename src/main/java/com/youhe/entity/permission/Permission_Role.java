package com.youhe.entity.permission;

import com.youhe.entity.SysBaseEntity;

public class Permission_Role extends SysBaseEntity {

    private int id;
    private int sys_permission_id;//用户ID
    private int sys_role_id;//  角色ID

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSys_permission_id() {
        return sys_permission_id;
    }

    public void setSys_permission_id(int sys_permission_id) {
        this.sys_permission_id = sys_permission_id;
    }

    public int getSys_role_id() {
        return sys_role_id;
    }

    public void setSys_role_id(int sys_role_id) {
        this.sys_role_id = sys_role_id;
    }
}