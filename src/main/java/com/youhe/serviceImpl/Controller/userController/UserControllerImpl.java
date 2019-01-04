package com.youhe.serviceImpl.Controller.userController;

import com.youhe.biz.department.DepartmentBiz;
import com.youhe.biz.role.RoleBiz;
import com.youhe.biz.user.UserBiz;
import com.youhe.entity.department.User_Department;
import com.youhe.entity.role.User_Role;
import com.youhe.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserControllerImpl {

    @Autowired
    private UserBiz userBiz;

    @Autowired
    private RoleBiz roleBiz;

    @Autowired
    private DepartmentBiz departmentBiz;

    @Transactional(rollbackFor = Exception.class)
    public void controllerSave(User user) {

        userBiz.save(user);

        if (user.getRoleId() != null && !user.getRoleId().equals("0")) {
            User_Role user_role = new User_Role();

            user_role.setSys_user_id(user.getUid() + "");
            user_role.setSys_role_id(user.getRoleId() + "");
            roleBiz.save_user_Role(user_role);
        }
        if (user.getDepartmentId() != null && !user.getDepartmentId().equals("0")) {

            User_Department user_department = new User_Department();

            user_department.setSys_user_id(user.getUid() + "");
            user_department.setSys_department_id(user.getDepartmentId());
            departmentBiz.save_user_department(user_department);

        }
    }

}
