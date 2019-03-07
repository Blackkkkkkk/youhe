package com.youhe.controller.sys.order;

import com.github.pagehelper.PageInfo;
import com.youhe.biz.order.OrderBiz;
import com.youhe.entity.order.Order;
import com.youhe.entity.order.OrderDetails;
import com.youhe.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 订单管理
 */
@RestController
@RequestMapping(value = "order")
public class OrderController {

    @Autowired
    private OrderBiz orderBiz;

    @GetMapping(value = "list")
    public ModelAndView list() {
        return new ModelAndView("sys/order/order");
    }

    @GetMapping(value = "detail")
    public ModelAndView detail() {
        return new ModelAndView("sys/order/orderDetail");
    }

    /**
     * 订单列表数据接口
     * @param order
     * @return R
     */
    @PostMapping(value = "listData")
    public R listData(Order order) {
        PageInfo<Order> page = orderBiz.findOrderByPage(order);
        return R.ok().put("page", page);
    }

    /**
     * 订单详情列表数据接口
     * @param orderDetails
     * @return R
     */
    @PostMapping(value = "detailListData")
    public R detailListData(OrderDetails orderDetails) {
        PageInfo<OrderDetails> page = orderBiz.findOrderDetailByPage(orderDetails);
        return R.ok().put("page", page);
    }
}
