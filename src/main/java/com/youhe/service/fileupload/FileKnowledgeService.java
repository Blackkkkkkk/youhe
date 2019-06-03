package com.youhe.service.fileupload;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import com.youhe.dto.fileupload.FileKnowledgeDTO;
import com.youhe.dto.fileupload.FileRuleDTO;
import com.youhe.entity.fileupload.FileKnowledge;
import com.youhe.entity.fileupload.FileRule;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xdn
 * @since 2019-05-29
 */
public interface FileKnowledgeService extends IService<FileKnowledge> {


    /**
     * 知识管理上传文件
     * @param file
     * @param request
     * @param fileCategory
     * @throws Exception
     */
    void uploadFile(MultipartFile[] file, HttpServletRequest request, FileKnowledge fileCategory) throws Exception;

    /**
     * 查询知识管理已存在的分类名称
     * @return
     */
    List<FileKnowledgeDTO> selectFileCategoryName();


    /**
     * 展示知识管理内容
     * @return
     */
    IPage<FileKnowledgeDTO> queryFiles(int current, int size);

    /**
     * 删除文件
     * @param id
     * @return
     */
     int deleteFileOne(Long id)throws Exception;

    /**
     * 查询出知识管理文件的服务器路径和所在服务器的文件名称
     * @param id
     * @return
     */
     FileKnowledge queryServerAddr(Long id) ;

    /**
     * 下载需要的内容
     * @param id
     * @return
     */
    void queryDownloadName(Long id, HttpServletResponse response, HttpServletRequest request) throws Exception;

    /**
     * 查询规章制度
     * @return
     */
    IPage<FileRuleDTO> queryRuleList(int current, int size);

    /**
     * 规章制度上传文件
     * @param file
     * @param request
     * @param fileRule
     * @throws Exception
     */
    void uploadRuleFile(MultipartFile file, HttpServletRequest request, FileRule fileRule) throws Exception;

    /**
     * 查询出规章制度文件的服务器路径和所在服务器的文件名称
     * @param id
     * @return
     */
    FileRule queryRuleAddr(Long id) ;

    /**
     * 删除规章制度文件
     * @param id
     * @return
     */
    int deleteRuleFileOne(Long id)throws Exception;

    /**
     * 查询规章制度已存在的分类名称
     * @return
     */
    List<FileRuleDTO> selectRuleFileCategoryName();

}
