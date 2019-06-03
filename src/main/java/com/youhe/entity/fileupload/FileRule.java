package com.youhe.entity.fileupload;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 *@ClassName FileRule
 *@Description TODO
 *@Author xdn
 *@Date 2019/6/213:54
 *@Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FileRule implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;
    /**
     * 上传人
     */
    private String uploadPeople;
    /**
     *上传时间
     */
    private Date uploadDate;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件内容类别编号
     */
    private String fileCategoryNumber;
    /**
     * 文件内容类别名称
     */
    private String fileCategoryName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 保存到服务器目录的文件名称
     */
    private String saveFileName;

    /**
     * 保存到服务器目录的文件全路径
     */
    private String serverAddr;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 备用
     */
    private String versionNumber;
}
