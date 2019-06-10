package com.youhe.controller.user.shop.shopDetail;


import com.github.pagehelper.PageInfo;
import com.youhe.biz.redis.RedisBiz;
import com.youhe.biz.shop.CollectBiz;
import com.youhe.biz.shop.PictureBiz;
import com.youhe.biz.shop.ShopBiz;
import com.youhe.biz.shop.ShopUserIndexBiz;
import com.youhe.entity.order.Order;
import com.youhe.entity.pay.Refund;
import com.youhe.entity.shop.Collect;
import com.youhe.entity.shop.Picture;
import com.youhe.entity.shop.Shop;
import com.youhe.entity.shop.Shop_index_carousel;
import com.youhe.initBean.redis.CartPrefix;
import com.youhe.mapper.shop.ShopMapper;
import com.youhe.service.shop.OrderDetailService;
import com.youhe.service.shop.OrderService;
import com.youhe.serviceImpl.Controller.orderController.OrderControllerImpl;
import com.youhe.utils.JsonDateValueProcessor;
import com.youhe.utils.R;
import com.youhe.utils.pay.PayUtil;
import com.youhe.utils.pay.sdk.domain.Response;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * Created by xiaoqiang on 2018/12/19.
 */
@Controller
@RequestMapping(value = "/shopDetail")
public class ShopDetailController {
    private Logger log = LoggerFactory.getLogger(ShopDetailController.class);

    @Autowired
    private ShopUserIndexBiz shopUserIndexBiz;

    @Autowired
    private ShopBiz shopBiz;

    @Autowired
    private RedisBiz redisBiz;

    @Autowired
    private PictureBiz pictureBiz;

    @Autowired
    private OrderControllerImpl orderController;

    @Autowired
    private OrderService orderBiz;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CollectBiz collectBiz;

    @RequestMapping(value = "/detail")
    public String shopDetail(Model model, Shop shop) {

        shop.setPageSize(0);
        List<Shop> list = shopBiz.findshopDetail(shop);

        model.addAttribute("list", list);
        return "user/shop/index/shopDetail";
    }

    @RequestMapping(value = "/myCollect")
    public String myCollect() {

        return "user/shop/order/my_collect";
    }


    @RequestMapping(value = "/saveCollect")
    @ResponseBody
    public R saveCollect(Collect collect) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        try {
            collect.setUserId(Integer.parseInt(shiroUser.getUid() + ""));
            collectBiz.save(collect);
            return R.ok("收藏成功");
        } catch (Exception e) {
            log.error(e.toString());
            return R.error("收藏失败");
        }


    }


    @RequestMapping(value = "/delCollect")
    @ResponseBody
    public R delCollect(Collect collect) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        try {
            collect.setUserId(Integer.parseInt(shiroUser.getUid() + ""));
            collectBiz.del(collect);
            return R.ok("取消收藏成功");
        } catch (Exception e) {
            log.error(e.toString());
            return R.error("取消收藏失败");
        }


    }

    @RequestMapping(value = "/CollectList")
    @ResponseBody
    public R CollectList(Collect collect) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        try {
            collect.setUserId(Integer.parseInt(shiroUser.getUid() + ""));
            List<Collect> list = collectBiz.find(collect);

            return R.ok().put("list", list);
        } catch (Exception e) {
            log.error(e.toString());
            return R.error("取消收藏失败").put("list", new ArrayList<Collect>());
        }

    }

    /*

    @RequestMapping(value = "/CollectList")
    @ResponseBody
    public R CollectList(Collect collect) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        try {
            collect.setUserId(Integer.parseInt(shiroUser.getUid() + ""));
            List<Collect> list = collectBiz.find(collect);

            return R.ok().put("list", list);
        } catch (Exception e) {
            log.error(e.toString());
            return R.error("取消收藏失败").put("list", new ArrayList<Collect>());
        }

    }
*/
    @RequestMapping(value = "/myOrderData")
    @ResponseBody
    public String myOrderData(Collect collect) {

        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        collect.setUserId(Integer.parseInt(shiroUser.getUid() + ""));
        List<Collect> list = collectBiz.find(collect);

        String shopIdList = "";
        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                shopIdList += list.get(i).getShopId() + ",";
            }
        }

        Shop shop = new Shop();
        shop.setShopIdList(shopIdList);
        List<Shop> shopList = shopBiz.findCarList(shop);


        // 自定义data类
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
        JSONArray json = JSONArray.fromObject(shopList, jsonConfig);
        String js = "{\"code\":0,\"msg\":\"\",\"count\":\" " + list.size() + " \",\"data\":" + json.toString() + "}";

        System.out.println(js);
        return js;
    }

}
