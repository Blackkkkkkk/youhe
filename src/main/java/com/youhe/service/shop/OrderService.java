package com.youhe.service.shop;

import com.github.pagehelper.PageInfo;
import com.youhe.entity.order.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youhe.entity.order.OrderDetail;
import com.youhe.entity.order.OrderDetails;
import com.youhe.entity.shop.Shop;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品订单表 服务类
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-16
 */
public interface OrderService extends IService<Order> {

    List<Order> findOrder(Order order);

    void saveOrder(Order order);

    void saveOrderDetail(OrderDetail orderDetail);

    /**
     * 分页：查找订单列表
     * @param order 订单参数
     * @return pageInfo
     */
   List<Order> findOrderBy(Order order);



//    void shoppingOrder();

    Map<String,Object> shoppingOrder();

    Map<String,Object> shoppingPurchase(Shop shop);






}
