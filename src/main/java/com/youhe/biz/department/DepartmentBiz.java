package com.youhe.biz.department;


import com.youhe.entity.department.Department;
import com.youhe.entity.department.User_Department;
import com.youhe.mapper.department.DepartmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DepartmentBiz {
    private Logger log = LoggerFactory.getLogger(DepartmentBiz.class);

    @Autowired
    private DepartmentMapper departmentMapper;

    //查找部门列表
    public List<Department> findDepartMentList(Department department) {
        log.debug("findDepartMentList:");
        return departmentMapper.findDepartMentList(department);
    }


    //删除部门
    public void del(Department department) {
        log.debug("del:");
        departmentMapper.del(department);
    }

    //修改部门
    public void update(Department department) {
        log.debug("update:");
        departmentMapper.update(department);
    }

    //新增部门
    public void add(Department department) {
        log.debug("add:");
        departmentMapper.add(department);
    }

    // 更新 用户-部门表
    public void update_user_department(User_Department user_department) {
        departmentMapper.update_user_department(user_department);
    }

    // 保存 用户-部门表
    public void save_user_department(User_Department user_department) {
        departmentMapper.save_user_department(user_department);
    }

    //删除用户-部门表
    public void del_user_departmentt(User_Department user_department) {
        departmentMapper.del_user_departmentt(user_department);
    }


}
