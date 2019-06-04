package com.youhe.mapper.fileupload;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youhe.dto.fileupload.FileKnowledgeDTO;
import com.youhe.dto.fileupload.FileRuleDTO;
import com.youhe.entity.fileupload.FileKnowledge;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youhe.entity.fileupload.FileRule;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xdn
 * @since 2019-05-29
 */
public interface FileKnowledgeMapper extends BaseMapper<FileKnowledge> {


    /**
     * 插入数据到知识管理表
     * @param fileInfos
     * @return
     */
    void insertFileInfomation(List<FileKnowledge> fileInfos);


    /**
     * 查询知识管理已存在的分类名称
     * @return
     */
    List<FileKnowledgeDTO> queryFileCategoryName();

    /**
     * 查询知识管理的内容
     * @return
     */
    List<FileKnowledgeDTO> queryFileList(IPage page);


    /**
     * 删除文件
     * @param id
     * @return
     */
    int deleteFilesOne(Long id);

    /**
     * 查询服务器文件名称和路径
     * @param id
     * @return
     */
    FileKnowledge  queryFileAddr(Long id);

    /**
     * 规章制度下载需要的内容
     * @param id
     * @return
     */
    FileRule  queryDownloadText(Long id);

    /**
     * 查询规章制度内容
     * @return
     */
    List<FileRuleDTO> queryRuleText(IPage page);

    /**
     * 插入到规章制度表
     * @param fileInfos
     */
    void insertRuleFile( List<FileRule> fileInfos);

    /**
     * 查询规章制度所在服务器路径
     * @param id
     * @return
     */
    FileRule queryRuleFileAddr(Long id);

    /**
     * 删除规章制度文件
     * @param id
     * @return
     */
    int deleteRuleFile(Long id);

    /**
     * 查规章制度已存在的分类名称
     * @return
     */
    List<FileRuleDTO> queryRuleFileCategoryName();

    /**
     * 知识管理下载需要的内容
     * @param id
     * @return
     */
    FileKnowledge  queryKnowledgeDownloadText(Long id);
}
