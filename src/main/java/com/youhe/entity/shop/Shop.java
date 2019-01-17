package com.youhe.entity.shop;

import com.youhe.entity.SysBaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xiaoqiang on 2018/12/27.
 */
@Data
public class Shop extends SysBaseEntity {


    private Integer id;
    private String name;              //商品名称
    private Integer pirce;                //价格
    private Integer num;                 //商品数量
    private String detail_picture;   //详情图
    private String thumbnail;  //缩略图
    private Integer top;        //1 置顶 0非置顶
    private Integer status;    //1 上架 0下架
    private Date register; //注册时间

    /*
     * 关联照片表的字段
     **/
    private String saveFileName; // 照片名字

    private String pageaddr; // 页面显示路径

    //排序
    private Integer orderNum;

    private String previewId;
    private Integer type;  //1.详情图，2缩略图

    private String pictureSize; //照片大小


    private String dstFileName; // 对应前端上传控件id


    private int cartNum;  //查询购物车时的数量

}
