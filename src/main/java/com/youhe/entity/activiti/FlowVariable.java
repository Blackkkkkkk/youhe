package com.youhe.entity.activiti;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 工作流变量
 */
@Getter
@Setter
@ToString
public class FlowVariable implements Serializable {

    private static final long serialVersionUID = 1L;

    private String processName; // 流程名称
    private String processDefinitionId; // 流程定义ID
    private String userId;  // 用户ID
    private String nextUserId;  // 下一审批人
    private String processInstanceId;   // 流程实例ID
    private String taskId;  // 任务ID
    private String executionId;  // 任务执行ID；在并发情况下是唯一的
    private String mainFormKey; // 主表单key
    private String formKey; // 任务表单key
    private String firstNodeKey;  // 首节点key
    private String currentNodeKey;  // 当前节点key
    private String currentNodeName; // 当前节点名称
    private boolean firstNode;  // 是否首节点
    private String targetTaskDefKey; // 目标任务定义ID
    private boolean firstSubmit;    // 是否第一次已提交，用于判断流程首环节是否回退过
    private String comment; // 流转意见

}
