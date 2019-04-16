package com.youhe.mapper.order;

import com.youhe.entity.order.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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

    List<Order> findOrder(Order order);

}
