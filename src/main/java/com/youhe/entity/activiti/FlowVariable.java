package com.youhe.entity.activiti;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.activiti.engine.impl.pvm.process.ActivityImpl;

import java.io.Serializable;
import java.util.List;

/**
 * 工作流变量
 */
@Getter
@Setter
@ToString
public class FlowVariable implements Serializable {

    private String processName; // 流程名称
    private String processDefinitionId; // 流程定义ID
    private String userId;  // 当前用户ID
    private String nextUserId;  // 下一审批人
    private String ccUserId;  // 抄送人
    private boolean agency; // 是否是代理申请
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
    private int nextNodeNum;   // 当前节点所有可能的出口路由节点个数
    private List<Node> nextNodes;   // 当前节点所有可能的出口路由节点列表
    private List<ActivityImpl> canBackNodes; // 当前节点所有可驳回的节点列表
    private String comment; // 流转意见

}
