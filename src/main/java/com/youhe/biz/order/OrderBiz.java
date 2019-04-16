package com.youhe.biz.order;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youhe.entity.order.Order;
import com.youhe.entity.order.OrderDetails;
import com.youhe.mapper.order.OrderMapper;
import com.youhe.mapper.order.OrdertMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderBiz {
    private Logger log = LoggerFactory.getLogger(OrderBiz.class);

    /*@Autowired
    private OrderMapper ordertMapper;

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
    }*/



}
