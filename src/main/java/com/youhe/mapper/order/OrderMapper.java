package com.youhe.mapper.order;

import com.github.pagehelper.PageInfo;
import com.youhe.entity.order.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youhe.entity.order.OrderDetail;
import com.youhe.entity.order.OrderDetails;

import java.util.List;

/**
 * <p>
 * 商品订单表 Mapper 接口
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-16
 */
public interface OrderMapper extends BaseMapper<Order> {
    //修改角色表
    void updates(String bigOrderCode,String deliveryAddr);
     //查询订单
    List<Order> findOrder(Order order);
    //查询订单详情
    List<OrderDetail> findOrderDetail(OrderDetail orderDetail);
    //修改订单状态
   int updateSatus(String bOrderNum);
}
