package com.youhe.serviceImpl.department;


import com.youhe.biz.activiti.ActivitiBiz;
import com.youhe.biz.department.DepartmentBiz;
import com.youhe.entity.activitiData.ACT_RE_MODEL_PROCDEF;
import com.youhe.entity.department.Department;
import com.youhe.service.activiti.ActivitiService;
import com.youhe.service.department.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentBiz departmentBiz;


    //查找部门列表
    @Override
    public List<Department> findDepartMentList(Department department) {
        return departmentBiz.findDepartMentList(department);
    }


    //删除部门
    @Override
    public void del(Department department) {
        
        departmentBiz.del(department);
    }

    //修改部门
    @Override
    public void update(Department department) {

        departmentBiz.update(department);
    }

    //新增部门
    @Override
    public void add(Department department) {

        departmentBiz.add(department);
    }

}
