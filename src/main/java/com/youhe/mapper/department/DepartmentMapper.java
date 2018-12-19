package com.youhe.mapper.department;


import com.youhe.entity.department.Department;

import java.util.List;


public interface DepartmentMapper {


    //查找部门列表
    List<Department> findDepartMentList(Department department);

    //删除部门
    void del(Department department);

    //修改部门
    void update(Department department);

    //新增部门
    void add(Department department);

}
