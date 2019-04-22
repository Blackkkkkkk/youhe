package com.youhe.service.shop;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youhe.biz.redis.RedisBiz;
import com.youhe.biz.shop.ShopBiz;
import com.youhe.entity.order.Order;
import com.youhe.entity.order.OrderDetail;
import com.youhe.entity.order.OrderDetails;
import com.youhe.initBean.redis.CartPrefix;
import com.youhe.entity.shop.Shop;
import com.youhe.mapper.order.OrderMapper;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import lombok.experimental.Accessors;
import org.activiti.editor.language.json.converter.util.CollectionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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


    @Autowired
    private RedisBiz redisBiz;
    @Autowired
    private ShopBiz shopBiz;



    @Override
    public List<Order> findOrder(Order order) {
        return baseMapper.findOrder(order);
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

    @Override
    public List<Shop> shoppingOrder(){
        //获取用户信息
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        List<Shop> shopList = new ArrayList<>(0);
        if (shiroUser.getUserAccount() != null || shiroUser.getUserAccount() != "") {
            //通过用户账号获取购物车详情
           shopList = redisBiz.hscan(CartPrefix.getCartList,shiroUser.getUserAccount());
           //大订单号自动生成的订单号
            String bigOrderCode=getBigOrderCode();
            Order order = new Order();
            OrderDetail orderDetail = new OrderDetail();
            //总金额
            int allPrice=0;
            if(CollectionUtils.isNotEmpty(shopList)){
                for (Shop shop : shopList) {
                    String smallOrderCode=getSmallOrderCode();
                    int pirce=shop.getPirce();
                    int num=shop.getCartNum();
                    allPrice+=shop.getPirce()*num;
                    orderDetail.setCommodityId(Long.valueOf(shop.getCommodityId()));
                    orderDetail.setNum(shop.getCartNum());
                    orderDetail.setPrice(shop.getPirce()*num);
                    orderDetail.setCommodityName(shop.getName());
                    orderDetail.setRemark(shop.getRemark());
                    orderDetail.setBOrderNum(bigOrderCode);
                    orderDetail.setSOrderNum(smallOrderCode);
                    orderDetailService.save(orderDetail);
                }
                order.setStatus(30);
                order.setUserId(Integer.parseInt(String.valueOf(shiroUser.getUid())));
                order.setBOrderNum(bigOrderCode);
                order.setTotalPrice(allPrice);
                super.save(order);
            }
        }
        return shopList;
    }

    //立即购买
    @Override
    public List<Shop> shoppingPurchase(Shop shop){
        //获取用户信息
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
//        List<Shop> shopList = new ArrayList<>(0);
        List<Shop> shopList = shopBiz.findShopList(shop);
        if (shiroUser.getUserAccount() != null || shiroUser.getUserAccount() != "") {
            //通过用户账号获取购物车详情
//            shopList = redisBiz.hscan(CartPrefix.getCartList,shiroUser.getUserAccount());
            //大订单号自动生成的订单号
            String bigOrderCode=getBigOrderCode();
            Order order = new Order();
            OrderDetail orderDetail = new OrderDetail();
            //总金额
            int allPrice=0;
            if(CollectionUtils.isNotEmpty(shopList)){
                for (Shop shops : shopList) {
                    //小订单号自动生成的订单号
                    String smallOrderCode = getSmallOrderCode();
                    int pirce = shops.getPirce();
                    int num = shops.getCartNum();
                    allPrice += shops.getPirce() * num;
                    order.setStatus(30);
                    order.setUserId(Integer.parseInt(String.valueOf(shiroUser.getUid())));
                    order.setBOrderNum(bigOrderCode);
                    order.setTotalPrice(shops.getPirce());
                    orderDetail.setCommodityId(Long.valueOf(shops.getCommodityId()));
                    orderDetail.setNum(shops.getNum());
                    orderDetail.setPrice(shops.getPirce());
                    orderDetail.setCommodityName(shops.getName());
                    orderDetail.setRemark(shops.getRemark());
                    orderDetail.setBOrderNum(bigOrderCode);
                    orderDetail.setSOrderNum(smallOrderCode);

                }
                orderDetailService.save(orderDetail);
                super.save(order);
            }
        }
        return shopList;
    }



    //随机生成的大订单号
    public String getBigOrderCode(){
        return  "SBO"+ DateUtil.format(new Date(),"yyyyMMdd") +
                (RandomUtil.randomInt(1000,9999));
    }

    //随机生成的小订单号
    public String getSmallOrderCode(){
        return  "SSO"+ DateUtil.format(new Date(),"yyyyMMdd") +
                (RandomUtil.randomInt(10000,99999));
    }



}
