package com.youhe.service.shop;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youhe.entity.order.Order;
import com.youhe.entity.order.OrderDetail;
import com.youhe.entity.order.OrderDetails;
import com.youhe.mapper.order.OrderMapper;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品订单表 服务实现类
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-16
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderDetailService orderDetailService;

    @Override
    public List<Order> findOrder(Order order) {
        return baseMapper.findOrder(order);
    }

    @Override
    public void updateOrder(Order order) {
        super.updateById(order);
    }

    @Override
    public void saveOrder(Order order) {
        super.save(order);
    }


    @Override
    public void saveOrderDetail(OrderDetail orderDetail) {
        orderDetailService.save(orderDetail);
    }


    @Override
    public PageInfo<Order> findOrderByPage(Order order) {
        PageHelper.startPage(order.getPageNum(), order.getPageSize());
        List<Order> orders = this.findOrder(order);
        return new PageInfo<>(orders);
    }

    @Override
    public PageInfo<OrderDetail> findOrderDetailByPage(OrderDetail orderDetail) {
        PageHelper.startPage(orderDetail.getPageNum(), orderDetail.getPageSize());
        List<OrderDetail> orderDetails = orderDetailService.list(new QueryWrapper<OrderDetail>().lambda()
                .eq(OrderDetail::getBOrderNum, orderDetail.getBOrderNum()));
        return new PageInfo<>(orderDetails);
    }
}
