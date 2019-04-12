package com.youhe.entity.file;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 作用：上传文件信息<br>
 * 说明：(无)
 *
 * @author Kalvin
 * @Date 2019年04月12日 17:21
 */
@Setter
@Getter
@ToString
public class UploadFile {

    private String name;    // 单纯文件名称
    private String filename;    // 文件名称带后缀
    private String filePath;    // 文件上传路径（相对）

    public UploadFile(String name, String filename, String filePath) {
        this.name = name;
        this.filename = filename;
        this.filePath = filePath;
    }
}
