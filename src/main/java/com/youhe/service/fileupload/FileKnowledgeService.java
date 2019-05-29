package com.youhe.service.fileupload;


import com.baomidou.mybatisplus.extension.service.IService;
import com.youhe.entity.fileupload.FileKnowledge;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xdn
 * @since 2019-05-29
 */
public interface FileKnowledgeService extends IService<FileKnowledge> {



    void uploadFile(MultipartFile[] multipartFile,HttpServletRequest request);
}
