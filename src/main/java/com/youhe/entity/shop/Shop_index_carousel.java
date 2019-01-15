package com.youhe.entity.shop;

import com.youhe.entity.SysBaseEntity;

/**
 * Created by xiaoqiang on 2019/1/10.
 */
public class Shop_index_carousel extends SysBaseEntity {

    private int id;

    private int type;  // 区分第几张轮播图

    private String text_1;//文字说明1
    private String text_2;//文字说明2
    private String text_3;//文字说明3
    private String text_4;//文字说明4
    private String text_5;//文字说明5
    private String text_6;//文字说明6


    //辅助查询字段
    private String reportaddr;// 保存到服务器目录的文件全路径
    private String pictureSize; //照片大小
    private String pageaddr;//页面显示路径
    private String picType;//照片类型
    private String saveFileName; // 照片名字


    public String getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }

    public String getReportaddr() {
        return reportaddr;
    }

    public void setReportaddr(String reportaddr) {
        this.reportaddr = reportaddr;
    }

    public String getPictureSize() {
        return pictureSize;
    }

    public void setPictureSize(String pictureSize) {
        this.pictureSize = pictureSize;
    }

    public String getPageaddr() {
        return pageaddr;
    }

    public void setPageaddr(String pageaddr) {
        this.pageaddr = pageaddr;
    }

    public String getPicType() {
        return picType;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getText_1() {
        return text_1;
    }

    public void setText_1(String text_1) {
        this.text_1 = text_1;
    }

    public String getText_2() {
        return text_2;
    }

    public void setText_2(String text_2) {
        this.text_2 = text_2;
    }

    public String getText_3() {
        return text_3;
    }

    public void setText_3(String text_3) {
        this.text_3 = text_3;
    }

    public String getText_4() {
        return text_4;
    }

    public void setText_4(String text_4) {
        this.text_4 = text_4;
    }

    public String getText_5() {
        return text_5;
    }

    public void setText_5(String text_5) {
        this.text_5 = text_5;
    }

    public String getText_6() {
        return text_6;
    }

    public void setText_6(String text_6) {
        this.text_6 = text_6;
    }
}
