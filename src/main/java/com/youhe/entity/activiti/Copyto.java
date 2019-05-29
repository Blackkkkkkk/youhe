package com.youhe.entity.activiti;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 任务抄送表
 * </p>
 *
 * @author Kalvin
 * @since 2019-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("act_ext_copyto")
public class Copyto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 抄送人
     */
    private String cc;

    /**
     * 办理人
     */
    private String assignee;

    /**
     * 流程实例ID
     */
    private String procInstId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 流程名称
     */
    private String procName;

    /**
     * 查阅时间
     */
    private Date readTime;

    /**
     * 耗时，单位：毫秒
     */
    private Long duration;

    /**
     * 创建时间
     */
    private Date createTime;

}
