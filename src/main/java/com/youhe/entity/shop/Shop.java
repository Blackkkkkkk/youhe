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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPageaddr() {
        return pageaddr;
    }

    public void setPageaddr(String pageaddr) {
        this.pageaddr = pageaddr;
    }

    public String getPictureSize() {
        return pictureSize;
    }

    public void setPictureSize(String pictureSize) {
        this.pictureSize = pictureSize;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
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
