package com.youhe.controller.comm;

import com.youhe.utils.R;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(value = "upload")
    public R upload() {
        // todo 通用文件上传接口
        LOGGER.info("调用上传文件接口...");
        return R.ok();
    }
}
