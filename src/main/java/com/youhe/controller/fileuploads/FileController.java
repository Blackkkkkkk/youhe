package com.youhe.controller.fileuploads;

import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jcraft.jsch.*;
import com.youhe.dto.fileupload.FileKnowledgeDTO;
import com.youhe.dto.fileupload.FileRuleDTO;
import com.youhe.entity.fileupload.FileKnowledge;
import com.youhe.entity.fileupload.FileRule;
import com.youhe.service.fileupload.FileKnowledgeService;
import com.youhe.utils.R;
import com.youhe.utils.fileupload.FtpsUtils;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 文档中心
 *
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

    /**
     * 知识管理页面
     * @return
     */
    @GetMapping(value = "/knowledge")
    public ModelAndView DocmentKnowLedge() {
        return new ModelAndView("sys/docment/files");
    }

    /**
     * 规章制度页面
     * @return
     */
    @GetMapping(value = "/rule")
    public ModelAndView rule() {
        return new ModelAndView("sys/docment/rule");
    }
    /**
     * 下载中心页面
     * @return
     */
    @GetMapping(value = "/download")
    public ModelAndView download() {
        return new ModelAndView("sys/docment/download");
    }
    /**
     * 上传至sftp
     *
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
        boolean flag = false;
        try {
            chSftp = FtpsUtils.getInstance().getChannel(sftpDetails, 3000000);
            for (int i = 0; i < multipartFile.length; i++) {
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
     * 知识管理上传至服务器本地
     *
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/uploadLocal")
    public R uploadLocalFile(@RequestParam(name = "file") MultipartFile[] file,
                             FileKnowledge fileKnowledge,
                             HttpServletRequest request) {
        if (StringUtil.isBlank(fileKnowledge.getFileCategoryName())) {
            return R.error(503, "分类名称不能为空");
        }
        try {
            fileKnowledgeService.uploadFile(file, request, fileKnowledge);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }

        return R.ok();
    }

    /**
     * 显示知识管理已存在的分类名称
     *
     * @return
     */
    @PostMapping("/categoryList")
    @ResponseBody
    public List<FileKnowledgeDTO> uploadLocalList() {
        List<FileKnowledgeDTO> fileKnowledgeDtos = fileKnowledgeService.selectFileCategoryName();
        return fileKnowledgeDtos;
    }

    /**
     * 显示知识管理的内容
     *
     * @param current
     * @param size
     * @return
     */
    @PostMapping("/fileList")
    @ResponseBody
    public R displayFile(int current, int size) {
        IPage<FileKnowledgeDTO> fileKnowledgeIPage = fileKnowledgeService.queryFiles(current, size);
        return R.ok().put("data", fileKnowledgeIPage.getRecords()).put("total", fileKnowledgeIPage.getTotal());
    }

    /**
     * 删除知识管理文件
     * @param id
     * @return
     */
    @GetMapping("/deleteOne")
    public R deleteFile(Long id) {
        try {

            FileKnowledge fileKnowledge = fileKnowledgeService.queryServerAddr(id);
            String fName = fileKnowledge.getServerAddr() + fileKnowledge.getSaveFileName();
            File file = new File(fName);
                  if(file.exists()){
                      file.delete();
                  }
            fileKnowledgeService.deleteFileOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }

        return R.ok();
    }

    /**
     * 下载文件
     * @param id
     * @param response
     * @param request
     */
    @GetMapping(value = "/downloadFile")
    public void downloadFile(Long id,HttpServletResponse response,HttpServletRequest request) {
        try {
            fileKnowledgeService.queryDownloadName(id,response,request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询规章制度内容
     * @param current
     * @param size
     * @return
     */
    @GetMapping(value = "/ruleList")
    public R ruleFile(int current,int size) {
        IPage<FileRuleDTO> fileRuleIPage = fileKnowledgeService.queryRuleList(current,size);
        return R.ok().put("data", fileRuleIPage.getRecords()).put("total", fileRuleIPage.getTotal());

    }
    /**
     * 规章制度上传至服务器本地
     *
     * @param file
     * @param request
     * @return
     */
    @PostMapping("/uploadRule")
    public R uploadRuleFile(@RequestParam(name = "file") MultipartFile file,
                            FileRule fileRule,
                             HttpServletRequest request) {
        if (StringUtil.isBlank(fileRule.getFileCategoryName())) {
            return R.error(503, "分类名称不能为空");
        }
        try {
            fileKnowledgeService.uploadRuleFile(file, request, fileRule);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }

        return R.ok();
    }

    /**
     * 删除规章制度文件
     * @param id
     * @return
     */
    @GetMapping("/deleteRuleOne")
    public R deleteRuleFile(Long id) {
        try {

            FileRule fileRule = fileKnowledgeService.queryRuleAddr(id);
            String fName = fileRule.getServerAddr() + fileRule.getSaveFileName();
            File file = new File(fName);
            if(file.exists()){
                file.delete();
            }
            fileKnowledgeService.deleteRuleFileOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }

        return R.ok();
    }

    /**
     * 显示知识管理已存在的分类名称
     *
     * @return
     */
    @PostMapping("/categoryRuleList")
    @ResponseBody
    public List<FileRuleDTO> uploadRuleList() {
        List<FileRuleDTO> fileRuleDtos = fileKnowledgeService.selectRuleFileCategoryName();
        return fileRuleDtos;
    }
}

