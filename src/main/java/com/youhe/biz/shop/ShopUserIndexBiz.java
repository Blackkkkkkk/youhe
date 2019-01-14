package com.youhe.biz.shop;


import com.youhe.entity.shop.Picture;
import com.youhe.entity.shop.Shop_index_carousel;
import com.youhe.mapper.shop.PictureMapper;
import com.youhe.mapper.shop.ShopUserIndexMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ShopUserIndexBiz {
    private Logger log = LoggerFactory.getLogger(ShopUserIndexBiz.class);

    @Autowired
    private ShopUserIndexMapper shopUserIndexMapper;

    //查找首页轮播图的信息
    public List<Shop_index_carousel> findPictureList(Shop_index_carousel shop_index_carousel) {
        return shopUserIndexMapper.findCarouselList(shop_index_carousel);
    }

    public void updateCarouse(Shop_index_carousel shop_index_carousel) {
        shopUserIndexMapper.updateCarouse(shop_index_carousel);
    }
}
