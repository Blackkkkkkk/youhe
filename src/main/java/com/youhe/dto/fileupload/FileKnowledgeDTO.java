package com.youhe.dto.fileupload;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 *@ClassName FileKnowledgeDto
 *@Description 知识管理所需字段
 *@Author xdn
 *@Date 2019/5/2914:32
 *@Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FileKnowledgeDTO implements Serializable {


    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     *
     */
    private Date uploadDate;
    /**
     *上传人
     */
    private String uploadPeople;
    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件内容类别名称
     */
    private String fileCategoryName;



}
