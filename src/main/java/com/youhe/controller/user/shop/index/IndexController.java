package com.youhe.controller.user.shop.index;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.youhe.biz.redis.RedisBiz;
import com.youhe.biz.shop.PictureBiz;
import com.youhe.biz.shop.ShopBiz;
import com.youhe.biz.shop.ShopUserIndexBiz;
import com.youhe.entity.order.Order;
import com.youhe.entity.order.OrderDetail;
import com.youhe.entity.pay.Refund;
import com.youhe.entity.shop.Picture;
import com.youhe.entity.shop.Shop;
import com.youhe.entity.shop.Shop_index_carousel;
import com.youhe.initBean.redis.CartPrefix;
import com.youhe.mapper.shop.ShopMapper;
import com.youhe.service.shop.OrderDetailService;
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
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;


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
        Shop shopList = shopBiz.findShopListView(shop);

//        List<Shop> shop = shopBiz.findShopListView(shop);
        shopList.setStockNum(shopList.getNum());
//        System.out.println(shopList.getNum());

        return R.ok().put("shopList", shopList);

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

        Map<String, Map> shopMap = new HashMap<String, Map>();
        String jsonString = "";
        Map<String, Object> shopDetailMap = new HashMap<String, Object>();

        String params = "";// map转json 字符串
        ObjectMapper json = new ObjectMapper();
        try {
            if (shiroUser.getUserAccount() == null || shiroUser.getUserAccount() == "") {
                Status = 3;
            } else {
                // 判断缓存是否有该物品
                Boolean exists = redisBiz.hhasKey(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "");
                if (exists) {
                    //拿到redis 存取的商品主id,转成map对象 ，在看看二级sukid 存在不存在，存在增加数量，不存在便新增个suk商品
                    jsonString = redisBiz.hget(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "");
                    Map<String, Map> gson = (Map<String, Map>) JSON.parse(jsonString);
                    if (shop.getShopStyleId() != null) {
                        shop.setShopId(Integer.parseInt(shop.getShopStyleId() + ""));
                    }
                    List<Shop> listShopid = shopBiz.findCarList(shop);

                    if (jsonString != null) {
                        shopMap = gson.get(shop.getShopStyleId() == null ? listShopid.get(0).getShopId() + "" : shop.getShopStyleId() + "");
                        if (shopMap != null && !shopMap.isEmpty()) {
                            if (!CollectionUtils.isEmpty(listShopid)) {
                                shopDetailMap = gson.get(listShopid.get(0).getSpId() + "");
                                System.out.println(listShopid.get(0).getSpId() + "");
                                int num = Integer.parseInt(shopDetailMap.get("num") + "") + 1;
                                shopDetailMap.put("num", num);
                                shopDetailMap.put("amount", Integer.parseInt(shopDetailMap.get("pirce") + "") * Integer.parseInt(shopDetailMap.get("num") + ""));
                                gson.put(shop.getShopStyleId() == null ? listShopid.get(0).getShopId() + "" : shop.getShopStyleId() + "", shopDetailMap);
                                params = json.writeValueAsString(gson);
                                redisBiz.hput(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "", params); // 商品id
                                Status = 2;
                            }
                        } else {

                            if (!CollectionUtils.isEmpty(listShopid)) {
                                //  shop = listShopid.get(0);
                            }
                            shopDetailMap = new HashMap<String, Object>();
                            shopDetailMap.put("num", shop.getCartNum());
                            shopDetailMap.put("pirce", shop.getPirce());
                            shopDetailMap.put("sukName", shop.getSukChangeName());
                            shopDetailMap.put("amount", Integer.parseInt(shop.getPirce() + "") * Integer.parseInt(shopDetailMap.get("num") + ""));
                            gson.put(shop.getShopStyleId() + "", shopDetailMap);

                            params = json.writeValueAsString(gson);
                            System.out.println(params);
                            redisBiz.hput(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "", params); // 商品id
                            Status = 1;
                        }
                    }
                } else {
                    shopDetailMap = new HashMap<String, Object>();
                    Shop findshopPicture = new Shop();
                    findshopPicture.setId(shop.getId());
                    List<Shop> list = shopBiz.findCarList(findshopPicture);
                    if (!CollectionUtils.isEmpty(list)) {
                        findshopPicture = list.get(0);
                        shopDetailMap.put("num", 1);
                        shopDetailMap.put("pirce", findshopPicture.getPirce());
                        shopDetailMap.put("sukName", findshopPicture.getName());
                        System.out.println(shopDetailMap.get("num") != null);
                        System.out.println(findshopPicture.getPirce() != null);
                        if (shopDetailMap.get("num") != null && findshopPicture.getPirce() != null) {
                            shopDetailMap.put("amount", Integer.parseInt(findshopPicture.getPirce() + "") * Integer.parseInt(shopDetailMap.get("num") + ""));
                        }

                        if (shop.getShopStyleId() != null) {
                            shopMap.put(shop.getShopStyleId(), shopDetailMap);
                        } else {
                            shopMap.put(findshopPicture.getShopId() + "", shopDetailMap);

                        }
                    }
                    params = json.writeValueAsString(shopMap);
                    redisBiz.hput(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "", params); // 商品id
                    Status = 1;
                }
                redisBiz.expire(CartPrefix.getCartList, shiroUser.getUserAccount());
            }
            List<Shop> shopList = searchList(shiroUser.getUserAccount());

            map.put("shopList", shopList);
            map.put("Status", Status);

        } catch (Exception e) {
            log.error(e.toString());
        }

        return map;
    }


    // 查询购物车的信息
    /*
     * key : 查询的键 购物车主要是用户的登录账户
     */
    public List<Shop> searchList(String key) {
        return redisBiz.hscan(CartPrefix.getCartList, key);
    }

    //购物车数量增加
    @RequestMapping(value = "/addCartNum")
    @ResponseBody
    public R addCartNum(Shop shop) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();


        //拿到redis 存取的商品主id,转成map对象 ，在看看二级sukid 存在不存在，存在增加数量，不存在便新增个suk商品
        String jsonString = redisBiz.hget(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "");

        Map<String, Map> gson = (Map<String, Map>) JSON.parse(jsonString);
        List<Shop> listShopid = shopBiz.findCarList(shop);
        Map<String, Map> shopMap = new HashMap<String, Map>();
        Map<String, Object> shopDetailMap = new HashMap<String, Object>();
        String params = "";// map转json 字符串
        ObjectMapper json = new ObjectMapper();

        try {
            shopDetailMap = gson.get(shop.getShopStyleId() == null ? listShopid.get(0).getShopId() + "" : shop.getShopStyleId() + "");
            if (shopDetailMap != null && !shopDetailMap.isEmpty()) {
                int num = Integer.parseInt(shopDetailMap.get("num") + "") + shop.getCarNumUD();
                shopDetailMap.put("num", num);
                shopDetailMap.put("amount", Integer.parseInt(shopDetailMap.get("pirce") + "") * Integer.parseInt(shopDetailMap.get("num") + ""));
                gson.put(shop.getShopStyleId() == null ? listShopid.get(0).getShopId() + "" : shop.getShopStyleId() + "", shopDetailMap);
                params = json.writeValueAsString(gson);
                redisBiz.hput(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "", params); // 商品id
            }

            return R.ok().put("shopList", redisBiz.hscan(CartPrefix.getCartList, shiroUser.getUserAccount()));
        } catch (Exception e) {
            System.out.println(e.toString());
            return R.error("添加失败，请联系管理员");
        }


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
        Long status = 0l;
        String params = "";// map转json 字符串
        ObjectMapper json = new ObjectMapper();
        try {
            if (shop.getShopId() == null) {  //如果没有二级类目就只删除全部一级类目的商品
                status = redisBiz.hdel(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "");
            } else {
                String jsonString = redisBiz.hget(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "");
                Map<String, Map> gson = (Map<String, Map>) JSON.parse(jsonString);
                gson.remove(shop.getShopId() + "");
                params = json.writeValueAsString(gson);

                System.out.println(params.length());
                if (params.length() > 2) {
                    redisBiz.hput(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "", params); // 商品id
                } else {
                    status = redisBiz.hdel(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "");
                }
                status = 1l;
            }
        } catch (Exception e) {

        }

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


    @GetMapping(value = "shoppingUser")
    public String shoppingUser() {

        return "user/shop/login/shoppingUser";
    }

}
