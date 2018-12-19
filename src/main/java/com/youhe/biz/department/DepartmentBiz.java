package com.youhe.biz.department;


import com.youhe.entity.activitiData.ACT_RE_MODEL_PROCDEF;
import com.youhe.entity.department.Department;
import com.youhe.mapper.activiti.ActivitiMapper;
import com.youhe.mapper.department.DepartmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
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

}
