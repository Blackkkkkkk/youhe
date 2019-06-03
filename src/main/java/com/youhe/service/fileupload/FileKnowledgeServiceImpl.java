package com.youhe.service.fileupload;


import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.youhe.dto.fileupload.FileKnowledgeDTO;
import com.youhe.dto.fileupload.FileRuleDTO;
import com.youhe.entity.fileupload.FileKnowledge;
import com.youhe.entity.fileupload.FileRule;
import com.youhe.entity.user.User;
import com.youhe.mapper.fileupload.FileKnowledgeMapper;
import com.youhe.mapper.user.UserMapper;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xdn
 * @since 2019-05-29
 */
@Service
public class FileKnowledgeServiceImpl extends ServiceImpl<FileKnowledgeMapper, FileKnowledge> implements FileKnowledgeService {

    @Autowired
    private FileKnowledgeMapper fileKnowledgeMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void uploadFile(MultipartFile[] file, HttpServletRequest request, FileKnowledge fileKnowledge) throws Exception {
        List<FileKnowledge> fileInfos = new ArrayList<FileKnowledge>();
        List<FileKnowledge> fileKnowledges = null;
        FileKnowledge fk = null;
        Long userId = ShiroUserUtils.getUserId();
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        File fileMk = new File(path);
        if (!fileMk.exists() || !fileMk.isDirectory()) {
            fileMk.mkdirs();
        }
        //分类编号
        String categoryId = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < file.length; i++) {
            //文件编号
            String fileId = UUID.randomUUID().toString().replace("-", "");
            fk = new FileKnowledge();
            User user= userMapper.findName(String.valueOf(userId));
            //获取文件大小
            long size = file[i].getSize();
            //获取文件名称
            String fileName = file[i].getOriginalFilename();
            //获取文件后缀名
            String suffix = fileName.substring(fileName.lastIndexOf('.'));
            //拼接新文件名
            String serverFileName = fileId + suffix;
            File serverFile = new File(path, serverFileName);
            //上传到本地
            file[i].transferTo(serverFile);

            fk.setFileName(fileName);
            fk.setFileSize(size);
            fk.setUploadPeople(user.getUserName());
            fk.setFileType(suffix);
            fk.setServerAddr(path);
            fk.setFileCategoryName(fileKnowledge.getFileCategoryName());
            fk.setSaveFileName(serverFileName);
            fk.setFileCategoryNumber(categoryId);
            fk.setUploadDate(new Date());
            fileInfos.add(fk);
        }

        //插入数据到知识管理表
        fileKnowledgeMapper.insertFileInfomation(fileInfos);
    }


    @Override
    public List<FileKnowledgeDTO> selectFileCategoryName() {
        return fileKnowledgeMapper.queryFileCategoryName();
    }

    @Override
    public IPage<FileKnowledgeDTO> queryFiles(int current, int size) {
        Page<FileKnowledgeDTO> page = new Page<>(current, size);
        List<FileKnowledgeDTO> fileKnowledges = fileKnowledgeMapper.queryFileList(page);
        return page.setRecords(fileKnowledges);
    }

    @Override
    public int deleteFileOne(Long id) throws Exception {
        return fileKnowledgeMapper.deleteFilesOne(id);
    }

    @Override
    public FileKnowledge queryServerAddr(Long id) {
        return fileKnowledgeMapper.queryFileAddr(id);
    }

    @Override
    public void queryDownloadName(Long id, HttpServletResponse response, HttpServletRequest request) throws Exception {
        FileRule fileRule = fileKnowledgeMapper.queryDownloadText(id);
        String fileName = fileRule.getFileName();
        String fileServerName = fileRule.getSaveFileName();
        // 如果文件名不为空，则进行下载
        if (fileName != null) {
            //设置文件路径
            String realPath = fileRule.getServerAddr();
            File file = new File(realPath, fileServerName);

            // 如果文件名存在，则进行下载
            if (file.exists()) {
                // 下载文件能正常显示中文
                response.addHeader("Content-Disposition", "attachment;filename=" +new String(fileName.getBytes("UTF-8"), "ISO8859-1" ));

                // 实现文件下载
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }

                    System.out.println("Download the song successfully!");
                } catch (Exception e) {
                    System.out.println("Download the song failed!");
                }
            }
        }
    }

    @Override
    public IPage<FileRuleDTO> queryRuleList(int current, int size) {
        Page<FileRuleDTO> page = new Page<>(current, size);
        List<FileRuleDTO> fileRuleDtos = fileKnowledgeMapper.queryRuleText(page);
        return  page.setRecords(fileRuleDtos);
    }

    @Override
    public void uploadRuleFile(MultipartFile file, HttpServletRequest request, FileRule fileRule) throws Exception {
        List<FileRule> fileInfos = new ArrayList<FileRule>();
        List<FileRule> fileKnowledges = null;
        FileRule fk = null;
        Long userId = ShiroUserUtils.getUserId();
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        File fileMk = new File(path);
        if (!fileMk.exists() || !fileMk.isDirectory()) {
            fileMk.mkdirs();
        }
        //分类编号
        String categoryId = UUID.randomUUID().toString().replace("-", "");

            User user= userMapper.findName(String.valueOf(userId));
            //文件编号
            String fileId = UUID.randomUUID().toString().replace("-", "");
            fk = new FileRule();
            //获取文件大小
            long size = file.getSize();
            //获取文件名称
            String fileName = file.getOriginalFilename();
            //获取文件后缀名
            String suffix = fileName.substring(fileName.lastIndexOf('.'));
            //拼接新文件名
            String serverFileName = fileId + suffix;
            File serverFile = new File(path, serverFileName);
            //上传到本地
            file.transferTo(serverFile);

            fk.setFileName(fileName);
            fk.setUploadPeople(user.getUserName());
            fk.setFileSize(size);
            fk.setFileType(suffix);
            fk.setServerAddr(path);
            fk.setFileCategoryName(fileRule.getFileCategoryName());
            fk.setSaveFileName(serverFileName);
            fk.setFileCategoryNumber(categoryId);
            fk.setUploadDate(new Date());
            fileInfos.add(fk);


        //插入数据到规章制度表
        fileKnowledgeMapper.insertRuleFile(fileInfos);
    }

    @Override
    public FileRule queryRuleAddr(Long id) {
        return fileKnowledgeMapper.queryRuleFileAddr(id);
    }

    @Override
    public int deleteRuleFileOne(Long id) throws Exception {
        return fileKnowledgeMapper.deleteRuleFile(id);
    }

    @Override
    public List<FileRuleDTO> selectRuleFileCategoryName() {
        return fileKnowledgeMapper.queryRuleFileCategoryName();
    }


}