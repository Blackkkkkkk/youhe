package com.youhe.serviceImpl.Controller.orderController;

import com.youhe.biz.order.OrderBiz;
import com.youhe.biz.permisson.PermissonBiz;
import com.youhe.biz.redis.RedisBiz;
import com.youhe.biz.role.RoleBiz;
import com.youhe.biz.shop.ShopBiz;
import com.youhe.biz.shop.ShopUserIndexBiz;
import com.youhe.controller.user.shop.index.IndexController;
import com.youhe.entity.order.Order;
import com.youhe.entity.order.OrderDetail;
import com.youhe.entity.order.OrderDetails;
import com.youhe.entity.pay.Refund;
import com.youhe.entity.permission.Permission_Role;
import com.youhe.entity.role.Role;
import com.youhe.entity.shop.PayResult;
import com.youhe.entity.shop.Shop;
import com.youhe.initBean.redis.CartPrefix;
import com.youhe.initBean.websocket.WebSocketServer;
import com.youhe.service.shop.OrderService;
import com.youhe.utils.R;
import com.youhe.utils.pay.PayUtil;
import com.youhe.utils.pay.sdk.domain.Response;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service

public class OrderControllerImpl {

    private Logger log = LoggerFactory.getLogger(OrderControllerImpl.class);
    @Autowired
    private ShopUserIndexBiz shopUserIndexBiz;

    @Autowired
    private ShopBiz shopBiz;

    @Autowired
    private RedisBiz redisBiz;

    @Autowired
    private OrderService orderBiz;

    @Transactional(rollbackFor = Exception.class)
    public void save(PayResult payResult, String userAccount) {

        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        System.out.println(shiroUser.getUserAccount());

        List<Shop> shopList = searchSaveList(userAccount);

        long payAmount = 0; //支付金额
        OrderDetail orderDetails = new OrderDetail();
        if (!CollectionUtils.isEmpty(shopList)) {
            for (Shop shop : shopList) {

                orderDetails
                        .setBOrderNum(payResult.getOutTradeNo())
                        .setSOrderNum("DH-" + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + (int) ((Math.random() * 9 + 1) * 10000))
                        .setCommodityName(shop.getName())
                        .setNum(shop.getNum())
                        .setPrice(shop.getPirce())
                        .setRemark(shop.getRemark());
                orderBiz.saveOrderDetail(orderDetails);


                //对应扣除库存
                shop.setNum(shop.getNum() - shop.getCartNum());
                shopBiz.update(shop);

                payAmount += shop.getPirce() * shop.getCartNum();  // 计算支付金额
            }

            //保存到数据表的大订单号
            Order order = new Order();
            order.setBOrderNum(payResult.getOutTradeNo());
            order.setStatus(payResult.getPayState());
            order.setTotalPrice(Integer.parseInt(payAmount + ""));

            orderBiz.saveOrder(order);

            //固定写死 id 20
            try {
                WebSocketServer.sendInfo(order.getBOrderNum(), "20");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //后期优化，省去一个遍历，直接放在上面
            for (Shop shop : shopList) {
                redisBiz.hdel(CartPrefix.getCartList, shiroUser.getUserAccount(), shop.getId() + "");
            }

        }

    }


    @Transactional(rollbackFor = Exception.class)
    public void refund(Refund refund, Long userId) {

        Order order = new Order();
        order.setBOrderNum(refund.getOutTradeNo());

        List<Order> list = orderBiz.findOrder(order);

        if (!CollectionUtils.isEmpty(list)) {
            order = list.get(0);
            refund.setAmount(Long.parseLong(order.getTotalPrice() + ""))
                    .setUserId(userId)
                    .setRefundAmount(Long.parseLong(order.getTotalPrice() + ""))
                    .setRemark("");
            try {
                Response response = PayUtil.refundApply(refund);
                System.out.println(1);
            } catch (Exception e) {
                log.info(e.toString());
            }
        } else {

        }

    }


    @Transactional(rollbackFor = Exception.class)
    public void refundResult(Refund refund, Long userId) {


        /*
        Order order = new Order();
        order.setBigOrderCode(refund.getOutTradeNo());

        List<Order> list = orderBiz.findOrder(order);

        if (!CollectionUtils.isEmpty(list)) {
            order = list.get(0);
            refund.setAmount(Long.parseLong(order.getTotalPrice() + ""))
                    .setUserId(userId)
                    .setRefundAmount(Long.parseLong(order.getTotalPrice() + ""))
                    .setRemark("");
        } else {

        }
*/
    }


    public List<Shop> searchList(String key) {
        return redisBiz.hscan(CartPrefix.getCartList, key);
    }

    public List<Shop> searchSaveList(String key) {
        return redisBiz.hscanSave(CartPrefix.getCartList, key);
    }
}
