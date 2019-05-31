package com.youhe.biz.shop;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youhe.entity.shop.Shop;
import com.youhe.mapper.shop.ShopMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ShopBiz {
    private Logger log = LoggerFactory.getLogger(ShopBiz.class);

    @Autowired
    private ShopMapper shopMapper;

    //查找商品表
    public List<Shop> findShopList(Shop shop) {
        return shopMapper.findShopList(shop);
    }

    //查找商品详情表
    public Shop findShopListView(Shop shop) {
        return shopMapper.findShopListView(shop);
    }


    //查找分类表
    public List<Shop> findCommodity(Shop shop) {
        return shopMapper.findCommodity(shop);
    }

    public PageInfo<Shop> findCommodityByPage(Shop shop) {
        PageHelper.startPage(shop.getPageNum(), shop.getPageSize(), true);
        List<Shop> list = findCommodity(shop);
        return new PageInfo<>(list);
    }

//    //查看详情
//    public List<Shop> findCommodityType(Shop shop) {
//        return shopMapper.findCommodity(shop);
//    }

    //查找商品表分页
    public PageInfo<Shop> list(Shop shop) {

        PageHelper.startPage(shop.getPageNum(), shop.getPageSize());

        List<Shop> list = shopMapper.findShopList(shop);

        PageInfo<Shop> pageInfo = new PageInfo<Shop>(list);


        return pageInfo;
    }


    public Long save(Shop shop) {
        return shopMapper.save(shop);
    }

    public Long del(Shop shop) {
        return shopMapper.del(shop);
    }

    public Long update(Shop shop) {
        return shopMapper.update(shop);
    }


    //查找商品详情页信息
    public List<Shop> findshopDetail(Shop shop) {
        return shopMapper.findshopDetail(shop);
    }


    //购物车信息
    public List<Shop> findCarList(Shop shop) {
        return shopMapper.findCarList(shop);
    }




}
