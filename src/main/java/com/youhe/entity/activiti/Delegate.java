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
 * 委托表
 * </p>
 *
 * @author Kalvin
 * @since 2019-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("act_ru_delegate")
public class Delegate implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    /**
     * 办理人
     */
    private String assignee;

    /**
     * 代理人
     */
    private String attorney;

    /**
     * 类型，0：申请代理，1：委托代理
     */
    private Integer type;

    /**
     * 流程定义ID
     */
    private String processDefinitionId;

    /**
     * 流程名称
     */
    private String processName;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;


}
