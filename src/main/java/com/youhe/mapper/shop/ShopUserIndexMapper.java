package com.youhe.mapper.shop;

import com.youhe.entity.shop.Picture;
import com.youhe.entity.shop.Shop_index_carousel;

import java.util.List;


public interface ShopUserIndexMapper {


    //查找用户首页轮播图信息
    List<Shop_index_carousel> findCarouselList(Shop_index_carousel shop_index_carousel);

    void updateCarouse(Shop_index_carousel shop_index_carousel);
}
