package com.youhe.mapper.shop;

import com.youhe.entity.shop.Picture;
import com.youhe.entity.shop.Shop;

import java.util.List;


public interface PictureMapper {


    //查找角色表
    List<Picture> findPictureList(Picture picture);

    int del(Picture picture);

    int save(Picture picture);

    int update(Picture picture);
}
