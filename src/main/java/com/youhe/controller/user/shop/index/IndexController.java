package com.youhe.controller.user.shop.index;


import com.github.pagehelper.PageInfo;
import com.youhe.biz.redis.RedisBiz;
import com.youhe.biz.shop.PictureBiz;
import com.youhe.biz.shop.ShopBiz;
import com.youhe.biz.shop.ShopUserIndexBiz;
import com.youhe.entity.order.Order;
import com.youhe.entity.pay.Refund;
import com.youhe.entity.shop.Picture;
import com.youhe.entity.shop.Shop;
import com.youhe.entity.shop.Shop_index_carousel;
import com.youhe.initBean.redis.CartPrefix;
import com.youhe.service.shop.OrderService;
import com.youhe.serviceImpl.Controller.orderController.OrderControllerImpl;
import com.youhe.utils.R;
import com.youhe.utils.pay.PayUtil;
import com.youhe.utils.pay.sdk.domain.Response;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by xiaoqiang on 2018/12/19.
 */
@Controller
@RequestMapping(value = "/touristShop")
public class IndexController {
    private Logger log = LoggerFactory.getLogger(IndexController.class);

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

    @RequestMapping(value = "/index")
    public String index(Model model, Shop_index_carousel shop_index_carousel) {

        // 获取轮播图信息
        List<Shop_index_carousel> carouselList = shopUserIndexBiz.findPictureList(shop_index_carousel);

        model.addAttribute("carouselList", carouselList);

        Shop shop = new Shop();

        shop.setIsIndex(1);
        shop.setStatus(1).
                setRegister_Sort(1).
                setTop_Sort(1).
                setHotSale_Sort(1).setIsNewProductOrderNum_Sort(1).setType(1);

        List<Shop> shopList = shopBiz.findShopList(shop);

        // 商品轮播图
        Picture picture = new Picture();
        picture.setType(3);
        List<Picture> pictureList = pictureBiz.findPictureList(picture);

        model.addAttribute("shopList", shopList);
        model.addAttribute("pictureList", pictureList);

        return "user/shop/index/index";
    }

    @RequestMapping(value = "/viewList")
    @ResponseBody
    public R viewList(Model model, Shop shop) {
        shop.setIsIndex(1);
        shop.setStatus(1).
                setRegister_Sort(1).
                setTop_Sort(1).
                setHotSale_Sort(1).setIsNewProductOrderNum_Sort(1);

        List<Shop> shopList = shopBiz.findShopListView(shop);

        if (!CollectionUtils.isEmpty(shopList)) {
            return R.ok(1, "").put("shopList", shopList.get(0));
        } else {
            return R.ok(0, "");
        }
    }


    @RequestMapping(value = "/shoppingCart")
    public String shoppingCart(Model model, Shop_index_carousel shop_index_carousel) {

        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();

        System.out.println(shiroUser.getUserAccount() == null || shiroUser.getUserAccount() == "");
        if (shiroUser.getUserAccount() != null || shiroUser.getUserAccount() != "") {
            try {
                List<Shop> shopList = searchList(shiroUser.getUserAccount());

                model.addAttribute("shopList", shopList);
            } catch (Exception e) {
                System.out.println(e.toString());
            }

        }

        return "user/shop/shoppingCart/shopping-cart";
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
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();

        Map<String, Object> map = new HashMap<String, Object>();

        int Status = 4;

        if (shiroUser.getUserAccount() == null || shiroUser.getUserAccount() == "") {
            Status = 3;
        } else {
            // 判断缓存是否有该物品
            Boolean exists = redisBiz.hhasKey(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "");
            if (exists) {
                // 数量自增1
                Long num = redisBiz.hincrement(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "", 1);
                Status = 2;
            } else {
                redisBiz.hput(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "", 1);
                Status = 1;
            }

            redisBiz.expire(CartPrefix.getCartList, shiroUser.getUserAccount());
        }

        List<Shop> shopList = searchList(shiroUser.getUserAccount());

        map.put("shopList", shopList);
        map.put("Status", Status);

        return map;
    }

    // 查询购物车的信息
    /*
     * key : 查询的键 购物车主要是用户的登录账户
     */
    public List<Shop> searchList(String key) {
        return redisBiz.hscan(CartPrefix.getCartList, key);
    }


    @RequestMapping(value = "/initCart")
    @ResponseBody
    public List<Shop> initCart() {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        return redisBiz.hscan(CartPrefix.getCartList, shiroUser.getUserAccount());
    }


    @RequestMapping(value = "/delCart")
    @ResponseBody
    public int delCart(Shop shop) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        Long status = redisBiz.hdel(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "");
        if (status == 1l) {
            return 1;
        } else {
            return 2;
        }
    }

    @RequestMapping(value = "/carNumUD")
    @ResponseBody
    public R carNumUD(Shop shop) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();

        redisBiz.hincrement(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "", shop.getCarNumUD());

        List<Shop> shopList = searchList(shiroUser.getUserAccount());

        return R.ok(1, "修改成功").put("shopList", shopList);
    }

    @GetMapping(value = "/shopList")
    public String shopList(Model model, Shop shop) {
        shop.setStatus(1).
                setRegister_Sort(1).
                setHotSale_Sort(1).setIsNewProductOrderNum_Sort(1);

        shop.setPageNum(shop.getPageNum());
        shop.setPageSize(shop.getPageSize());
        PageInfo<Shop> pageInfo = shopBiz.findCommodityByPage(shop);
        System.out.println(pageInfo.toString());
        List<Shop> list = pageInfo.getList();
        model.addAttribute("cId", shop.getCid());
        model.addAttribute("shopList", list);
        model.addAttribute("pageInfo", pageInfo);
        return "user/shop/index/shop_list";
    }


    @RequestMapping(value = "/commodityMenu")
    public String commodity(Model model, Shop shop) {
        model.addAttribute("cId", shop.getCid());
        return "user/shop/index/commodityMenu";
    }

    //退款接口
    @RequestMapping(value = "/refund")
    @ResponseBody
    public R refund(Refund refund) {

        try {
            Order order = new Order();

            order.setBOrderNum(refund.getOutTradeNo());

            List<Order> list = orderBiz.findOrder(order);

            if (!CollectionUtils.isEmpty(list)) {
                order = list.get(0);
                refund.setAmount(Long.parseLong(order.getTotalPrice() + ""))
                        .setRefundAmount(Long.parseLong(order.getTotalPrice() + ""));
                Response response = PayUtil.refundApply(refund);
                System.out.println(refund);
            } else {

            }

        } catch (Exception e) {
            log.info(e.toString());
        }
        return R.ok();
    }


    @GetMapping(value = "orderList")
    public ModelAndView orderList() {
        return new ModelAndView("user/shop/order/my_order");
    }

}
