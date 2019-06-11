package com.youhe.controller.sys.desktop;


import com.youhe.entity.desktop.Desktop;
import com.youhe.entity.desktop.DesktopSet;
import com.youhe.entity.permission.Permission_Role;
import com.youhe.mapper.desktop.DesktopSetMapper;
import com.youhe.service.sys.desktop.DesktopServiceImpl;
import com.youhe.utils.R;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Sara
 * @since 2019-06-10
 */
@RestController
@RequestMapping("/desktopSet")
public class DesktopSetController {


    @Autowired
    private DesktopSetMapper desktopSetMapper;

    @Autowired
    private DesktopServiceImpl desktopServiceImpl;


    /**
     * @param
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<Long> list(DesktopSet desktopSet) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Long uid=shiroUser.getUid();
        desktopSet.setUserId(uid.intValue());
        List<DesktopSet> desktopList = desktopSetMapper.findDesktop(desktopSet);
        List<Long> lists = new ArrayList<Long>();

        for (DesktopSet  de: desktopList) {
//            System.out.println(de.getSys_permission_id());
            lists.add(Long.parseLong(de.getSysDivId() + ""));
        }
        return lists;
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/update")
    public R update(@RequestBody DesktopSet dept) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Long uid=shiroUser.getUid();
        dept.setUserId(uid.intValue());
//        try {
            desktopServiceImpl.update(dept);
            return R.ok();
//        }catch (Exception e){
//            log.info("/role/update: 角色更新失败:" + e.toString());
//            return R.error("更新失败！");
//        }
    }

}

