package com.youhe.controller.fastdfs;

import cn.hutool.core.lang.UUID;
import com.jcraft.jsch.*;
import com.youhe.entity.fastdfs.FastDFSFile;
import com.youhe.utils.R;
import com.youhe.utils.fastdfs.FastDFSClient;
import com.youhe.utils.fastdfs.FtpsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
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

    @GetMapping(value = "/knowledge")
    public ModelAndView DocmentKnowLedge() {
        return new ModelAndView("/sys/docment/file");
    }

    @PostMapping(value = "/file")
    public R uploadFile(@RequestParam(name = "file") MultipartFile multipartFile) {

            Map<String, String> sftpDetails = new HashMap<String, String>();
            sftpDetails.put(FtpsUtils.SFTP_REQ_HOST, "192.168.2.81");
            sftpDetails.put(FtpsUtils.SFTP_REQ_USERNAME, "root");
            sftpDetails.put(FtpsUtils.SFTP_REQ_PASSWORD, "123456");
            sftpDetails.put(FtpsUtils.SFTP_REQ_PORT, "22");
            ChannelSftp chSftp = null;
            boolean flag=false;
            try {
                chSftp = FtpsUtils.getInstance().getChannel(sftpDetails, 3000000);
                    String originalFileName = multipartFile.getOriginalFilename();
                    String suffix = originalFileName.substring(originalFileName.lastIndexOf('.'));
                    //2、使用UUID生成新文件名
                    String newFileName = UUID.randomUUID().toString().replace("-", "");
                    String savePath = FtpsUtils.DESTINATION_FOLDER + newFileName + suffix;
                    flag = FtpsUtils.getInstance().outAndCloseStream(chSftp, savePath, multipartFile);

                if(flag){
                    chSftp.quit();
                    FtpsUtils.getInstance().closeChannel();
                    return R.ok();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return R.error();
            }

        return R.error();
    }


    /**
     * 上传至fastdfs
     *
     * @param file
     * @param redirectAttributes
     * @return
     */

    @PostMapping("/upload") //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }
        try {
            // Get the file and save it somewhere
            String path = saveFile(file);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
            System.out.printf(file.getOriginalFilename());
            redirectAttributes.addFlashAttribute("path",
                    "file path url '" + path + "'");
        } catch (Exception e) {
            logger.error("upload file failed", e);
        }
        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

    /**
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public String saveFile(MultipartFile multipartFile) throws IOException {
        String[] fileAbsolutePath = {};
        String fileName = multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        byte[] file_buff = null;
        InputStream inputStream = multipartFile.getInputStream();
        if (inputStream != null) {
            int len1 = inputStream.available();
            file_buff = new byte[len1];
            inputStream.read(file_buff);
        }
        inputStream.close();
        FastDFSFile file = new FastDFSFile(fileName, file_buff, ext);
        try {
            fileAbsolutePath = FastDFSClient.upload(file);  //upload to fastdfs
        } catch (Exception e) {
            logger.error("upload file Exception!", e);
        }
        if (fileAbsolutePath == null) {
            logger.error("upload file failed,please upload again!");
        }
        String path = FastDFSClient.getTrackerUrl() + fileAbsolutePath[0] + "/" + fileAbsolutePath[1];
        return path;
    }
}

