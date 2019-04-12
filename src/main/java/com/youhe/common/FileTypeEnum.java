package com.youhe.common;

/**
 * 作用：上传文件分类<br>
 * 说明：(无)
 *
 * @author Kalvin
 * @Date 2019年04月12日 16:32
 */
public enum FileTypeEnum {

    /**
     * 需要增加一个类型直接在下面添加
     */
    FLOW_ATTACHMENT("F_ATT", "flow_attachment"),
    SHOP_IMG("S_IMG", "shop_img");

    /**
     * 文件分类
     */
    private String type;
    /**
     * 分类目录
     */
    private String path;

    FileTypeEnum(String type, String path) {
        this.type = type;
        this.path = path;
    }

    public static FileTypeEnum getByType(String type) {
        for (FileTypeEnum fileTypeEnum : FileTypeEnum.values()) {
            if (fileTypeEnum.type.equals(type)) {
                return fileTypeEnum;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public String getPath() {
        return path;
    }
}
