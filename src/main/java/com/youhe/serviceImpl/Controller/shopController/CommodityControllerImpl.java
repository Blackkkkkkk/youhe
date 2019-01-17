package com.youhe.serviceImpl.Controller.shopController;

import com.youhe.biz.permisson.PermissonBiz;
import com.youhe.biz.shop.CommodityBiz;
import com.youhe.entity.permission.Permission_Role;
import com.youhe.entity.role.Role;
import com.youhe.entity.shop.Commodity;
import com.youhe.serviceImpl.Controller.roleController.RoleControllerImpl;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(rollbackFor = Exception.class)
public class CommodityControllerImpl {



    @Autowired
    private CommodityBiz commodityBiz;

    @Autowired
    private PermissonBiz permissonBiz;


    //保存角色
    public void save(Commodity commodity) {
        commodityBiz.add(commodity);
        ((CommodityControllerImpl) AopContext.currentProxy()).savePer(commodity);

    }


    //保存角色权限  先删除后保存
    public void savePer(Commodity commodity) {

        Permission_Role permission_role = new Permission_Role();

        ((CommodityControllerImpl) AopContext.currentProxy()).delPer(permission_role, commodity);

        List<Long> permissiondList = commodity.getPermissiondList();

        for (Long pe : permissiondList) {
            permission_role.setSys_permission_id(Integer.parseInt(pe + ""));
            permissonBiz.save_permission_role(permission_role);
        }
    }

    //删除角色权限
    public void delPer(Permission_Role permission_role, Commodity commodity) {
        //先删除该角色的所有权限
        permission_role.setSys_role_id(Integer.parseInt(commodity.getCid()));
        permissonBiz.del_permission_role(permission_role);
    }


    //删除角色
    public void del(Commodity commodity) {

        commodityBiz.del(commodity);

        Permission_Role permission_role = new Permission_Role();

        ((CommodityControllerImpl) AopContext.currentProxy()).delPer(permission_role, commodity);

    }

    //修改角色
    public void update(Commodity commodity) {

        commodityBiz.update(commodity);
        ((CommodityControllerImpl) AopContext.currentProxy()).savePer(commodity);
    }



}
