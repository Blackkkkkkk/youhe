package com.youhe.biz.shop;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youhe.entity.shop.Picture;
import com.youhe.entity.shop.Shop;
import com.youhe.mapper.shop.PictureMapper;
import com.youhe.mapper.shop.ShopMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PictureBiz {
    private Logger log = LoggerFactory.getLogger(PictureBiz.class);

    @Autowired
    private PictureMapper pictureMapper;

    //查找图片
    public List<Picture> findPictureList(Picture picture) {
        return pictureMapper.findPictureList(picture);
    }


    public int save(Picture picture) {
        return pictureMapper.save(picture);
    }

    public int del(Picture picture) {
        return pictureMapper.del(picture);
    }

    public int update(Picture picture) {
        return pictureMapper.update(picture);
    }
}
