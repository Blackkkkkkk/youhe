package com.youhe.controller.comm;

import com.youhe.common.FileTypeEnum;
import com.youhe.entity.file.UploadFile;
import com.youhe.utils.R;
import com.youhe.utils.upload.FileUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 作用：文件上传 控制层<br>
 * 说明：(无)
 *
 * @author Kalvin
 * @Date 2019年04月10日 16:57
 */
@RestController
@RequestMapping(value = "file")
public class FileUploadController extends BaseController {


    /**
     * 通用文件上传接口
     * @param file MultipartFile
     * @param fType 文件分类（必须与FileTypeEnum里面的type值相同）
     * @return r
     */
    @PostMapping(value = "upload")
    public R upload(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "fType") String fType) {
        LOGGER.info("调用上传文件接口上传文件类型：{}", fType);
        UploadFile upload = FileUtils.upload(file, FileTypeEnum.getByType(fType));
        return R.ok().put("upload", upload);
    }
}
