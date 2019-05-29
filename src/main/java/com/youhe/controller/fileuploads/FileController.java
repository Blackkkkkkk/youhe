package com.youhe.controller.fileuploads;

import cn.hutool.core.lang.UUID;
import com.jcraft.jsch.*;
import com.youhe.service.fileupload.FileKnowledgeService;
import com.youhe.utils.R;
import com.youhe.utils.fileupload.FtpsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * 文档中心
 * 上传文件
 *
 * @ClassName FileController
 * @Description TODO
 * @Author xdn
 * @Date 2019/5/2213:43
 * @Version 1.0
 */

@RestController
@RequestMapping(value = "docment")
public class FileController {
    private static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileKnowledgeService fileKnowledgeService;

    @GetMapping(value = "/knowledge")
    public ModelAndView DocmentKnowLedge() {
        return new ModelAndView("sys/docment/files");
    }

    /**
     * 上传至sftp
     * @param multipartFile
     * @return
     */
    @PostMapping(value = "/file")
    public R uploadFile(@RequestParam(name = "file") MultipartFile[] multipartFile) {

            Map<String, String> sftpDetails = new HashMap<String, String>();
            sftpDetails.put(FtpsUtils.SFTP_REQ_HOST, "192.168.2.81");
            sftpDetails.put(FtpsUtils.SFTP_REQ_USERNAME, "root");
            sftpDetails.put(FtpsUtils.SFTP_REQ_PASSWORD, "123456");
            sftpDetails.put(FtpsUtils.SFTP_REQ_PORT, "22");
            ChannelSftp chSftp = null;
            boolean flag=false;
            try {
                chSftp = FtpsUtils.getInstance().getChannel(sftpDetails, 3000000);
                for(int i=0;i<multipartFile.length;i++) {
                    String originalFileName = multipartFile[i].getOriginalFilename();
                    String suffix = originalFileName.substring(originalFileName.lastIndexOf('.'));
                    //2、使用UUID生成新文件名
                    String newFileName = UUID.randomUUID().toString().replace("-", "");
                    String savePath = FtpsUtils.DESTINATION_FOLDER + newFileName + suffix;
                    flag = FtpsUtils.getInstance().outAndCloseStream(chSftp, savePath, multipartFile[i]);

                    if (flag) {
                        chSftp.quit();
                        FtpsUtils.getInstance().closeChannel();
                        return R.ok();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return R.error();
            }

        return R.error();
    }



    /**
     * 上传至服务器本地
     * @param multipartFile
     * @param request
     * @return
     */
    @PostMapping("/uploadLocal")
    public  R uploadLocalFile(@RequestParam(name = "file")MultipartFile[] multipartFile, HttpServletRequest request){
        fileKnowledgeService.uploadFile(multipartFile,request);
        return  R.ok();
    }

    @PostMapping("/uploaList")
    public  R uploadLocalList(){

        return  R.ok();
    }
}

