package com.youhe.activiti.engine;

import com.youhe.entity.activitiData.MyCommentEntity;
import com.youhe.entity.activitiData.ProdefTask;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Task;

import java.io.InputStream;
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
    List<ProdefTask> getTaskList(String userId);

    /**
     * 获取已办任务列表
     * @param userId 用户ID
     * @return List<HistoricTaskInstance>
     */
    List<ProdefTask> getHisTaskList(String userId);

    /**
     * 获取流程表单
     * @param taskId 任务ID
     * @return map
     */
    Map<String, Object> getTaskFormData(String taskId);


    /**
     * 获取表单用户填写的数据
     * @param taskId
     * @return
     */
    Map<String, Object> getTaskFormValue(String taskId);
    /**
     * 获取流程xml数据
     * @param modelId 模型ID
     * @return str
     */
    String getProcessXmlData(String modelId);

    /**
     * 获取流程启动者
     * @param processInstanceId
     * @return
     */
    String getStartUserId(String processInstanceId);

    /**
     * 获取流程首环节activitiId
     * @param processInstanceId
     * @return
     */
    String getStartActivityId(String processInstanceId);

    /**
     * 获取历史任务实例
     * @param processInstanceId 流程实例ID
     * @param activityId 活动节点id/节点定义ID
     * @return 历史任务审批人
     */
    HistoricActivityInstance getHisActivityInstance(String processInstanceId, String activityId);

    /**
     * 获取历史任务审批人
     * @param processInstanceId 流程实例ID
     * @param activityId 历史活动节点id/节点定义ID
     * @return 历史任务审批人（上一环节审批）
     */
    String getHisAssignee(String processInstanceId, String activityId);

    /**
     * 获取上一环节审批人
     * @param task 当前处理任务
     * @return 上一环节审批人
     */
    String getPreAssignee(Task task);

    /**
     * 获取上一环节活动实例ID
     * @param task
     * @return
     */
    String getPreActivityId(Task task);

    /**
     * 跳转任务节点
     * @param targetTaskDefKey 目标节点
     * @param map 业务数据
     * @param assignee 任务接收人
     * @param comment 审批意见
     * @param type 类型：0-回退 1-前进
     */
    void gotoAnyTask(String targetTaskDefKey, Map<String, Object> map, String assignee, String comment, Integer type);

    /**
     * 回退首环节
     * @param taskId 当前任务ID
     */
    void gotoFirstTask(String taskId);

    /**
     * 驳回上环节
     * @param taskId 当前任务ID
     */
    void gotoPreTask(String taskId);

    /**
     *查看流转意见
     * @param processInstanceId 流程实例id
     * @return
     */
     List<MyCommentEntity> findAdvice(String processInstanceId);

    /**
     * 获取下一步用户任务节点集
     * @param procDefId  流程定义ID
     * @param taskDefKey 当前任务KEY
     * @param map        业务数据
     * @return list<UserTask>
     */
    List<UserTask> getNextNode(String procDefId, String taskDefKey, Map<String, Object> map);

    /**
     * 根据任务ID获取任务实例
     * @param taskId 任务ID
     * @return
     */
    TaskEntity getTaskById(String taskId);

    /**
     * 根据任务ID获取流程定义
     * @param taskId 任务ID
     * @return
     */
    ProcessDefinitionEntity getProcessDefinitionByTaskId(String taskId);

    /**
     * 根据任务ID获取对应的流程实例
     * @param taskId 任务ID
     * @return
     */
    ProcessInstance getProcessInstanceByTaskId(String taskId);

    /**
     * 根据任务ID获取当前活动节点
     * @param taskId 任务ID
     * @return
     */
    ActivityImpl getActivityImpl(String taskId);

    /**
     * 根据任务ID获取活动节点
     * @param taskId 任务ID
     * @param activityId 活动节点ID 为null当前当前节点
     * @return
     */
    ActivityImpl getActivityImpl(String taskId, String activityId);

    /**
     * 获取可驳回的节点列表
     * @param taskId 任务ID
     * @param currActivity 当前节点
     * @param rtnList  存储回退节点集合
     * @param tempList 临时存储节点集合（存储一次迭代过程中的同级userTask节点）
     * @return 可驳回的节点列表
     */
    List<ActivityImpl> getCanBackActivity(String taskId, ActivityImpl currActivity, List<ActivityImpl> rtnList, List<ActivityImpl> tempList);

    /**
     * 创建attachment
     * @param attachmentType - 类型
     * @param taskId 任务ID
     * @param processInstanceId 流程实例ID
     * @param attachmentName    附件名称
     * @param attachmentDescription 附件描述
     * @param url 文件上传成功后返回的可下载的url地址
     */
    Attachment createAttachment(String userId, String attachmentType, String taskId, String processInstanceId, String attachmentName, String attachmentDescription, String url);

    /**
     * 根据附件id返回附件文件流信息
     * @param attachmentId 附件ID
     */
    InputStream getAttachmentContent(String attachmentId);

    /**
     * 根据attachmenId获取附件对象
     * @param attachmentId 附件ID
     */
    Attachment getAttachment(String attachmentId);

    /**
     * 查询某个task所关联的附件集合
     * @param taskId 任务ID
     * @return
     */
    List<Attachment> getTaskAttachments(String taskId);

    /**
     * 查询某个实例所关联的附件集合（多个task组合成一个实例）
     * @param processInstanceId 流程实例ID
     * @return
     */
    List<Attachment> getInstanceAttachments(String processInstanceId);

    /**
     * 根据附件ID删除附件信息
     * @param attachmentId 附件ID
     */
    void deleteAttachment(String attachmentId);
}
