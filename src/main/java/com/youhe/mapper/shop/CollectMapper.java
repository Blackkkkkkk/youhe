package com.youhe.mapper.shop;

import com.youhe.entity.shop.Collect;
import com.youhe.entity.shop.Shop;

import java.util.List;


public interface CollectMapper {


    List<Collect> find(Collect collect);

    void save(Collect collect);

    void del(Collect collect);


}
