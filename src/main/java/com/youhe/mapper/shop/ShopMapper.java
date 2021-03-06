package com.youhe.mapper.shop;

import com.youhe.entity.role.Role;
import com.youhe.entity.role.User_Role;
import com.youhe.entity.shop.Shop;

import java.util.List;


public interface ShopMapper {


    List<Shop> findShopList(Shop shop);

    //查找详情
    Shop findShopListView(Shop shop);

    //查找分类
    List<Shop> findCommodity(Shop shop);


//    //查看详情
//    List<Shop> findCommodityType(Shop shop);

    Long save(Shop shop);

    Long del(Shop shop);

    Long update(Shop shop);

    //查找商品详情页信息
    List<Shop> findshopDetail(Shop shop);

    //购物车信息
    List<Shop> findCarList(Shop shop);

    List<Shop> findSearchList(Shop shop);
}
