package com.youhe.serviceImpl.Controller.orderController;

import com.youhe.biz.order.OrderBiz;
import com.youhe.biz.permisson.PermissonBiz;
import com.youhe.biz.redis.RedisBiz;
import com.youhe.biz.role.RoleBiz;
import com.youhe.biz.shop.ShopBiz;
import com.youhe.biz.shop.ShopUserIndexBiz;
import com.youhe.controller.user.shop.index.IndexController;
import com.youhe.entity.order.Order;
import com.youhe.entity.order.OrderDetails;
import com.youhe.entity.permission.Permission_Role;
import com.youhe.entity.role.Role;
import com.youhe.entity.shop.PayResult;
import com.youhe.entity.shop.Shop;
import com.youhe.initBean.redis.CartPrefix;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service

public class OrderControllerImpl {

    @Autowired
    private ShopUserIndexBiz shopUserIndexBiz;

    @Autowired
    private ShopBiz shopBiz;

    @Autowired
    private RedisBiz redisBiz;

    @Autowired
    private OrderBiz orderBiz;

    @Transactional(rollbackFor = Exception.class)
    public void save(PayResult payResult, String userAccount) {

        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        System.out.println(shiroUser.getUserAccount());
        List<Shop> shopList = searchList(userAccount);

        long payAmount = 0; //支付金额
        OrderDetails orderDetails = new OrderDetails();
        if (!CollectionUtils.isEmpty(shopList)) {
            for (Shop shop : shopList) {

                orderDetails.setBigOrderCode(payResult.getOutTradeNo())
                        .setOrderCode("DH-" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + (int) ((Math.random() * 9 + 1) * 10000))
                        .setName(shop.getName())
                        .setNum(shop.getNum())
                        .setPirce(shop.getPirce())
                        .setRemark(shop.getRemark());
                orderBiz.saveOrderDetails(orderDetails);

                payAmount += shop.getPirce() * shop.getNum();
            }

            Order order = new Order();
            order.setBigOrderCode(payResult.getOutTradeNo())
                    .setStatus(payResult.getPayState())
                    .setOrderEndTime(new Date())
                    .setTotalPrice(Integer.parseInt(payAmount + ""));

            orderBiz.saveOrder(order);

            //后期优化，省去一个遍历，直接放在上面
            for (Shop shop : shopList) {
                redisBiz.hdel(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "");
            }

        }

    }

    public List<Shop> searchList(String key) {
        return redisBiz.hscan(CartPrefix.getCartList, key);
    }
}
