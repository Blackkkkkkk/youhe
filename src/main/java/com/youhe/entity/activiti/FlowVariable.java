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

    private String userId;  // 用户ID
    private String nextUserId;  // 下一审批人
    private String processInstanceId;   // 流程实例ID
    private String taskId;  // 任务ID
    private String mainFormKey; // 主表单key
    private String formKey; // 任务表单key
    private String firstNodeKey;  // 首节点key
    private String comment; // 流转意见

}
