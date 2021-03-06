package com.youhe.controller.sys.shop;

import com.youhe.biz.redis.RedisBiz;
import com.youhe.biz.shop.PictureBiz;
import com.youhe.biz.shop.ShopBiz;

import com.youhe.controller.loginController.LoginController;

import com.youhe.entity.shop.Picture;
import com.youhe.entity.shop.Shop;


import com.youhe.serviceImpl.Controller.shopController.ShopControllerImpl;
import com.youhe.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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
    private PictureBiz pictureBiz;

    @Autowired
    private ShopControllerImpl shopController;

    @Autowired
    private RedisBiz redisBiz;


    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/index")
    public String index(Model model) {

        return "sys/shop/shopDataManage";

    }

    //设置
    @RequestMapping(value = "/shopPageMange")
    public String shopPageMange(Model model) {

        return "sys/shop/shopPageManage/shopPageManage";

    }


    /**
     * @param shop 获取商品
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<Shop> list(Shop shop) {

        List<Shop> shopList = shopBiz.findShopList(shop);


        return shopList;

    }


    /**
     * @param shop 获取商品和相册
     * @return
     */
    @RequestMapping("/ShopPiclist")
    @ResponseBody
    public Map<String, List> ShopPiclist(Shop shop) {

        Map<String, List> map = new HashMap<String, List>();


        map.put("shopList", shopBiz.findShopList(shop));

        Picture picture = new Picture();
        picture.setShopId(shop.getId());
        picture.setType(shop.getType());
        map.put("pictureList", pictureBiz.findPictureList(picture));


        return map;

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
        System.out.println(result + "************************");
        return R.ok().put("result", result);
    }

    //删除图片
    @RequestMapping(value = "/delReport", method = RequestMethod.POST)
    @ResponseBody
    public R delReport(Picture picture) {

        Map<String, Object> result = new HashMap<String, Object>();

        if (!picture.getVernier().equals("0")) {

            try {

                pictureBiz.del(picture);

                File file = new File(picture.getReportaddr());
                // 判断目录或文件是否存在
                if (file.exists()) {  // 不存在返回 false
                    file.delete();
                }

                // result = shopController.uploadReport(request, response, shop);

                return R.ok("删除成功！");
            } catch (Exception e) {
                return R.error("删除失败！");
            }
        } else {
            return R.error("必须保留一张图片用于！");
        }


    }

    @RequestMapping("/save")
    @ResponseBody
    public R save(Shop shop) {
        Long id = shopBiz.save(shop);

        if (id > 0) {
            return R.ok().put("id", shop.getId());
        } else {
            return R.error("保存失败！");
        }

    }

    @RequestMapping("/update")
    @ResponseBody
    public R update(Shop shop) {
        Long id = shopBiz.update(shop);
        if (id > 0) {
            return R.ok().put("id", shop.getId());
        } else {
            return R.error("更新失败！");
        }

    }

    @RequestMapping("/del")
    @ResponseBody
    public R del(Shop shop) {

        try {
            shopController.del(shop);
            return R.ok("删除成功！").put("state", true);
        } catch (Exception e) {
            return R.error("删除失败！").put("state", false);
        }
    }


    // 图片轮播
    @RequestMapping(value = "/pictureCarousel")
    public String pictureCarousel(Model model, Picture picture) {


        List<Picture> list = pictureBiz.findPictureList(picture);


        model.addAttribute("list", list);


        return "sys/shop/pictureCarousel";

    }

    //修改照片的排序
    @RequestMapping("/updateOrderNum")
    @ResponseBody
    public R updateOrderNum(Picture picture) {
        Picture uPicture = new Picture();
        try {
            uPicture.setId(picture.getNewIndexId());
            uPicture.setOrderNum(picture.getNewIndex());
            pictureBiz.update(uPicture);

            uPicture.setId(picture.getOldIndexId());
            uPicture.setOrderNum(picture.getOldIndex());
            pictureBiz.update(uPicture);

            return R.ok();
        } catch (Exception e) {
            System.out.println(e.toString());
            return R.error();
        }


    }


}
