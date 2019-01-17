package com.youhe.biz.shop;


import com.youhe.entity.role.Role;
import com.youhe.entity.shop.Commodity;
import com.youhe.mapper.shop.CommodityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommodityBiz {
    private Logger log = LoggerFactory.getLogger(CommodityBiz.class);

    @Autowired
    private CommodityMapper commodityMapper;


    //查找用户表
    public List<Commodity> findCommodityList(Commodity commodity) {
        return commodityMapper.findCommodityList(commodity);
    }

    //新增角色表
    public void add(Commodity commodity) {
        commodityMapper.add(commodity);
    }

    //修改角色表
    public void update(Commodity commodity) {
        commodityMapper.update(commodity);
    }

    //删除角色
    public void del(Commodity commodity) {
        commodityMapper.del(commodity);
    }

}
