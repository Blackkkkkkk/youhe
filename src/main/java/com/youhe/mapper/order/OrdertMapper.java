package com.youhe.mapper.order;


import com.youhe.entity.order.Order;
import com.youhe.entity.order.OrderDetails;

import java.util.List;


public interface OrdertMapper {


    List<Order> findOrder(Order order);

    void updateOrder(Order order);

    void saveOrder(Order order);

    void delOrder(Order order);

    List<OrderDetails> findOrderDetails(OrderDetails orderDetails);

    void updateOrderDetails(OrderDetails orderDetails);

    void saveOrderDetails(OrderDetails orderDetails);

    void delOrderDetails(OrderDetails orderDetails);

}
