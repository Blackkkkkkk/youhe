package com.youhe.service.shop;

import com.github.pagehelper.PageInfo;
import com.youhe.entity.order.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单明细表 服务类
 * </p>
 *
 * @author Kalvin
 * @since 2019-04-16
 */
public interface OrderDetailService extends IService<OrderDetail> {

    /**
     * 分页：查找订单详情列表
     * @param orderDetail 订单详情参数
     * @return pageInfo
     */
    PageInfo<OrderDetail> findOrderDetails(OrderDetail orderDetail);

    int updateOrderStaus(String bOrderNum);
}
