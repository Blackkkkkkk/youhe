package com.youhe.controller.order;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.youhe.biz.redis.RedisBiz;
import com.youhe.controller.comm.BaseController;
import com.youhe.entity.order.OrderDetails;
import com.youhe.entity.shop.PayResult;
import com.youhe.entity.shop.Shop;
import com.youhe.initBean.redis.CartPrefix;
import com.youhe.serviceImpl.Controller.orderController.OrderControllerImpl;
import com.youhe.utils.R;
import com.youhe.utils.pay.PayUtil;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 【作用】商品订单接口<br>
 * 【说明】（无）
 *
 * @author kalvin
 * @Date 2019年04月03日 14:41
 */
@RestController
@RequestMapping(value = "order")
public class OrderController extends BaseController {

    @Autowired
    private OrderControllerImpl orderController;
    @Autowired
    private RedisBiz redisBiz;

    /**
     * 统一下单接口（不包含分账）
     */
    @GetMapping(value = "cashierPay")
    public R cashierPay() {
        String userAccount = ShiroUserUtils.getShiroUser().getUserAccount();
        if (StrUtil.isBlank(userAccount)) {
            return R.error("请登录后再下单支付");
        }

        // 获取购物车商品
        List<Shop> shops = redisBiz.hscan(CartPrefix.getCartList, userAccount);
        ArrayList<OrderDetails> orderDetails = new ArrayList<>();
        long totalAmount = 0L;    // 订单总金额
        for (Shop shop : shops) {
            OrderDetails orderDetail = new OrderDetails();
            Integer price = shop.getPirce();
            int cartNum = shop.getCartNum();
            orderDetail.setName(shop.getName()).setNum(cartNum).setPirce(price);
            orderDetails.add(orderDetail);
            totalAmount += price * cartNum;
        }
        String outTradeNo = "GO-" + DateUtil.format(new Date(), "yyyyMMddhhmm") + RandomUtil.randomString(3);
        String url = PayUtil.cashierPay(outTradeNo, totalAmount, orderDetails);
        return R.ok().put("url", url);
    }

    /**
     * 支付成功的同步接口
     * @param payResult 支付结果
     * @param model
     * @return
     */
    @RequestMapping(value = "/asynchronousPay")
    public ModelAndView syncNotifyUrl(PayResult payResult, Model model) {
        LOGGER.info("支付结果通知：{}", payResult.toString());
        String orderNo = payResult.getOutTradeNo();
        // 通过订单编号获取用户ID
        // todo 订单表设计不合理，订单表应该有userId
        String userAccount = "admin";

        orderController.save(payResult, userAccount);
        List<Shop> shopList = redisBiz.hscan(CartPrefix.getCartList, userAccount);
        model.addAttribute("shopList", shopList);
        return new ModelAndView("redirect:touristShop/shoppingCart");
    }

    /**
     * 异步通知接口
     * @return
     */
    public R asyncNotifyUrl() {
        return R.ok();
    }
}
