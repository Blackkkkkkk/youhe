package com.youhe.controller.sys.order;

import com.github.pagehelper.PageInfo;
import com.youhe.entity.order.Order;
import com.youhe.entity.order.OrderDetail;
import com.youhe.service.shop.OrderDetailService;
import com.youhe.service.shop.OrderService;
import com.youhe.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 订单管理
 */
@RestController
@RequestMapping(value = "order/manage")
public class OrderManageController {

    @Autowired
    private OrderService orderBiz;
    @Autowired
    private OrderDetailService orderDetailService;
    @GetMapping(value = "list")
    public ModelAndView list() {
        return new ModelAndView("sys/order/order");
    }

    @GetMapping(value = "detail")
    public ModelAndView detail(String bOrderNum) {
        ModelAndView model=new ModelAndView();
        model.setViewName("sys/order/orderDetail");
        model.addObject("bOrderNum",bOrderNum);
        return model;
    }

    /**
     * 订单列表数据接口
     * @param order
     * @return R
     */
    @PostMapping(value = "listData")
    public R listData(Order order) {
        List<Order> orderList = orderBiz.findOrderBy(order);
        return R.ok().put("orderList",orderList);
    }

    /**
     * 订单详情列表数据接口
     * @param orderDetail
     * @return R
     */
    @GetMapping(value = "detailListData")
    public R detailListData(OrderDetail orderDetail) {
      /*  orderDetail.setBOrderNum("SBO201904194726");*/
        PageInfo<OrderDetail> page = orderDetailService.findOrderDetails(orderDetail);
        return R.ok().put("page", page);
    }
    /**
     * 修改订单状态
     * @param bOrderNum
     * @return R
     */
    @GetMapping(value = "updateStaus")
    public R updateOrderStatus (String bOrderNum) {
        int i = orderDetailService.updateOrderStaus(bOrderNum);
        if(i>0){
            return R.ok();
        }
            return R.error();
    }
}
