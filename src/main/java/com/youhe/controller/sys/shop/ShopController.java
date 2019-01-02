package com.youhe.controller.sys.shop;

import com.github.pagehelper.PageInfo;

import com.youhe.biz.shop.ShopBiz;

import com.youhe.controller.loginController.LoginController;

import com.youhe.entity.shop.Picture;
import com.youhe.entity.shop.Shop;


import com.youhe.serviceImpl.Controller.shopController.ShopControllerImpl;
import com.youhe.utils.R;
import net.sf.json.JSONArray;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xiaoqiang on 2018/12/19.
 */
@Controller
@RequestMapping(value = "/shop")
public class ShopController {

    @Autowired
    private ShopBiz shopBiz;


    @Autowired
    private ShopControllerImpl shopController;

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/index")
    public String index(Model model) {


        File file = new File("./upload/stencilset.json");
        if (!file.exists()) {
            System.out.println("1");
        } else {
            System.out.println("2");
        }

        return "sys/shop/shopDataManage";

    }

    /**
     * @param shop 获取商品
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<Shop> list(Shop shop) {

        List<Shop> shopList = shopBiz.findRoleList(shop);

        return shopList;

    }


    /**
     *      * 图片上传
     *      *
     */
    @RequestMapping(value = "/uploadReport", method = RequestMethod.POST)
    @ResponseBody
    public R uploadReport(HttpServletRequest request, HttpServletResponse response, Shop shop) {
        Map<String, Object> result = new HashMap<String, Object>();

        result = shopController.uploadReport(request, response, shop);

        return R.ok().put("result", result);
    }

    //删除图片
    @RequestMapping(value = "/delReport", method = RequestMethod.POST)
    @ResponseBody
    public R delReport(Picture picture) {
        Map<String, Object> result = new HashMap<String, Object>();

        // result = shopController.uploadReport(request, response, shop);

        return R.ok().put("result", result);
    }


    @RequestMapping("/save")
    @ResponseBody
    public R save(Shop shop) {


        Long id = shopBiz.save(shop);


        //   List<Shop> shopList = shopBiz.findRoleList(shop);
        System.out.println("1");
        return R.ok().put("id", id);

    }


}
