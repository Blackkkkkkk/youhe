package com.youhe.entity.role;

import com.youhe.entity.SysBaseEntity;
import com.youhe.entity.permission.Permission;
import com.youhe.entity.user.User;

import java.util.HashSet;
import java.util.Set;

public class User_Role extends SysBaseEntity {

    private String id;
    private String sys_user_id;//用户ID
    private String sys_role_id;//  角色ID


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSys_user_id() {
        return sys_user_id;
    }

    public void setSys_user_id(String sys_user_id) {
        this.sys_user_id = sys_user_id;
    }

    public String getSys_role_id() {
        return sys_role_id;
    }

    public void setSys_role_id(String sys_role_id) {
        this.sys_role_id = sys_role_id;
    }
}
