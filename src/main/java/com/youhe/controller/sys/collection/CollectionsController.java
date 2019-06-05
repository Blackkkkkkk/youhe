package com.youhe.controller.sys.collection;


import com.youhe.common.Constant;
import com.youhe.entity.collection.Collections;
import com.youhe.entity.user.User;
import com.youhe.mapper.collection.CollectionsMapper;
import com.youhe.service.sys.CollectionsService;
import com.youhe.utils.R;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Sara
 * @since 2019-05-29
 */
@RestController
@RequestMapping("/collections")
public class CollectionsController {

    @Autowired
    private CollectionsMapper collectionsMapper;
    @Autowired
    private CollectionsService collectionsService;

    @RequiresPermissions(value = "sys:collections")
    @RequestMapping(value = "/saveCollection")
    @ResponseBody
    public R saveCollection(String url) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        String userAccount=shiroUser.getUserAccount();
        Collections collections=new Collections();
        Integer count=collectionsMapper.finds(url);
        collections.setUrl(url);
        if (count>0){
            collectionsMapper.del(url);
            return R.error();
        }else {
            collectionsService.save(collections);
            return R.ok();
        }

    }

    //查找地址
    @RequestMapping(value = "/finds")
    public  R finds(String url) {
//        Map<String, Object> vars = new HashMap<>();
        Integer count=collectionsMapper.finds(url);
//        vars.put("user",userNmae);
        if (count>0){
            return R.ok();
        }
//        thymeleafViewResolver.setStaticVariables(vars);
       return R.error();
    }





}

