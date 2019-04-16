package com.youhe.service.shop;

import com.youhe.entity.order.OrderDetail;
import com.youhe.mapper.order.OrderDetailMapper;
import com.youhe.service.shop.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-16
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}
