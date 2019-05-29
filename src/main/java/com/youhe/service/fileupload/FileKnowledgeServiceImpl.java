package com.youhe.service.fileupload;


import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.youhe.entity.fileupload.FileKnowledge;
import com.youhe.mapper.fileupload.FileKnowledgeMapper;
import com.youhe.utils.R;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xdn
 * @since 2019-05-29
 */
@Service
public class FileKnowledgeServiceImpl extends ServiceImpl<FileKnowledgeMapper, FileKnowledge> implements FileKnowledgeService {



     @Override
     public void uploadFile(MultipartFile[] multipartFile, HttpServletRequest request){
         if (multipartFile == null || multipartFile.length < 1) {
         }
         String path = request.getSession().getServletContext().getRealPath("/uploads/");
         File files = new File(path);
         if (!files.exists() || !files.isDirectory()){
             files.mkdirs();
         }
         String uuid= UUID.randomUUID().toString().replace("-","");
         for (MultipartFile file : multipartFile) {
             try {
                 file.getSize();
                 String fileName = file.getOriginalFilename();
                 String suffix = fileName.substring(fileName.lastIndexOf('.'));
                 String ossFileName = uuid+suffix;
                 File serverFile = new File(path,ossFileName);
                 file.transferTo(serverFile);
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
     }
}
