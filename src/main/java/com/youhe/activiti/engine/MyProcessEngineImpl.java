package com.youhe.activiti.engine;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.youhe.activiti.ext.NodeJumpTaskCmd;
import com.youhe.common.Constant;
import com.youhe.entity.activiti.FlowVariable;
import com.youhe.entity.activitiData.ProdefTask;
import com.youhe.exception.YuheOAException;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.ReadOnlyProcessDefinition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 我的流程引擎实现类
 */
@Service
public class MyProcessEngineImpl implements MyProcessEngine {

    private final static Logger LOGGER = LoggerFactory.getLogger(MyProcessEngineImpl.class);

    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FormService formService;
    @Autowired
    private HistoryService historyService;

    @Override
    public String createModel() {
        final String modelName = "modelName";
        final String modelKey = "modelKey";
        final String description = "description";

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        Model modelData = repositoryService.newModel();

        ObjectNode modelObjectNode = objectMapper.createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, modelName);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelData.setMetaInfo(modelObjectNode.toString());
        modelData.setName(modelName);
        modelData.setKey(modelKey);

        LOGGER.info("editorNode.toString() = {}", editorNode.toString());

        // 保存模型
        repositoryService.saveModel(modelData);
        repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes(StandardCharsets.UTF_8));
        return modelData.getId();
    }

    @Override
    public List<Model> getModelList() {
        return repositoryService.createModelQuery().list();
    }

    @Override
    public String deploy(String modelId) {
        String modelName = "";
        try {
            //获取模型
            //RepositoryService repositoryService = processEngine.getRepositoryService();
            Model modelData = repositoryService.getModel(modelId);
            modelName = modelData.getName();
            byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
            if (bytes == null) {
                throw new YuheOAException("模型数据为空，请先设计流程并成功保存，再进行发布。");
            }
            JsonNode modelNode = new ObjectMapper().readTree(bytes);
            LOGGER.info("modelNode = {}", modelNode.toString());
//            LOGGER.info("modelNode.formKey = {}", modelNode.get("formKey"));
            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            if (model.getProcesses().size() == 0) {
                throw new YuheOAException("数据模型不符要求，请至少设计一条主线流程。");
            }
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

            //发布流程
            String processName = modelName + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment()
                    .name(modelName)
                    .addString(processName, new String(bpmnBytes, StandardCharsets.UTF_8))
                    .deploy();

            modelData.setDeploymentId(deployment.getId());
            repositoryService.saveModel(modelData);

            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();
            LOGGER.info("流程{}成功发布", modelName);

            return processDefinition.getId();
        } catch (Exception e) {
            LOGGER.error("流程{}发布失败：{}", modelName, e.getMessage());
            throw new YuheOAException("流程发布失败" + e.getMessage());
        }

    }

    @Override
    public List<Model> getDeployedProcesses() {
        return repositoryService.createModelQuery().deployed().list();
    }

    @Override
    public ProcessInstance start(String processDefinitionId) {
        return start(processDefinitionId, null);
    }

    @Override
    public ProcessInstance start(String processDefinitionId, String businessId) {
        try {
            // 获取当前登录用户
            Long userId = ShiroUserUtils.getUserId();
            Map<String, Object> variables = new HashMap<>();
            FlowVariable flowVariable = new FlowVariable();
            flowVariable.setNextUserId(String.valueOf(userId));
            variables.put(Constant.FLOW_VARIABLE_KEY, flowVariable);
            // 设置当前任务的办理人
            Authentication.setAuthenticatedUserId(String.valueOf(userId));

            ProcessInstance processInstance;
            if (StrUtil.isAllBlank(businessId)) {
                processInstance = runtimeService.startProcessInstanceById(processDefinitionId, variables);
            } else {
                processInstance = runtimeService.startProcessInstanceById(processDefinitionId, variables);
            }

            // 设置流程启动后的相关核心变量
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
            String processName = processDefinition.getName();
            LOGGER.info("用户{}启动了[{}]实例（{}）", userId, processName, processInstance.getId());
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getProcessInstanceId()).singleResult();
            flowVariable.setProcessName(processName);
            flowVariable.setProcessDefinitionId(processDefinitionId);
            flowVariable.setProcessInstanceId(processInstance.getProcessInstanceId());
            flowVariable.setCurrentNodeKey(task.getTaskDefinitionKey());
            flowVariable.setTaskId(task.getId());
            flowVariable.setExecutionId(task.getExecutionId());
            variables.put(Constant.FLOW_VARIABLE_KEY, flowVariable);
            taskService.setVariables(task.getId(), variables);

            return processInstance;
        } catch (Exception e) {
            throw new YuheOAException("流程启动失败：" + e.getMessage());
        }
    }

    @Override
    public void submitTask(Map<String, Object> taskFlowData) {
        String userId = String.valueOf(ShiroUserUtils.getUserId());
        Object o = taskFlowData.get(Constant.FLOW_VARIABLE_KEY);
        JSONObject object = JSONUtil.parseObj(o);
        FlowVariable flowVariable = object.toBean(FlowVariable.class);
        flowVariable.setFirstSubmit(false);
        taskFlowData.put(Constant.FLOW_VARIABLE_KEY, flowVariable);

        final String processInstanceId = flowVariable.getProcessInstanceId(); // 流程实例ID
        final String nextUserId = flowVariable.getNextUserId();   // 下一节点审批人用户ID

        if (StrUtil.isBlank(processInstanceId)) {
            throw new YuheOAException("流程实例ID不允许为空");
        }
        if (StrUtil.isBlank(nextUserId)) {
            throw new YuheOAException("下一环节审批人不允许为空");
        }
        // 提交任务
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .taskId(flowVariable.getTaskId()).singleResult();
        taskService.addComment(task.getId(), processInstanceId,
                flowVariable.getComment() == null ? Constant.DEFAULT_AGREE_COMMENT : flowVariable.getComment()); // 添加审批意见
        Authentication.setAuthenticatedUserId(userId);
        taskService.complete(task.getId(), taskFlowData);
    }


    @Override
    public List<ProdefTask> getTaskList(String userId) {
        List<ProdefTask> ptList=new ArrayList<>();

        List<Task> taskList = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
        taskList.forEach(lists->{
            //通过浅克隆创建对象
            ProdefTask pt = ProdefTask.getOnePerson();

            pt.setAssignee(lists.getAssignee());
            pt.setName(lists.getName());
            pt.setCreateTime(String.valueOf(lists.getCreateTime()));
            pt.setTaskId(lists.getId());
            String processDefinitionId = lists.getProcessDefinitionId();
            //根据流程定义id查询出流程名称
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(processDefinitionId)
                    .singleResult();
          pt.setName_(processDefinition.getName());

            ptList.add(pt);
        });
        return ptList;
        /*taskList.stream().map(BeanUtil::beanToMap).collect(Collectors.toList())*/
    }

    @Override
    public List<ProdefTask> getHisTaskList(String userId) {
        List<ProdefTask> ptHisList=new ArrayList<>();

        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).orderByHistoricTaskInstanceEndTime().desc().list();
        list.forEach(lists->{
            //通过浅克隆创建对象
            ProdefTask pt = ProdefTask.getOnePerson();

            pt.setAssignee(lists.getAssignee());
            pt.setName(lists.getName());
            pt.setCreateTime(String.valueOf(lists.getCreateTime()));
            String processDefinitionId = lists.getProcessDefinitionId();
            //根据流程定义id查询出流程名称
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(processDefinitionId)
                    .singleResult();
            pt.setName_(processDefinition.getName());

            ptHisList.add(pt);
        });
        return ptHisList;
    }

    @Override
    public Map<String, Object> getTaskFormData(String taskId) {

        // 获取当前登录用户
        String userId = String.valueOf(ShiroUserUtils.getUserId());
        Task task = taskService.createTaskQuery().taskId(taskId).taskAssignee(userId).singleResult();

        if (task == null) {
            // todo 流程完结或当前用户没有权限
            return null;
        }

        // 自定义外置表单（表单都放在templates/activiti/form目录下） todo 还未完善，以后再扩展
        String formKey = task.getFormKey();

        // 获取当前节点表单数据
        Map<String, Object> variables = taskService.getVariables(taskId);
        FlowVariable flowVariable = (FlowVariable) variables.get(Constant.FLOW_VARIABLE_KEY);

        String firstNodeKey = flowVariable.getFirstNodeKey();
        String currentNodeKey = task.getTaskDefinitionKey();
        if (StrUtil.isBlank(firstNodeKey) || firstNodeKey.equals(currentNodeKey)) {  // 首节点
            firstNodeKey = currentNodeKey;
            flowVariable.setFirstNodeKey(firstNodeKey); // 保存首节点key值
            flowVariable.setFirstNode(true);
            if (StrUtil.isNotBlank(formKey)) {
                flowVariable.setFormKey(formKey);   // 设置表单
                flowVariable.setMainFormKey(formKey);   // 设置主表单
            } else {
                flowVariable.setFormKey(Constant.DEFAULT_FORM_NAME);   // 没有设置表单，使用默认的
                flowVariable.setMainFormKey(Constant.DEFAULT_FORM_NAME);
            }
        } else {    // 其它节点
            flowVariable.setFirstNode(false);
            if (StrUtil.isBlank(formKey)) {
                flowVariable.setFormKey(flowVariable.getMainFormKey()); // 如果其它节点没有配置表单则使用主表单
            } else {
                flowVariable.setFormKey(formKey);   // 否则更换成当前节点的表单
            }
        }
        flowVariable.setUserId(userId);
        flowVariable.setCurrentNodeKey(currentNodeKey);
        flowVariable.setCurrentNodeName(task.getName());
        flowVariable.setTaskId(taskId);
        flowVariable.setExecutionId(task.getExecutionId());
        flowVariable.setUserId(String.valueOf(userId));
        variables.put(Constant.FLOW_VARIABLE_KEY, flowVariable);
        LOGGER.info("variables = {}", variables);
        return variables;
    }

    @Override
    public String getProcessXmlData(String modelId) {
        org.activiti.engine.repository.Model modelData = repositoryService.getModel(modelId);

        BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
        JsonNode editorNode;
        try {
            editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
        } catch (IOException e) {
            throw new YuheOAException("获取流程xml数据出错：" + e.getMessage());
        }
        BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
        BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
        byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
        return new String(bpmnBytes);
    }

    @Override
    public String getStartUserId(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        return historicProcessInstance.getStartUserId();
    }

    @Override
    public String getStartActivityId(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        return historicProcessInstance.getStartActivityId();
    }

    @Override
    public HistoricActivityInstance getHisActivityInstance(String processInstanceId, String activityId) {
        HistoricActivityInstance historicActivityInstance = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .activityId(activityId)
                .orderByHistoricActivityInstanceStartTime()
                .desc().singleResult();
        return historicActivityInstance;
    }

    @Override
    public String getHisAssignee(String processInstanceId, String activityId) {
        HistoricActivityInstance hisActivityInstance = this.getHisActivityInstance(processInstanceId, activityId);
        return hisActivityInstance.getAssignee();
    }

    @Override
    public void gotoAnyTask(String targetTaskDefKey, Map<String, Object> map, String assignee, String comment, Integer type) {
        try {
            FlowVariable flowVariable = (FlowVariable) map.get(Constant.FLOW_VARIABLE_KEY);
            String processInstanceId = flowVariable.getProcessInstanceId();
            String processDefinitionId = flowVariable.getProcessDefinitionId();
            ReadOnlyProcessDefinition processDefinitionEntity = (ReadOnlyProcessDefinition) repositoryService
                    .getProcessDefinition(processDefinitionId);
            // 目标节点
            ActivityImpl destinationActivity = (ActivityImpl) processDefinitionEntity.findActivity(targetTaskDefKey);
            // 优先使用参数过来的审批人
            if (Constant.NODE_JUMP_TYPE_ROLL == type) { // 回退用户就使用历史审批用户
                if (StrUtil.isBlank(assignee)) {
                    // 获取历史任务审批人
                    assignee = this.getHisAssignee(processInstanceId, destinationActivity.getId());
                }
            } else if (Constant.NODE_JUMP_TYPE_GO == type) {    // 前进就使用页面带进来的审批用户
                String nextUserId = flowVariable.getNextUserId();
                if (StrUtil.isBlank(nextUserId)) {
                    throw new YuheOAException("审批人不允许为空");
                }
                assignee = nextUserId;
            } else {
                throw new YuheOAException("gotoAnyTask参数type错误");
            }

            // 设置目标节点审批人
            flowVariable.setNextUserId(assignee);

            // 优先使用参数传过来的意见
            if (StrUtil.isBlank(comment)) {
                comment = flowVariable.getComment();
            }

            // 当前节点
            String executionId = flowVariable.getExecutionId();
            Task task = taskService.createTaskQuery().executionId(executionId).singleResult();
            ActivityImpl currentActivity = (ActivityImpl) processDefinitionEntity
                    .findActivity(task.getTaskDefinitionKey());
            taskService.addComment(task.getId(), processInstanceId, comment);
            CommandExecutor commandExecutor = ((TaskServiceImpl) taskService)
                    .getCommandExecutor();

            // 开始跳转节点
            commandExecutor.execute(new NodeJumpTaskCmd(executionId, destinationActivity, map, currentActivity));
        } catch (Exception e) {
            throw new YuheOAException("节点跳转失败：" + e.getMessage());
        }
    }

    @Override
    public void gotoFirstTask(Map<String, Object> map) {
        FlowVariable flowVariable = (FlowVariable) map.get(Constant.FLOW_VARIABLE_KEY);
        String processInstanceId = flowVariable.getProcessInstanceId();
        String startUserId = this.getStartUserId(processInstanceId);
        String activityId = this.getStartActivityId(processInstanceId);

        this.gotoAnyTask(activityId, map, startUserId, "回退首环节", Constant.NODE_JUMP_TYPE_ROLL);
    }

    @Override
    public void gotoPreTask(Map<String, Object> map) {
        FlowVariable flowVariable = (FlowVariable) map.get(Constant.FLOW_VARIABLE_KEY);

        // 取得流程定义
        ProcessDefinitionEntity definition = (ProcessDefinitionEntity) (repositoryService.getProcessDefinition(flowVariable.getProcessDefinitionId()));

        // 获取当前任务
        Task task = taskService.createTaskQuery().taskId(flowVariable.getTaskId()).singleResult();

        // 取得上一步活动
        ActivityImpl currActivity = definition.findActivity(task.getTaskDefinitionKey());
        List<PvmTransition> nextTransitionList = currActivity.getIncomingTransitions();
        // 暂时不考虑上一步活动为会签的情况，默认认为上一步活动只有一个
        PvmActivity nextActivity = nextTransitionList.get(0).getSource();
        String activityId = nextActivity.getId();
        ActivityImpl nextActivityImpl = definition.findActivity(activityId);
        String assignee = this.getHisAssignee(flowVariable.getProcessInstanceId(), activityId);

        this.gotoAnyTask(activityId, map, assignee, "驳回", Constant.NODE_JUMP_TYPE_ROLL);
    }


}
