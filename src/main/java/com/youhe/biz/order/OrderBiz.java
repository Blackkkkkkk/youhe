package com.youhe.biz.order;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youhe.entity.order.Order;
import com.youhe.entity.order.OrderDetails;
import com.youhe.mapper.order.OrdertMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderBiz {
    private Logger log = LoggerFactory.getLogger(OrderBiz.class);

    @Autowired
    private OrdertMapper ordertMapper;


    public List<Order> findOrder(Order order) {
        return ordertMapper.findOrder(order);
    }

    public void updateOrder(Order order) {
        ordertMapper.updateOrder(order);
    }

    public void saveOrder(Order order) {
        ordertMapper.saveOrder(order);
    }

    public void delOrder(Order order) {
        ordertMapper.delOrder(order);
    }

    public List<OrderDetails> findOrderDetails(OrderDetails orderDetails) {
        return ordertMapper.findOrderDetails(orderDetails);
    }

    public void updateOrderDetails(OrderDetails orderDetails) {
        ordertMapper.updateOrderDetails(orderDetails);
    }

    public void saveOrderDetails(OrderDetails orderDetails) {
        ordertMapper.saveOrderDetails(orderDetails);
    }

    public void delOrderDetails(OrderDetails orderDetails) {
        ordertMapper.delOrderDetails(orderDetails);
    }

    /**
     * 分页：查找订单列表
     * @param order 订单参数
     * @return pageInfo
     */
    public PageInfo<Order> findOrderByPage(Order order) {
        PageHelper.startPage(order.getPageNum(), order.getPageSize());
        List<Order> orders = ordertMapper.findOrder(order);
        return new PageInfo<>(orders);
    }

    /**
     * 分页：查找订单列表
     * @param orderDetails 订单详情参数
     * @return pageInfo
     */
    public PageInfo<OrderDetails> findOrderDetailByPage(OrderDetails orderDetails) {
        PageHelper.startPage(orderDetails.getPageNum(), orderDetails.getPageSize());
        List<OrderDetails> orderDetail = ordertMapper.findOrderDetails(orderDetails);
        return new PageInfo<>(orderDetail);
    }

}
