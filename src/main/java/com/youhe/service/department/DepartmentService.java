package com.youhe.service.department;


import com.youhe.entity.activitiData.ACT_RE_MODEL_PROCDEF;
import com.youhe.entity.department.Department;
import com.youhe.entity.department.User_Department;
import com.youhe.entity.role.User_Role;

import java.util.List;

public interface DepartmentService {

    //查找部门列表
    List<Department> findDepartMentList(Department department);

    //删除部门
    void del(Department department);

    //修改部门
    void update(Department department);

    //新增部门
    void add(Department department);


    // 更新 用户-部门表
    void update_user_department(User_Department user_department);

    // 保存 用户-部门表
    void save_user_department(User_Department user_department);

    //删除用户-部门表
    void del_user_departmentt(User_Department user_department);
}
