package com.youhe.controller.user.shop.index;


import com.youhe.biz.redis.RedisBiz;
import com.youhe.biz.shop.ShopBiz;
import com.youhe.biz.shop.ShopUserIndexBiz;
import com.youhe.controller.sys.shop.ShopController;
import com.youhe.entity.shop.Shop;
import com.youhe.entity.shop.Shop_index_carousel;
import com.youhe.initBean.redis.CartPrefix;
import com.youhe.serviceImpl.Controller.shopController.ShopControllerImpl;
import com.youhe.utils.R;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xiaoqiang on 2018/12/19.
 */
@Controller
@RequestMapping(value = "/touristShop")
public class IndexController {


    @Autowired
    private ShopUserIndexBiz shopUserIndexBiz;

    @Autowired
    private ShopBiz shopBiz;

    @Autowired
    private RedisBiz redisBiz;

    @RequestMapping(value = "/index")
    public String index(Model model, Shop_index_carousel shop_index_carousel) {

        // 获取轮播图信息
        List<Shop_index_carousel> carouselList = shopUserIndexBiz.findPictureList(shop_index_carousel);

        model.addAttribute("carouselList", carouselList);

        Shop shop = new Shop();
        List<Shop> shopList = shopBiz.findShopList(shop);
        model.addAttribute("shopList", shopList);

        return "user/shop/index/index";
    }


    @RequestMapping("/CarouselList")
    @ResponseBody
    public Map<String, List<Shop_index_carousel>> list(Shop_index_carousel shop_index_carousel) {
        /*
          获取轮播图的文字和对应的信息
         */
        Map<String, List<Shop_index_carousel>> map = new HashMap<String, List<Shop_index_carousel>>();
        List<Shop_index_carousel> carouselList = shopUserIndexBiz.findPictureList(shop_index_carousel);
        map.put("carouselList", carouselList);

        /*
         获取对应的照片
         */

        return map;
    }

    /**
     * 保存
     */
    @RequestMapping("/CarouselSave")
    @ResponseBody
    public R save(Shop_index_carousel shop_index_carousel) {

        HashMap<String, String> hashMap = new HashMap<String, String>();

        try {
            shopUserIndexBiz.updateCarouse(shop_index_carousel);
            return R.ok().put("id", shop_index_carousel.getId());
        } catch (Exception e) {
            System.out.println(e.toString());

            return R.error().put("msg", "保存失败");
        }
    }


    /**
     *      * 图片上传
     *      *
     */
    @RequestMapping(value = "/uploadReport", method = RequestMethod.POST)
    @ResponseBody
    public R uploadReport(HttpServletRequest request, HttpServletResponse response, Shop shop) {
        Map<String, Object> result = new HashMap<String, Object>();

        // result = shopController.uploadReport(request, response, shop);

        return R.ok().put("result", result);
    }

    // 加入购物车
    /*
        return    1. 加入购物车成功   2: 购物车已存在，数量自动加1   3: 未登录  4 添加购物车异常
     */
    @RequestMapping(value = "/addCart")
    @ResponseBody
    public Map<String, Object> addCart(Shop shop) {

        Map<String, Object> map = new HashMap<String, Object>();
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();

        int Status = 4;


        if (shiroUser.getUserAccount() == null && shiroUser.getUserAccount() == "") {
            Status = 3;
        } else {
            // 判断缓存是否有该物品
            Boolean exists = redisBiz.hhasKey(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "");
            if (exists) {
                // 数量自增1
                Long num = redisBiz.hincrement(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "");
                Status = 2;
            } else {
                redisBiz.hput(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "");
                Status = 1;
            }
        }

        List<Shop> shopList = redisBiz.hscan(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "");

        map.put("shopList", shopList);
        map.put("Status", Status);

        return map;
    }
}