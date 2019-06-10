package com.youhe.activiti.engine;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.youhe.activiti.ext.FelSupport;
import com.youhe.activiti.ext.NodeJumpTaskCmd;
import com.youhe.common.Constant;
import com.youhe.entity.activiti.Copyto;
import com.youhe.entity.activiti.FlowVariable;
import com.youhe.entity.activiti.HiDelegate;
import com.youhe.entity.activiti.Node;
import com.youhe.entity.activitiData.MyCommentEntity;
import com.youhe.entity.activitiData.ProdefTask;
import com.youhe.entity.department.Department;
import com.youhe.entity.user.User;
import com.youhe.exception.YuheOAException;
import com.youhe.mapper.activiti.ActivityMapper;
import com.youhe.mapper.user.UserMapper;
import com.youhe.service.activiti.CopytoService;
import com.youhe.service.activiti.HiDelegateService;
import com.youhe.utils.activiti.TimeAsc;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.*;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.*;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.CommentEntity;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.ReadOnlyProcessDefinition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
    private HistoryService historyService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ManagementService managementService;
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private CopytoService copytoService;
    @Autowired
    private HiDelegateService hiDelegateService;

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
        return repositoryService.createModelQuery().orderByCreateTime().desc().list();
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
    public List<Map<String, Object>> getDeployedProcesses() {
        List<Model> list = repositoryService.createModelQuery().deployed().orderByLastUpdateTime().desc().list();
        List<Map<String, Object>> maps = list.stream().map(BeanUtil::beanToMap).collect(Collectors.toList());
        maps.forEach(map -> {
            ProcessDefinition processDefinition = repositoryService
                    .createProcessDefinitionQuery().deploymentId((String) map.get("deploymentId")).singleResult();
            map.put("processDefId", processDefinition.getId());
        });
        return maps;
    }

    @Override
    public ProcessInstance start(String processDefinitionId) {
        return this.start(processDefinitionId, null, null);
    }

    @Override
    public ProcessInstance start(String processDefinitionId, String businessId) {
        return this.start(processDefinitionId, businessId, null);
    }

    @Override
    public ProcessInstance start(String processDefinitionId, Long userId) {
        return this.start(processDefinitionId, null, userId);
    }

    @Override
    public ProcessInstance start(String processDefinitionId, String businessId, Long userId) {
        try {
            Map<String, Object> variables = new HashMap<>();
            FlowVariable flowVariable = new FlowVariable();
            if (userId == null) {
                // 获取当前登录用户
                userId = ShiroUserUtils.getUserId();
            } else {
                flowVariable.setAgency(true);
            }

            flowVariable.setUserId(String.valueOf(userId));
            flowVariable.setFirstNode(true);
            flowVariable.setFirstSubmit(true);
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
        int nodeNum = flowVariable.getNextNodeNum();

        if (StrUtil.isBlank(processInstanceId)) {
            throw new YuheOAException("流程实例ID不允许为空");
        }
        if (StrUtil.isBlank(nextUserId) && nodeNum != 0) {
            throw new YuheOAException("下一环节审批人不允许为空");
        }

        String comment = StrUtil.isBlank(flowVariable.getComment()) ? Constant.DEFAULT_AGREE_COMMENT : flowVariable.getComment();
        if (nodeNum == 0 || nodeNum == 1) {    // 正常提交任务
            Task task = taskService.createTaskQuery()
                    .processInstanceId(processInstanceId)
                    .taskId(flowVariable.getTaskId()).singleResult();

            // 创建附件
            String filePaths = (String) taskFlowData.get("filePath");
            String fileNames = (String) taskFlowData.get("fileName");
            if (StrUtil.isNotBlank(filePaths)) {
                String[] filePathArr = filePaths.split(",");
                String[] fileNameArr = fileNames.split(",");
                for (int i = 0; i < filePathArr.length; i++) {
                    this.createAttachment(userId, "", task.getId(), processInstanceId, fileNameArr[i], "", filePathArr[i]);
                }
            }
            taskService.addComment(task.getId(), processInstanceId, comment); // 添加审批意见
            Authentication.setAuthenticatedUserId(userId);

            // owner不为空说明可能存在委托任务
            if (StrUtil.isNotBlank(task.getOwner())) {
                DelegationState delegationState = task.getDelegationState();
                // 把被委托人代理处理后的任务交回给委托人
                if (DelegationState.PENDING == delegationState) {
                    taskService.resolveTask(task.getId());
                }
            }
            taskService.setAssignee(task.getId(), userId);
            taskService.complete(task.getId(), taskFlowData);
        } else if (nodeNum > 1) {  // 跳转分支任务
            this.gotoAnyTask(flowVariable.getTargetTaskDefKey(), taskFlowData, null, comment, Constant.NODE_JUMP_TYPE_GO);
        } else {
            throw new YuheOAException("提交任务异常");
        }

        // 若有抄送人，则插入抄送记录
        final String ccUserId = flowVariable.getCcUserId();
        if (StrUtil.isNotBlank(ccUserId)) {
            // 获取提交成功后的目标任务节点实例。注意不是当前任务节点
            /*List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).active().list();
            tasks.forEach(task -> {
                Copyto copyto;
                for (String cc : ccUserId.split(",")) {
                    copyto = new Copyto();
                    copyto.setCc(cc).setAssignee(nextUserId)
                            .setProcInstId(processInstanceId).setTaskId(task.getId())
                            .setProcName(flowVariable.getProcessName());
                    copytos.add(copyto);
                }
            });*/
            // 抄送当前审批环节
            List<Copyto> copytos = new ArrayList<>();
            Copyto copyto;
            for (String cc : ccUserId.split(",")) {
                copyto = new Copyto();
                copyto.setCc(cc).setAssignee(nextUserId)
                        .setProcInstId(processInstanceId).setTaskId(flowVariable.getTaskId())
                        .setProcName(flowVariable.getProcessName());
                copytos.add(copyto);
            }
            copytoService.saveBatch(copytos);
        }

    }

    @Override
    public Map<String, Object> getTaskFormValue(String taskId) {


        HistoricTaskInstance historicTaskInstance = historyService.createHistoricTaskInstanceQuery()
                .taskId(taskId).singleResult();


        List<HistoricVariableInstance> hisList = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(historicTaskInstance.getProcessInstanceId()).list();

        Map<String, Object> hisMap = new HashMap<>();
        hisList.forEach(list -> {
            hisMap.put(list.getVariableName(), list.getValue());
        });
        return hisMap;
    }

    @Override
    public List<ProdefTask> getTaskList(String userId) {
        List<ProdefTask> ptList = new ArrayList<>();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        List<Task> taskList = taskService.createTaskQuery().taskCandidateOrAssigned(userId).orderByTaskCreateTime().desc().list();
        taskList.forEach(lists -> {
            //通过浅克隆创建对象
            ProdefTask pt = ProdefTask.getOnePerson();


            pt.setName(lists.getName());
            pt.setCreateTime(date.format(lists.getCreateTime()));
            pt.setTaskId(lists.getId());

            //根据流程实例id查询发起人
            String startUserId = this.getStartUserId(lists.getProcessInstanceId());
            pt.setStartUserId(startUserId);
            User user = userMapper.findName(pt.getStartUserId());
            pt.setStartUserName(user.getUserName());
            //查询出上一环节的审批人
            String preAssignee = this.getPreAssignee(lists);
            if (StringUtils.isNotBlank(preAssignee)) {
                User userMapperName = userMapper.findName(preAssignee);
                pt.setPreUserName(userMapperName.getUserName());
            }

            //根据流程定义id查询出流程名称(标题)
            String processDefinitionId = lists.getProcessDefinitionId();
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(processDefinitionId)
                    .singleResult();
            pt.setName_(processDefinition.getName());

            ptList.add(pt);
        });
        return ptList;

    }

    @Override
    public List<ProdefTask> getHisTaskList(String userId) {
        List<ProdefTask> ptHisList = new ArrayList<>();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().taskAssignee(userId).finished().orderByHistoricTaskInstanceEndTime().desc().list();

        list.forEach(lists -> {
            //通过浅克隆创建对象
            ProdefTask pt = ProdefTask.getOnePerson();


            pt.setName(lists.getName());
            pt.setCreateTime(date.format(lists.getCreateTime()));
            pt.setEndTime(date.format(lists.getEndTime()));
            pt.setTaskId(lists.getId());

            //根据流程实例id查询发起人
            String startUserId = this.getStartUserId(lists.getProcessInstanceId());
            pt.setStartUserId(startUserId);
            User user = userMapper.findName(pt.getStartUserId());
            pt.setStartUserName(user.getUserName());
            //根据流程定义id查询出流程名称(标题)
            String processDefinitionId = lists.getProcessDefinitionId();
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(processDefinitionId)
                    .singleResult();
            pt.setName_(processDefinition.getName());

            ptHisList.add(pt);
        });
        return ptHisList;
    }

    @Override
    public int total(String userId) {
        int listSize = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(userId).finished().orderByHistoricTaskInstanceEndTime()
                .desc().list().size();
        return listSize;
    }

    @Override
    public List<ProdefTask> getHisApplyList(String userId, int size, int current) {
        List<ProdefTask> ptHisList = new ArrayList<>();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        int currents = current - 1;
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(userId)
                .finished().orderByHistoricTaskInstanceEndTime().desc()
                .listPage(currents * size, size);
        list.forEach(lists -> {
            //通过浅克隆创建对象
            ProdefTask pt = ProdefTask.getOnePerson();


            pt.setName(lists.getName());
            pt.setCreateTime(date.format(lists.getCreateTime()));
            pt.setEndTime(date.format(lists.getEndTime()));
            pt.setTaskId(lists.getId());

            //根据流程实例id查询发起人
            String startUserId = this.getStartUserId(lists.getProcessInstanceId());
            pt.setStartUserId(startUserId);
            User user = userMapper.findName(pt.getStartUserId());
            pt.setStartUserName(user.getUserName());
            //根据流程定义id查询出流程名称(标题)
            String processDefinitionId = lists.getProcessDefinitionId();
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
        Task task = taskService.createTaskQuery().taskId(taskId).taskCandidateOrAssigned(userId).singleResult();

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

        // 设置下一步路由出口用户任务节点集
        List<UserTask> nextNodes = this.getNextNode(flowVariable.getProcessDefinitionId(), flowVariable.getCurrentNodeKey(), variables);
        List<Node> nodes = new ArrayList<>();
        nextNodes.forEach(userTask -> {
            Node node = new Node(userTask.getId(), userTask.getName());

            TaskDefinition taskDefinition = this.getTaskDefinition(userTask.getId(), flowVariable.getProcessDefinitionId());

            Expression assigneeExpression = taskDefinition.getAssigneeExpression();
            Set<Expression> candidateUserIdExpressions = taskDefinition.getCandidateUserIdExpressions();
            Set<Expression> candidateGroupIdExpressions = taskDefinition.getCandidateGroupIdExpressions();
            if (assigneeExpression != null) {
                node.setAssignee(assigneeExpression.getExpressionText());
            }
            if (CollectionUtil.isNotEmpty(candidateUserIdExpressions)) {
                StringBuilder candidateUserIds = new StringBuilder();
                for (Expression candidateUserIdExpression : candidateUserIdExpressions) {
                    if (candidateUserIds.length() > 0) {
                        candidateUserIds.append(",");
                    }
                    candidateUserIds.append(candidateUserIdExpression.getExpressionText());
                }
                node.setCandidateUserIds(candidateUserIds.toString());
            }
            if (CollectionUtil.isNotEmpty(candidateGroupIdExpressions)) {
                StringBuilder candidateGroupIds = new StringBuilder();
                for (Expression candidateGroupIdExpression : candidateGroupIdExpressions) {
                    if (candidateGroupIds.length() > 0) {
                        candidateGroupIds.append(",");
                    }
                    candidateGroupIds.append(candidateGroupIdExpression.getExpressionText());
                }
                node.setCandidateGroupIds(candidateGroupIds.toString());
            }

            nodes.add(node);

        });


        flowVariable.setNextNodeNum(nextNodes.size());
        flowVariable.setNextNodes(nodes);
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
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .activityId(activityId)
                .finished()
                .orderByHistoricActivityInstanceStartTime()
                .desc().list();
        return CollectionUtil.isEmpty(list) ? null : list.get(0);   // 取最新那条就行
    }

    @Override
    public String getHisAssignee(String processInstanceId, String activityId) {
        HistoricActivityInstance hisActivityInstance = this.getHisActivityInstance(processInstanceId, activityId);
        return hisActivityInstance == null ? "" : hisActivityInstance.getAssignee();
    }

    @Override
    public String getPreAssignee(Task task) {
        if (task == null) {
            throw new YuheOAException("获取上一环节审批人异常");
        }

        Map<String, Object> variables = taskService.getVariables(task.getId());
        FlowVariable flowVariable = (FlowVariable) variables.get(Constant.FLOW_VARIABLE_KEY);
        String preActivityId = this.getPreActivityId(task);
        return this.getHisAssignee(flowVariable.getProcessInstanceId(), preActivityId);
    }

    @Override
    public String getPreActivityId(Task task) {
        if (task == null) {
            throw new YuheOAException("获取上一活动ID异常");
        }
        Map<String, Object> variables = taskService.getVariables(task.getId());
        FlowVariable flowVariable = (FlowVariable) variables.get(Constant.FLOW_VARIABLE_KEY);
        // 取得流程定义
        ProcessDefinitionEntity definition = (ProcessDefinitionEntity) (repositoryService.getProcessDefinition(flowVariable.getProcessDefinitionId()));
        // 取得上一步活动
        ActivityImpl currActivity = definition.findActivity(task.getTaskDefinitionKey());
        List<PvmTransition> nextTransitionList = currActivity.getIncomingTransitions();

        // 暂时不考虑上一步活动为会签的情况，默认认为上一步活动只有一个
        StringBuilder instr = new StringBuilder();
//        PvmActivity nextActivity = nextTransitionList.get(0).getSource();
        nextTransitionList.forEach(nextTransition -> {
            if (instr.length() > 0) {
                instr.append(",");
            }
            instr.append("'");
            instr.append(nextTransition.getSource().getId());
            instr.append("'");
        });
        String selSql = "select * from " +
                managementService.getTableName(HistoricActivityInstance.class) +
                " t where t.PROC_INST_ID_ = '" + flowVariable.getProcessInstanceId() + "'" +
                " and t.END_TIME_ is not null" +
                " and t.ACT_ID_ in(" + instr.toString() + ") order by START_TIME_ desc limit 1";
        // 进一步确认当前节点的上一个提交节点
        HistoricActivityInstance preActivity = historyService.createNativeHistoricActivityInstanceQuery().sql(selSql).singleResult();
        return preActivity.getActivityId();
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

            // 更新历史实例表(因为使用命令跳转节点历史节点没有设置结束时间，所以使用手动更新)
            activityMapper.updateHistoricActivityInstance(flowVariable.getProcessInstanceId(), currentActivity.getId());
        } catch (Exception e) {
            throw new YuheOAException("节点跳转失败：" + e.getMessage());
        }
    }

    @Override
    public void gotoFirstTask(String taskId) {
        Map<String, Object> map = this.getTaskFormData(taskId);
        if (map == null) {
            throw new YuheOAException("当前任务不存在或已被提交");
        }

        FlowVariable flowVariable = (FlowVariable) map.get(Constant.FLOW_VARIABLE_KEY);
        String processInstanceId = flowVariable.getProcessInstanceId();
        String startUserId = this.getStartUserId(processInstanceId);
        String activityId = this.getStartActivityId(processInstanceId);

        this.gotoAnyTask(activityId, map, startUserId, "回退首环节", Constant.NODE_JUMP_TYPE_ROLL);
    }

    @Override
    public void gotoPreTask(String taskId) {
        Map<String, Object> map = this.getTaskFormData(taskId);
        if (map == null) {
            throw new YuheOAException("当前任务不存在或已被提交");
        }

        // 获取当前任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String activityId = this.getPreActivityId(task);
        String assignee = this.getPreAssignee(task);

        this.gotoAnyTask(activityId, map, assignee, "驳回", Constant.NODE_JUMP_TYPE_ROLL);
    }

    @Override
    public List<MyCommentEntity> findAdvice(String processInstanceId) {
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<MyCommentEntity> comList = new ArrayList();

        List<Comment> proComments = taskService.getProcessInstanceComments(processInstanceId);

        proComments.forEach(comment -> {

            CommentEntity commentEntity = (CommentEntity) comment;
            MyCommentEntity com = new MyCommentEntity();
            com.setTime(date.format(commentEntity.getTime()));
            BeanUtils.copyProperties(commentEntity, com);

            //根据任务id查询出审批人和当前环节名称
            List<HistoricTaskInstance> userList = processEngine.getHistoryService().createHistoricTaskInstanceQuery().taskId(comment.getTaskId()).list();
            userList.forEach(ul -> {
                User userMapperName = userMapper.findName(ul.getAssignee());
                com.setUserId(userMapperName.getUserName());
                com.setCurNoName(ul.getName());
            });

            comList.add(com);
        });
        TimeAsc.getInstance().ListSort(comList);
        return comList;
    }

    @Override
    public List<UserTask> getNextNode(String procDefId, String taskDefKey, Map<String, Object> map) {
        List<UserTask> userTasks = new ArrayList<>();
        // 获取BpmnModel对象
        BpmnModel bpmnModel = repositoryService.getBpmnModel(procDefId);
        // 获取Process对象
        Process process = bpmnModel.getProcesses().get(bpmnModel.getProcesses().size() - 1);
        // 获取所有的FlowElement信息
        Collection<FlowElement> flowElements = process.getFlowElements();
        // 获取当前节点信息
        FlowElement flowElement = getFlowElementById(taskDefKey, flowElements);

        getNextNodeRecur(flowElements, flowElement, map, userTasks);

        return userTasks;
    }

    @Override
    public TaskEntity getTaskById(String taskId) {
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new YuheOAException("任务实例未找到");
        }
        return task;
    }

    @Override
    public ProcessDefinitionEntity getProcessDefinitionByTaskId(String taskId) {
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(getTaskById(taskId)
                        .getProcessDefinitionId());
        if (processDefinition == null) {
            throw new YuheOAException("流程定义未找到");
        }
        return processDefinition;
    }

    @Override
    public ProcessInstance getProcessInstanceByTaskId(String taskId) {
        ProcessInstance processInstance = runtimeService
                .createProcessInstanceQuery().processInstanceId(
                        getTaskById(taskId).getProcessInstanceId())
                .singleResult();
        if (processInstance == null) {
            throw new YuheOAException("流程实例未找到");
        }
        return processInstance;
    }

    @Override
    public ActivityImpl getActivityImpl(String taskId) {
        return this.getActivityImpl(taskId, null);
    }

    @Override
    public ActivityImpl getActivityImpl(String taskId, String activityId) {
        // 取得流程定义
        ProcessDefinitionEntity processDefinition = this.getProcessDefinitionByTaskId(taskId);

        // 获取当前活动节点ID
        if (StrUtil.isBlank(activityId)) {
            activityId = this.getTaskById(taskId).getTaskDefinitionKey();
        }

        // 根据流程定义，获取该流程实例的结束节点
        if (activityId.toUpperCase().equals("END")) {
            for (ActivityImpl activityImpl : processDefinition.getActivities()) {
                List<PvmTransition> pvmTransitionList = activityImpl
                        .getOutgoingTransitions();
                if (pvmTransitionList.isEmpty()) {
                    return activityImpl;
                }
            }
        }

        // 根据节点定义ID，获取对应的活动节点
        return processDefinition.findActivity(activityId);
    }

    @Override
    public List<ActivityImpl> getCanBackActivity(String taskId, ActivityImpl currActivity, List<ActivityImpl> rtnList, List<ActivityImpl> tempList) {
        ProcessInstance processInstance = this.getProcessInstanceByTaskId(taskId);

        // 当前节点的流入来源
        List<PvmTransition> incomingTransitions = currActivity
                .getIncomingTransitions();
        // 条件分支节点集合，userTask节点遍历完毕，迭代遍历此集合，查询条件分支对应的userTask节点
        List<ActivityImpl> exclusiveGateways = new ArrayList<>();
        // 并行节点集合，userTask节点遍历完毕，迭代遍历此集合，查询并行节点对应的userTask节点
        List<ActivityImpl> parallelGateways = new ArrayList<>();
        // 遍历当前节点所有流入路径
        for (PvmTransition pvmTransition : incomingTransitions) {
            TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
            ActivityImpl activityImpl = transitionImpl.getSource();
            String type = (String) activityImpl.getProperty("type");
            /**
             * 并行节点配置要求：<br>
             * 必须成对出现，且要求分别配置节点ID为:XXX_start(开始)，XXX_end(结束)
             */
            if ("parallelGateway".equals(type)) {// 并行路线
                String gatewayId = activityImpl.getId();
                String gatewayType = gatewayId.substring(gatewayId
                        .lastIndexOf("_") + 1);
                if ("START".equals(gatewayType.toUpperCase())) {// 并行起点，停止递归
                    return rtnList;
                } else {// 并行终点，临时存储此节点，本次循环结束，迭代集合，查询对应的userTask节点
                    parallelGateways.add(activityImpl);
                }
            } else if ("startEvent".equals(type)) {// 开始节点，停止递归
                return rtnList;
            } else if ("userTask".equals(type)) {// 用户任务
                tempList.add(activityImpl);
            } else if ("exclusiveGateway".equals(type)) {// 分支路线，临时存储此节点，本次循环结束，迭代集合，查询对应的userTask节点
                currActivity = transitionImpl.getSource();
                exclusiveGateways.add(currActivity);
            }
        }

        // 迭代条件分支集合，查询对应的userTask节点
        for (ActivityImpl activityImpl : exclusiveGateways) {
            this.getCanBackActivity(taskId, activityImpl, rtnList, tempList);
        }

        // 迭代并行集合，查询对应的userTask节点
        for (ActivityImpl activityImpl : parallelGateways) {
            this.getCanBackActivity(taskId, activityImpl, rtnList, tempList);
        }

        // 根据同级userTask集合，过滤最近发生的节点
        currActivity = filterNewestActivity(processInstance.getProcessInstanceId(), tempList);
        if (currActivity != null) {
            // 查询当前节点的流向是否为并行终点，并获取并行起点ID
            String id = findParallelGatewayId(currActivity);
            if (StrUtil.isBlank(id)) {// 并行起点ID为空，此节点流向不是并行终点，符合驳回条件，存储此节点
                rtnList.add(currActivity);
            } else {// 根据并行起点ID查询当前节点，然后迭代查询其对应的userTask任务节点
                currActivity = this.getActivityImpl(taskId, id);
            }

            // 清空本次迭代临时集合
            tempList.clear();
            // 执行下次迭代
            this.getCanBackActivity(taskId, currActivity, rtnList, tempList);
        }
        return rtnList;
    }

    @Override
    public Attachment createAttachment(String userId, String attachmentType, String taskId, String processInstanceId, String attachmentName, String attachmentDescription, String url) {
        try {
            identityService.setAuthenticatedUserId(userId);
            return taskService.createAttachment(attachmentType, taskId, processInstanceId, attachmentName, attachmentDescription, url);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
    }

    @Override
    public InputStream getAttachmentContent(String attachmentId) {
        return taskService.getAttachmentContent(attachmentId);
    }

    @Override
    public Attachment getAttachment(String attachmentId) {
        return taskService.getAttachment(attachmentId);
    }

    @Override
    public List<Attachment> getTaskAttachments(String taskId) {
        return taskService.getTaskAttachments(taskId);
    }

    @Override
    public List<Attachment> getInstanceAttachments(String processInstanceId) {
        return taskService.getProcessInstanceAttachments(processInstanceId);
    }

    @Override
    public void deleteAttachment(String attachmentId) {
        // 如果有必要可先删除远程文件
        taskService.deleteAttachment(attachmentId);
    }

    @Override
    public String exportModelJson(String modelId) {
        ModelEntity model = (ModelEntity) repositoryService.getModel(modelId);
        if (model == null || !model.hasEditorSource()) {
            throw new YuheOAException("导出模型数据失败，模型数据不存在");
        }
        byte[] modelEditorSource = repositoryService.getModelEditorSource(modelId);
        byte[] modelEditorSourceExt = repositoryService.getModelEditorSourceExtra(modelId);
        JSONObject obj = JSONUtil.createObj();
        obj.put("modelId", model.getId());
        obj.put("modelName", model.getName());
        obj.put("modelRev", model.getRevision());
        obj.put("modelKey", model.getKey());
        obj.put("modelMetaInfo", model.getMetaInfo());
        obj.put("editorSourceValueId", model.getEditorSourceValueId());
        obj.put("editorSourceExtValueId", model.getEditorSourceExtraValueId());
        obj.put("modelEditorSource", new String(modelEditorSource, StandardCharsets.UTF_8));
        obj.put("modelEditorSourceExt", new String(modelEditorSourceExt, StandardCharsets.UTF_8));
        LOGGER.info("json={}", obj.toString());

        return obj.toString();
    }

    @Override
    public void importModel(InputStream in) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int i;
            while ((i = in.read()) != -1) {
                baos.write(i);
            }
            String jsonStr = baos.toString("utf-8");
            JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
            String modelId = jsonObject.get("modelId").toString();
            LOGGER.info("import modelId={}", modelId);
            Model modelEntity = repositoryService.getModel(modelId);
            if (modelEntity == null) {
                modelEntity = repositoryService.newModel();
            }

            // 新增/更新
            modelEntity.setName(jsonObject.get("modelName").toString());
            modelEntity.setKey(jsonObject.get("modelKey").toString());
            modelEntity.setMetaInfo(jsonObject.get("modelMetaInfo").toString());
            repositoryService.saveModel(modelEntity);

            // 新增/更新二进制数据
            byte[] modelEditorSources = jsonObject.get("modelEditorSource").toString().getBytes(StandardCharsets.UTF_8);
            byte[] modelEditorSourceExts = jsonObject.get("modelEditorSourceExt").toString().getBytes(StandardCharsets.UTF_8);
            repositoryService.addModelEditorSource(modelEntity.getId(), modelEditorSources);
            repositoryService.addModelEditorSourceExtra(modelEntity.getId(), modelEditorSourceExts);
        } catch (IOException e) {
            throw new YuheOAException("导入流程模型失败，模型数据格式不正确。");
        }

    }

    @Override
    public void importProcess(InputStream in) {
        try {
//            JsonNode jsonNode = new ObjectMapper().readTree(in);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String s = StrUtil.toString(in);
            JSONObject jsonObject = JSONUtil.xmlToJson(sb.toString());
            LOGGER.info("bytes={}", sb.toString());
            JSON jsonNode = JSONUtil.parse(in);
            String modelId = this.createModel();
            String name = jsonObject.getByPath("definitions.process.-name").toString();
            String jsonXml = jsonObject.toString();
            Model model = repositoryService.getModel(modelId);

            ObjectNode modelJson = (ObjectNode) objectMapper.readTree(model.getMetaInfo());

            modelJson.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelJson.put(ModelDataJsonConstants.MODEL_DESCRIPTION, "导入流程");
            model.setMetaInfo(modelJson.toString());
            model.setName(name);

            repositoryService.saveModel(model);

            repositoryService.addModelEditorSource(model.getId(), jsonXml.getBytes(StandardCharsets.UTF_8));
            repositoryService.addModelEditorSourceExtra(model.getId(), null);   // todo 流程图片资源还没有保存
        } catch (Exception e) {
            LOGGER.error("导入流程失败", e);
            throw new YuheOAException("导入流程失败" + e.getMessage());
        }

    }

    /**
     * 查询下一步节点 递归
     *
     * @param flowElements 全流程节点集合
     * @param flowElement  当前节点
     * @param map          业务数据
     * @param nextUser     下一步用户节点
     */
    private void getNextNodeRecur(Collection<FlowElement> flowElements, FlowElement flowElement, Map<String, Object> map, List<UserTask> nextUser) {

        //如果是结束节点
        if (flowElement instanceof EndEvent) {
            //如果是子任务的结束节点
            if (getSubProcess(flowElements, flowElement) != null) {
                flowElement = getSubProcess(flowElements, flowElement);
            }
        }

        //获取Task的出线信息--可以拥有多个
        List<SequenceFlow> outGoingFlows = null;
        if (flowElement instanceof Task) {
            outGoingFlows = ((org.activiti.bpmn.model.Task) flowElement).getOutgoingFlows();
        } else if (flowElement instanceof Gateway) {
            outGoingFlows = ((Gateway) flowElement).getOutgoingFlows();
        } else if (flowElement instanceof StartEvent) {
            outGoingFlows = ((StartEvent) flowElement).getOutgoingFlows();
        } else if (flowElement instanceof SubProcess) {
            outGoingFlows = ((SubProcess) flowElement).getOutgoingFlows();
        } else if (flowElement instanceof UserTask) {
            outGoingFlows = ((UserTask) flowElement).getOutgoingFlows();
        }

        if (outGoingFlows != null && outGoingFlows.size() > 0) {
            //遍历所有的出线--找到可以正确执行的那一条
            for (SequenceFlow sequenceFlow : outGoingFlows) {

                //1.有表达式，且为true
                //2.无表达式
                String expression = sequenceFlow.getConditionExpression();
                if (StrUtil.isBlank(expression) ||
                        Boolean.valueOf(
                                String.valueOf(
                                        FelSupport.result(map, expression.substring(expression.lastIndexOf("{") + 1, expression.lastIndexOf("}")))))) {
                    //出线的下一节点
                    String nextFlowElementID = sequenceFlow.getTargetRef();
                    //查询下一节点的信息
                    FlowElement nextFlowElement = getFlowElementById(nextFlowElementID, flowElements);

                    //用户任务
                    if (nextFlowElement instanceof UserTask) {
                        nextUser.add((UserTask) nextFlowElement);
                    }
                    //排他网关
                    else if (nextFlowElement instanceof ExclusiveGateway) {
                        getNextNodeRecur(flowElements, nextFlowElement, map, nextUser);
                    }
                    //并行网关
                    else if (nextFlowElement instanceof ParallelGateway) {
                        getNextNodeRecur(flowElements, nextFlowElement, map, nextUser);
                    }
                    //接收任务
                    else if (nextFlowElement instanceof ReceiveTask) {
                        getNextNodeRecur(flowElements, nextFlowElement, map, nextUser);
                    }
                    //子任务的起点
                    else if (nextFlowElement instanceof StartEvent) {
                        getNextNodeRecur(flowElements, nextFlowElement, map, nextUser);
                    }
                    //结束节点
                    else if (nextFlowElement instanceof EndEvent) {
                        getNextNodeRecur(flowElements, nextFlowElement, map, nextUser);
                    }
                }
            }
        }
    }

    /**
     * 查询一个节点的是否子任务中的节点，如果是，返回子任务
     *
     * @param flowElements 全流程的节点集合
     * @param flowElement  当前节点
     */
    private FlowElement getSubProcess(Collection<FlowElement> flowElements, FlowElement flowElement) {
        for (FlowElement flowElement1 : flowElements) {
            if (flowElement1 instanceof SubProcess) {
                for (FlowElement flowElement2 : ((SubProcess) flowElement1).getFlowElements()) {
                    if (flowElement.equals(flowElement2)) {
                        return flowElement1;
                    }
                }
            }
        }
        return null;
    }


    /**
     * 根据ID查询流程节点对象, 如果是子任务，则返回子任务的开始节点
     *
     * @param Id           节点ID
     * @param flowElements 流程节点集合
     */
    private FlowElement getFlowElementById(String Id, Collection<FlowElement> flowElements) {
        for (FlowElement flowElement : flowElements) {
            if (flowElement.getId().equals(Id)) {
                //如果是子任务，则查询出子任务的开始节点
                if (flowElement instanceof SubProcess) {
                    return getStartFlowElement(((SubProcess) flowElement).getFlowElements());
                }
                return flowElement;
            }
            if (flowElement instanceof SubProcess) {
                FlowElement flowElement1 = getFlowElementById(Id, ((SubProcess) flowElement).getFlowElements());
                if (flowElement1 != null) {
                    return flowElement1;
                }
            }
        }
        return null;
    }

    /**
     * 返回流程的开始节点
     *
     * @param flowElements 流程节点集合
     */
    private FlowElement getStartFlowElement(Collection<FlowElement> flowElements) {
        for (FlowElement flowElement : flowElements) {
            if (flowElement instanceof StartEvent) {
                return flowElement;
            }
        }
        return null;
    }

    /**
     * 根据流入任务集合，查询最近一次的流入任务节点
     *
     * @param processInstanceId 流程实例ID
     * @param tempList          流入任务集合
     * @return
     */
    private ActivityImpl filterNewestActivity(String processInstanceId, List<ActivityImpl> tempList) {
        while (tempList.size() > 0) {
            ActivityImpl activity_1 = tempList.get(0);
            HistoricActivityInstance activityInstance_1 = getHisActivityInstance(processInstanceId, activity_1.getId());
            if (activityInstance_1 == null) {
                tempList.remove(activity_1);
                continue;
            }
            if (tempList.size() > 1) {
                ActivityImpl activity_2 = tempList.get(1);
                HistoricActivityInstance activityInstance_2 = this.getHisActivityInstance(processInstanceId, activity_2.getId());
                if (activityInstance_2 == null) {
                    tempList.remove(activity_2);
                    continue;
                }
                if (activityInstance_1.getEndTime().before(
                        activityInstance_2.getEndTime())) {
                    tempList.remove(activity_1);
                } else {
                    tempList.remove(activity_2);
                }
            } else {
                break;
            }
        }
        if (tempList.size() > 0) {
            return tempList.get(0);
        }
        return null;
    }

    /**
     * 根据当前节点，查询输出流向是否为并行终点，如果为并行终点，则拼装对应的并行起点ID
     *
     * @param activityImpl 当前节点
     * @return
     */
    private String findParallelGatewayId(ActivityImpl activityImpl) {
        List<PvmTransition> incomingTransitions = activityImpl
                .getOutgoingTransitions();
        for (PvmTransition pvmTransition : incomingTransitions) {
            TransitionImpl transitionImpl = (TransitionImpl) pvmTransition;
            activityImpl = transitionImpl.getDestination();
            String type = (String) activityImpl.getProperty("type");
            if ("parallelGateway".equals(type)) {// 并行路线
                String gatewayId = activityImpl.getId();
                String gatewayType = gatewayId.substring(gatewayId
                        .lastIndexOf("_") + 1);
                if ("END".equals(gatewayType.toUpperCase())) {
                    return gatewayId.substring(0, gatewayId.lastIndexOf("_"))
                            + "_start";
                }
            }
        }
        return null;
    }

    @Override
    public List<Department> selectUsers() {
        return userMapper.findNames();
    }

    @Override
    public ProcessDefinitionEntity getProcessDefinition(String processDefId) {
        return (ProcessDefinitionEntity) processEngine.getRepositoryService()
                .getProcessDefinition(processDefId);
    }

    @Override
    public TaskDefinition getTaskDefinition(String taskDefId, String processDefId) {
        ProcessDefinitionEntity processDefinition = this.getProcessDefinition(processDefId);
        ActivityImpl activityImpl = processDefinition.findActivity(taskDefId); // 根据活动id获取活动实例
        return (TaskDefinition) activityImpl.getProperties().get("taskDefinition");
    }

    @Override
    public IPage<Map<String, Object>> listAllHisAgencyPage(Long userId, int current, int size) {
        Page<HiDelegate> page = new Page<>(current, size);
        LambdaQueryWrapper<HiDelegate> hiDelegateLambdaQueryWrapper = new LambdaQueryWrapper<>();
        hiDelegateLambdaQueryWrapper.eq(HiDelegate::getAttorney, userId);
        IPage<Map<String, Object>> pageMaps = hiDelegateService.pageMaps(page, hiDelegateLambdaQueryWrapper);
        List<Map<String, Object>> records = pageMaps.getRecords();
        records.forEach(record -> {
            HistoricTaskInstance hisTask = historyService.createHistoricTaskInstanceQuery().taskId((String) record.get("task_id")).singleResult();
            ProcessDefinitionEntity processDefinition = this.getProcessDefinition(hisTask.getProcessDefinitionId());
            record.put("processName", processDefinition.getName());
            record.put("taskName", hisTask.getName());
        });
        pageMaps.setRecords(records);
        return pageMaps;
    }

}
