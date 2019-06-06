package com.youhe.biz.shop;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youhe.entity.shop.Collect;
import com.youhe.entity.shop.Shop;
import com.youhe.mapper.shop.CollectMapper;
import com.youhe.mapper.shop.ShopMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CollectBiz {
    private Logger log = LoggerFactory.getLogger(CollectBiz.class);

    @Autowired
    private CollectMapper collectMapper;

    //查找商品表
    public List<Collect> find(Collect collect) {
        return collectMapper.find(collect);
    }


    public void save(Collect collect) {
        collectMapper.save(collect);
    }

    public void del(Collect collect) {
        collectMapper.del(collect);
    }


}
