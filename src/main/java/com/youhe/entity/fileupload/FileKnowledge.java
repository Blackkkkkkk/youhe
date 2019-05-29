package com.youhe.entity.fileupload;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xdn
 * @since 2019-05-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FileKnowledge implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    private LocalDateTime registerDate;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件内容类别
     */
    private String fileCategory;

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
    private String fileSize;

    /**
     * 备用
     */
    private String versionNumber;


}
