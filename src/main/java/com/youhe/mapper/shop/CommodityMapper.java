package com.youhe.mapper.shop;

import com.youhe.entity.shop.Commodity;

import java.util.List;

public interface CommodityMapper {


    //查找角色表
    List<Commodity> findCommodityList(Commodity commodity);

    //新增角色表
    void add(Commodity commodity);

    //修改角色表
    void update(Commodity commodity);

    //删除角色
    void del(Commodity commodity);
}
