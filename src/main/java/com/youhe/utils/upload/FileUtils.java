package com.youhe.utils.upload;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileTypeUtil;
import com.youhe.common.Constant;
import com.youhe.common.FileTypeEnum;
import com.youhe.entity.file.UploadFile;
import com.youhe.exception.YuheOAException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 作用：文件工具类<br>
 * 说明：(无)
 *
 * @author Kalvin
 * @Date 2019年04月12日 16:44
 */
public class FileUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(FileTypeUtil.class);

    public static UploadFile upload(MultipartFile multipartFile, FileTypeEnum fileType) {
        try {
            if (fileType == null) {
                throw new YuheOAException("fileType不允许为空");
            }
            String filename = multipartFile.getOriginalFilename();
            assert filename != null;
            int index = filename.lastIndexOf(".");
            String suffix = "", fn = filename;
            String datePath = DateUtil.format(new Date(), "yyyyMMdd");
            String hhmmss = DateUtil.format(new Date(), "hhmmss");
            if (index != -1) {
                suffix = filename.substring(index);
                fn = filename.substring(0, index) + "_" + hhmmss;
            }
            String path = fileType.getPath();
            String filePath;
            if ("F_ATT".equals(fileType.getType())) {
                filePath = path + "/" + datePath + "/" + fn + suffix;
            } else {
                filePath = path + "/" + fn + suffix;
            }
            String url = Constant.FILE_UPLOAD_PREFIX + filePath;
            LOGGER.info("upload url={}", url);
            File lFile = new File(url);
            // 判断父目录是否存在，如果不存在，则创建
            if (lFile.getParentFile() != null && !lFile.getParentFile().exists()) {
                lFile.getParentFile().mkdirs();
            }
            lFile.createNewFile();
            multipartFile.transferTo(lFile);
            return new UploadFile(fn, fn + suffix, filePath);
        } catch (IOException e) {
            throw new YuheOAException("上传文件失败：" + e.getMessage());
        }
    }
}
