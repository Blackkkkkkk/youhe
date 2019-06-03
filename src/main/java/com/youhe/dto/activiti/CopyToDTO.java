package com.youhe.dto.activiti;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 *@ClassName CopytoDto
 *@Description 所需字段
 *@Date 2019/5/2718:23
 *@Version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CopyToDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 发送环节
     */
    private String sendNode;
    /**
     * 发送人
     */
    private String assignee;
    /**
     * 流程名称
     */
    private String procName;
    /**
     * 发送时间
     */
    private Date createTime;

    /**
     * 流程实例ID
     */
    private String procInstId;

    /**
     * 任务ID
     */
    private String taskId;
    /**
     * 查阅时间
     */
    private Date readTime;
}
