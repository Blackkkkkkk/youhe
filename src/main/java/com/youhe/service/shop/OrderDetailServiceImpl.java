package com.youhe.service.shop;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youhe.entity.order.OrderDetail;
import com.youhe.mapper.order.OrderDetailMapper;
import com.youhe.mapper.order.OrderMapper;
import com.youhe.service.shop.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    @Autowired
    private OrderMapper orderMapper;

    @Override
    public PageInfo<OrderDetail> findOrderDetails(OrderDetail orderDetail) {
        PageHelper.startPage(orderDetail.getPageNum(), orderDetail.getPageSize());
        List<OrderDetail> orderDetails = orderMapper.findOrderDetail(orderDetail);
        return new PageInfo<>(orderDetails);
    }

    @Override
    public int updateOrderStaus(String bOrderNum) {
       return   orderMapper.updateSatus(bOrderNum);
    }
}
