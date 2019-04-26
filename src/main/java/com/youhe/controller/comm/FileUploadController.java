package com.youhe.controller.comm;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.youhe.activiti.engine.MyProcessEngine;
import com.youhe.common.Constant;
import com.youhe.common.FileTypeEnum;
import com.youhe.entity.file.UploadFile;
import com.youhe.exception.YuheOAException;
import com.youhe.utils.R;
import com.youhe.utils.spring.HttpServletContextKit;
import com.youhe.utils.upload.FileUtils;
import org.activiti.engine.task.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

    @Autowired
    private MyProcessEngine myProcessEngine;

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

    /**
     * 下载文件。若同时传了两个可选参数，默认使用attachmentId
     * @param filename 下载的文件名称：可选。不是流程的附件传它
     * @param attachmentId 下载的附件ID：可选。流程相关附件传它
     * @param fType 附件类型：filename时必要，否则无法找到文件的路径。请看FileTypeEnum类
     */
    @GetMapping(value = "download")
    public void download(String filename, String fType, String attachmentId) {
        if ((StrUtil.isNotBlank(filename) && StrUtil.isNotBlank(attachmentId)) || StrUtil.isNotBlank(attachmentId)) {
            // 默认使用attachmentId
            Attachment attachment = myProcessEngine.getAttachment(attachmentId);
            String realFilePath = Constant.FILE_UPLOAD_PREFIX + attachment.getUrl();
            File file = new File(realFilePath);
            ServletUtil.write(HttpServletContextKit.getHttpServletResponse(), file);
        } else if (StrUtil.isNotBlank(filename)) {
            FileTypeEnum fileTypeEnum = FileTypeEnum.getByType(fType);
            if (fileTypeEnum == null) {
                throw new YuheOAException("fType 参数异常");
            }
            String realFilePath = Constant.FILE_UPLOAD_PREFIX + fileTypeEnum.getPath() + "/" + filename;
            File file = new File(realFilePath);
            ServletUtil.write(HttpServletContextKit.getHttpServletResponse(), file);
        }
    }
}
