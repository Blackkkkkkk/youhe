package com.youhe.service.sys.desktop;

import com.youhe.entity.desktop.Desktop;
import com.youhe.entity.desktop.DesktopSet;
import com.youhe.entity.permission.Permission_Role;
import com.youhe.entity.role.Role;
import com.youhe.mapper.desktop.DesktopMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youhe.mapper.desktop.DesktopSetMapper;
import com.youhe.serviceImpl.Controller.roleController.RoleControllerImpl;
import com.youhe.utils.R;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sara
 * @since 2019-06-03
 */
@Service
public class DesktopServiceImpl extends ServiceImpl<DesktopMapper, Desktop> implements DesktopService {

    @Autowired
    private DesktopMapper desktopMapper;
    @Autowired
    private DesktopSetMapper desktopSetMapper;

    @Autowired
    private DesktopServiceImpl desktopServiceImpl;
    //修改角色
    public void update(DesktopSet dept) {
//        desktopMapper.update(dept);
        ((DesktopServiceImpl) AopContext.currentProxy()).savePer(dept);
    }


    public void savePer(DesktopSet dept) {
        DesktopSet desktop = new DesktopSet();
       Integer uid=dept.getUserId();
        desktopSetMapper.delPer(dept);
//        ((DesktopServiceImpl) AopContext.currentProxy()).delPer(desktop, dept);
        List<Long> desktopList = dept.getDesktopList();
        desktop.setUserId(uid);
        for (Long de : desktopList) {
            desktop.setSysDivId(Integer.parseInt(de+""));
//            permission_role.setSys_permission_id(Integer.parseInt(pe + ""));
            desktopSetMapper.save(desktop);
        }
    }






//    public void delPer(Desktop desktop, Desktop dept) {
//
//        permission_role.setSys_role_id(Integer.parseInt(role.getRid()));
//        permissonBiz.del_permission_role(permission_role);
//    }

}
