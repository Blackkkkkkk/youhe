package com.youhe.controller.sys.desktop;


import com.youhe.entity.desktop.Desktop;
import com.youhe.entity.desktop.DesktopSet;
import com.youhe.entity.permission.Permission_Role;
import com.youhe.entity.role.Role;
import com.youhe.mapper.desktop.DesktopMapper;
import com.youhe.mapper.desktop.DesktopSetMapper;
import com.youhe.service.activiti.DelegateServiceImpl;
import com.youhe.service.sys.desktop.DesktopServiceImpl;
import com.youhe.utils.R;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Sara
 * @since 2019-06-03
 */
@RestController
@RequestMapping("/desktop")
public class DesktopController {

    @Autowired
    private DesktopMapper desktopMapper;
    @Autowired
    private DesktopSetMapper desktopSetMapper;



    /**
     * @param desktop 获取div
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<Desktop> list(Desktop desktop) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Long uid=shiroUser.getUid();
        desktop.setUid(uid);
//
//        if (flag==1){
//            desktop.setUserId(uid.intValue());
//            List<Desktop> desktopList = desktopSetMapper.findDesktop(desktop);
//            return desktopList;
//        }else {
            List<Desktop> desktopList = desktopMapper.findDesktopList(desktop);
            return desktopList;
//        }
    }


    /**
     * @param
     * @return
     */
//    @RequestMapping("/lists")
//    @ResponseBody
//    public List<DesktopSet> list(DesktopSet desktopSet) {
//        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
//        String userAccount=shiroUser.getUserAccount();
//        Long uid=shiroUser.getUid();
//        desktopSet.setUserId(uid.intValue());
//        List<DesktopSet> desktopList = desktopSetMapper.findDesktop(desktopSet);
//        return desktopList;
//    }


    /**
     * @param desktopSet 获取角色权限
     * @return
     */
    @RequestMapping("/desktop_list")
//    @ResponseBody
    public List<Long> desktop_list(DesktopSet desktopSet) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Long uid=shiroUser.getUid();
        desktopSet.setUserId(uid.intValue());


        List<DesktopSet> desktopSet_ist = desktopSetMapper.find_desktop_list(desktopSet);

        List<Long> list = new ArrayList<Long>();

        for (DesktopSet plist : desktopSet_ist) {

            list.add(Long.parseLong(plist.getSysDivId()+ ""));
        }
        return list;
    }
}

