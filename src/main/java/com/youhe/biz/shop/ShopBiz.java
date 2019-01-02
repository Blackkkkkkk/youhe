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
    public List<Shop> findRoleList(Shop shop) {
        return shopMapper.findShopList(shop);
    }


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

}
