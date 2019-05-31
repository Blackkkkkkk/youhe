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
import java.util.*;

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
    public List<Order> findOrderBy(Order order) {
        /*PageHelper.startPage(order.getPageNum(), order.getPageSize());*/
        List<Order> orders = this.findOrder(order);
        return orders;
    }


    //结算
    @Override
    public Map<String, Object> shoppingOrder() {
        //获取用户信息
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        Map<String, Object> resultMap = new HashMap<>();
//        List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
        List<Shop> shopList = new ArrayList<>(0);

        if (shiroUser.getUserAccount() != null || shiroUser.getUserAccount() != "") {
            //通过用户账号获取购物车详情
            shopList = redisBiz.hscan(CartPrefix.getCartList, shiroUser.getUserAccount());
            //大订单号自动生成的订单号
            String bigOrderCode = getBigOrderCode();
            Order order = new Order();

            //总金额
            int allPrices = 0;
            String pageaddr = "";
            String saveFileName = "";
            int cartNum = 0;
            int cartPrices = 0;
            int pirce = 0;
            String smallOrderCode = "";
            List<OrderDetail> list = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(shopList)) {
                for (Shop shop : shopList) {
                    OrderDetail orderDetail = new OrderDetail();
                    //小订单号
                    smallOrderCode = getSmallOrderCode();
                    int num = shop.getCartNum();

                    pirce = shop.getPirce();
                    allPrices += shop.getPirce() * num;
                    saveFileName = shop.getSaveFileName();
//                    cartNum=shop.getCartNum();
                    cartPrices = shop.getPirce() * num;
                    pageaddr = shop.getPageaddr();

                    orderDetail.setCommodityId(Long.valueOf(shop.getCommodityId()));
                    orderDetail.setNum(shop.getCartNum());
                    orderDetail.setPrice(shop.getPirce() * num);
                    orderDetail.setCommodityName(shop.getName());
                    orderDetail.setRemark(shop.getRemark());
                    orderDetail.setBOrderNum(bigOrderCode);
                    orderDetail.setSOrderNum(smallOrderCode);
                    orderDetailService.save(orderDetail);
                    orderDetail.setPageaddr(shop.getPageaddr());
                    orderDetail.setSaveFileName(shop.getSaveFileName());
                    orderDetail.setOnePrice(String.valueOf(shop.getPirce()));
                    shop.setCartPrices(shop.getPirce() * num);
                    shop.setAllPrices(allPrices);


//                    resultMap.put("orderDetail",orderDetail);
                    list.add(orderDetail);
                }
                order.setStatus(30);
                order.setUserId(Integer.parseInt(String.valueOf(shiroUser.getUid())));
                order.setBOrderNum(bigOrderCode);
                order.setTotalPrice(allPrices);
                super.save(order);

                resultMap.put("order", order);
//                resultMap.put("cartNum",cartNum);
                resultMap.put("pirce", pirce);
                resultMap.put("cartPrices", cartPrices);
//                resultMap.put("allPrice",allPrice);
                resultMap.put("bigOrderCode", bigOrderCode);
                resultMap.put("orderDetail", list);
                resultMap.put("allPrices", allPrices);
            }
        }
        return resultMap;
    }

    //立即购买
    @Override
    public Map<String, Object> shoppingPurchase(Shop shop) {
        //获取用户信息
        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        Map<String, Object> resultMap = new HashMap<>();
//        List<Shop> shopList = new ArrayList<>(0);
        List<Shop> shopList = shopBiz.findShopList(shop);
        String remark = shop.getRemark();
        int cartNum = shop.getCartNum();
        int pirce = 0;
        String name = "";
        String pageaddr = "";
        List<Shop> shopListNew = new ArrayList<Shop>();
        if (shiroUser.getUserAccount() != null || shiroUser.getUserAccount() != "") {
            //通过用户账号获取购物车详情
//            shopList = redisBiz.hscan(CartPrefix.getCartList,shiroUser.getUserAccount());
            //大订单号自动生成的订单号
            String bigOrderCode = getBigOrderCode();
            Order order = new Order();
            OrderDetail orderDetail = new OrderDetail();
            //总金额
            int allPrices = 0;
            int cartPrices = 0;
            List<OrderDetail> list = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(shopList)) {
                Shop shop1 = shopList.get(0);
                shopListNew.add(shop1);
                for (Shop shops : shopListNew) {
//                    cartPrices=shop.getPirce()*cartNum;
                    //小订单号自动生成的订单号
                    //名字
                    name = shops.getName();
                    String smallOrderCode = getSmallOrderCode();
                    allPrices += shops.getPirce() * cartNum;
                    order.setStatus(30);
                    order.setUserId(Integer.parseInt(String.valueOf(shiroUser.getUid())));
                    order.setBOrderNum(bigOrderCode);
                    order.setTotalPrice(shops.getPirce());
                    orderDetail.setCommodityId(Long.valueOf(shops.getCommodityId()));
                    orderDetail.setNum(cartNum);
//                    orderDetail.setPrice(shops.getPirce());
                    orderDetail.setPrice(shops.getPirce() * cartNum);
                    orderDetail.setCommodityName(shops.getName());
                    orderDetail.setRemark(remark);
                    orderDetail.setBOrderNum(bigOrderCode);
                    orderDetail.setSOrderNum(smallOrderCode);
                    shops.setRemark(remark);
                    shops.setAllPrices(allPrices);
                    shops.setNum(cartNum);
                    shops.setBigOrderCode(bigOrderCode);
                    list.add(orderDetail);
                    orderDetailService.save(orderDetail);
                    super.save(order);
                    orderDetail.setPageaddr(shops.getPageaddr());
                    orderDetail.setSaveFileName(shops.getSaveFileName());
                    orderDetail.setOnePrice(String.valueOf(shops.getPirce()));
                }


                resultMap.put("order", order);
//                resultMap.put("cartNum",cartNum);
                //单价
                resultMap.put("pirce", pirce);
                resultMap.put("name", name);
                resultMap.put("cartNum", cartNum);
                resultMap.put("cartPrices", cartPrices);
//                resultMap.put("allPrice",allPrice);
                resultMap.put("bigOrderCode", bigOrderCode);
                resultMap.put("orderDetail", list);
                //总价
                resultMap.put("allPrices", allPrices);
            }
        }
        return resultMap;
    }


    //随机生成的大订单号
    public String getBigOrderCode() {
        return "SBO" + DateUtil.format(new Date(), "yyyyMMdd") +
                (RandomUtil.randomInt(1000, 9999));
    }

    //随机生成的小订单号
    public String getSmallOrderCode() {
        return "SSO" + DateUtil.format(new Date(), "yyyyMMdd") +
                (RandomUtil.randomInt(10000, 99999));
    }


}
