package com.youhe.entity.shop;

import com.youhe.entity.SysBaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xiaoqiang on 2018/12/27.
 */
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

    //排序
    private Integer orderNum;

    private String previewId;
    private Integer type;  //1.详情图，2缩略图


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPirce() {
        return pirce;
    }

    public void setPirce(Integer pirce) {
        this.pirce = pirce;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getDetail_picture() {
        return detail_picture;
    }

    public void setDetail_picture(String detail_picture) {
        this.detail_picture = detail_picture;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getRegister() {
        return register;
    }

    public void setRegister(Date register) {
        this.register = register;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getPreviewId() {
        return previewId;
    }

    public void setPreviewId(String previewId) {
        this.previewId = previewId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
