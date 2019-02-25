package com.youhe.mapper.shop;

import com.youhe.entity.role.Role;
import com.youhe.entity.role.User_Role;
import com.youhe.entity.shop.Shop;

import java.util.List;


public interface ShopMapper {


    //查找角色表
    List<Shop> findShopList(Shop shop);

    //查找分类
    List<Shop> findCommodity(Shop shop);

    Long save(Shop shop);

    Long del(Shop shop);

    Long update(Shop shop);
}
