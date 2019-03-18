package com.youhe.activiti.engine;

import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * 我的流程引擎
 */
public interface MyProcessEngine {

    /**
     * 创建流程模型
     * @return modelId
     */
    String createModel();

    /**
     * 获取所有流程模型列表
     * @return List<Model>
     */
    List<Model> getModelList();

    /**
     * 发布/部署流程
     * @param modelId 模型ID
     * @return 流程定义ID
     */
    String deploy(String modelId);

    /**
     * 获取所有已发布的流程
     * @return List<Model>
     */
    List<Model> getDeployedProcesses();

    /**
     * 启动流程
     * @param processDefinitionId 流程定义ID
     * @return 流程实例
     */
    ProcessInstance start(String processDefinitionId);

    /**
     * 启动流程带业务ID
     * @param processDefinitionId 流程定义ID
     * @param businessId 业务ID
     * @return 流程实例
     */
    ProcessInstance start(String processDefinitionId, String businessId);

    /**
     * 提交任务
     * @param taskFlowData 流程任务流转数据
     */
    void submitTask(Map<String, Object> taskFlowData);

    /**
     * 获取待办任务列表
     * @param userId 用户ID
     * @return List<Task>
     */
    List<Task> getTaskList(String userId);

    /**
     * 获取已办任务列表
     * @param userId 用户ID
     * @return List<HistoricTaskInstance>
     */
    List<HistoricTaskInstance> getHisTaskList(String userId);

    /**
     * 获取流程表单
     * @param taskId 任务ID
     * @return map
     */
    Map getTaskForm(String taskId);

}
