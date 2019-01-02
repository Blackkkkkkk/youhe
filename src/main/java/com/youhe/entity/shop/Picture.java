package com.youhe.entity.shop;

import com.youhe.entity.SysBaseEntity;

import java.util.Date;

/**
 * Created by xiaoqiang on 2018/12/29.
 */
public class Picture extends SysBaseEntity {

    private int id;
    private Date registerDate; //注册时间

    private int type;  //1.详情图，2缩略图
    private String fileName;// 文件名称
    private String fileType;//文件类型
    private String saveFileName;//保存到服务器目录的文件名称
    private String reportaddr;// 保存到服务器目录的文件全路径

    private String previewId;//照片的独立id


    public String getPreviewId() {
        return previewId;
    }

    public void setPreviewId(String previewId) {
        this.previewId = previewId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

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
}
