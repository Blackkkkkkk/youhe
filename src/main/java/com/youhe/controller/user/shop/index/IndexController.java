package com.youhe.controller.user.shop.index;


import com.github.pagehelper.PageInfo;
import com.youhe.biz.order.OrderBiz;
import com.youhe.biz.redis.RedisBiz;
import com.youhe.biz.shop.PictureBiz;
import com.youhe.biz.shop.ShopBiz;
import com.youhe.biz.shop.ShopUserIndexBiz;
import com.youhe.entity.order.Order;
import com.youhe.entity.pay.Refund;
import com.youhe.entity.shop.PayResult;
import com.youhe.entity.shop.Picture;
import com.youhe.entity.shop.Shop;
import com.youhe.entity.shop.Shop_index_carousel;
import com.youhe.initBean.redis.CartPrefix;
import com.youhe.serviceImpl.Controller.orderController.OrderControllerImpl;
import com.youhe.utils.R;
import com.youhe.utils.pay.PayUtil;
import com.youhe.utils.pay.sdk.domain.Response;
import com.youhe.utils.pay.sdk.pay.PaymentHelper;
import com.youhe.utils.pay.sdk.pay.domain.cashierPay.CashierRequest;
import com.youhe.utils.pay.sdk.pay.domain.cashierPay.CashierResponse;
import com.youhe.utils.pay.sdk.pay.domain.cashierPay.OrderGoods;
import com.youhe.utils.pay.sdk.pay.domain.cashierPay.OrderInfo;
import com.youhe.utils.pay.sdk.utils.Config;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.activiti.editor.language.json.converter.util.CollectionUtils;
import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;


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
    private OrderBiz orderBiz;

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
                setHotSale_Sort(1).setIsNewProductOrderNum_Sort(1);

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

        List<Shop> shopList = shopBiz.findShopList(shop);

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
                redisBiz.hput(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "");
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

    //支付成功的同步接口
    @RequestMapping(value = "/asynchronousPay")
    public String asynchronousPay(PayResult payResult, Model model) {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        orderController.save(payResult, shiroUser.getUserAccount());

        if (shiroUser.getUserAccount() != null || shiroUser.getUserAccount() != "") {
            try {
                List<Shop> shopList = searchList(shiroUser.getUserAccount());

                model.addAttribute("shopList", shopList);
            } catch (Exception e) {
                System.out.println(e.toString());
            }

        }

        return "redirect:shoppingCart";
    }


    //支付接口
    @RequestMapping(value = "/pay")
    @ResponseBody
    public R pay() {
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        List<Shop> shopList = searchList(shiroUser.getUserAccount());
        Response response = new Response();
        try {
            initialize();
            response = cashierPay();//统一下单接口，不包含分账信息

            if (response.getReturnCode().equals("0000")) {
                return R.ok(1, "").put("url", response.getCasherUrl());
            } else {
                return R.ok(0, "支付失败请联系管理员");
            }
        } catch (Exception e) {
            return R.ok(0, "支付失败请联系管理员");
        }

    }

    //退款接口
    @RequestMapping(value = "/refund")
    @ResponseBody
    public R refund(Refund refund) {

        try {
            Order order = new Order();

            order.setBigOrderCode(refund.getOutTradeNo());

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


    //退款接口
    @RequestMapping(value = "/refundResult")
    @ResponseBody
    public R refundResult(Refund refund) {

        try {

            System.out.println("1");
        } catch (Exception e) {
            System.out.println("2");
        }
        return R.ok();
    }

    public void initialize() throws URISyntaxException {

        Config.initialize(new File(IndexController.class.getClassLoader().getResource("").getPath() + "static/payproperties/config_uat.properties"));

        /*  打印路径地址

        System.out.println(IndexController.class.getClassLoader().getResource("").getPath() + "static/payproperties/config_uat.properties");
        System.out.println(IndexController.class.getResource("").getPath());
        System.out.println(this.getClass().getClassLoader().getResource("").getPath());
        */

        System.setProperty("sdk.mode", "debug");
    }

    /**
     * 统一下单接口（不包含分账）
     */
    public Response cashierPay() throws Exception {

        String outTradeNo = "SH-" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + (int) ((Math.random() * 9 + 1) * 10000); //交易编号,商户侧唯一
        String customerCode = Config.getCustomerCode(); //商户编号
        String terminalNo = "10001"; //终端号，写死
        String clientIp = "127.0.0.1"; //IP
        long payAmount = 2; //支付金额,分为单位
        String payCurrency = "CNY"; //币种，写死
        String channelType = "01"; //渠道类型，写死

        String notifyUrl = "http://www.baidu.com"; //异步通知地址
        String redirectUrl = "http://238r9j8196.wicp.vip/touristShop/asynchronousPay"; //同步通知地址
        String attachData = payAmount + ""; //备注数据,可空
        String transactionStartTime = Config.getTransactionStartTime();  //交易开始时间
        String transactionEndTime = ""; //交易结束时间

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId("test");
        orderInfo.setBusinessType("test");
        orderInfo.addGood(new OrderGoods("红富士", "1箱", 1));
        orderInfo.addGood(new OrderGoods("82年的茅台", "1瓶", 1));

        CashierRequest order = new CashierRequest(outTradeNo, customerCode, terminalNo, clientIp, orderInfo,
                payAmount, payCurrency, channelType, notifyUrl, redirectUrl,
                attachData, transactionStartTime, transactionEndTime);

//        order.setRechargeMemCustCode("5651300000001052");
        //----分账设置，如需分账，一定要传true
        order.setNeedSplit(false);
        //是否限制信用卡支付，不填默认是false(即不限制)
        order.setNoCreditCards(true);
        CashierResponse response = PaymentHelper.cashierPay(order);
        System.out.println("收银台订购地址：" + response.getCasherUrl());

        return response;
    }

}
