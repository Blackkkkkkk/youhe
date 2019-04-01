package com.youhe.controller.activiti;


import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youhe.activiti.engine.MyProcessEngine;
import com.youhe.activiti.ext.ProcessDiagramGenerator;
import com.youhe.common.Constant;
import com.youhe.controller.comm.BaseController;
import com.youhe.entity.activiti.FlowVariable;
import com.youhe.entity.activiti.FormCodeData;
import com.youhe.entity.activitiData.ProdefTask;
import com.youhe.exception.YuheOAException;
import com.youhe.utils.R;
import com.youhe.utils.activiti.FormParseUtils;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "activiti")
public class ActivitiController extends BaseController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private MyProcessEngine myProcessEngine;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;

    @GetMapping(value = "ProcessManagement")
    public ModelAndView ProcessManagement() {
        return new ModelAndView("activiti/manage/ProcessManagement");
    }

    /**
     * 我的待办页面
     * @return
     */
    @GetMapping(value = "/dealwith")
    public ModelAndView indexMyDealWith() {
        return new ModelAndView("activiti/manage/MyToDo");
    }

    /**
     * 我的已办页面
     * @return
     */
    @GetMapping(value = "/dealwithdo")
    public ModelAndView indexMyDeal() {
        return new ModelAndView("activiti/manage/MyDo");
    }

    @GetMapping(value = "create")
    public void create(HttpServletRequest request, HttpServletResponse response) {
        try {
            String modelId = myProcessEngine.createModel();
            response.sendRedirect(request.getContextPath() + "/process-editor/modeler.html?modelId=" + modelId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * 获取所有流程模型
     */
    @GetMapping(value = "/modelList")
    @ResponseBody
    public R modelList() {
        return R.ok().put("modelList", myProcessEngine.getModelList());
    }

    /**
     * 发布模型为流程定义
     */
    @GetMapping("/deploy")
    @ResponseBody
    public R deploy(String modelId) {
        myProcessEngine.deploy(modelId);
        return R.ok();
    }

    /**
     * 启动流程
     *
     * @param deploymentId 流程发布ID
     * @return 任务表单
     */
    @GetMapping(value = "start")
    public ModelAndView startProcess(String deploymentId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        ProcessInstance processInstance = myProcessEngine.start(processDefinition.getId());
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        return new ModelAndView("redirect:form/task/" + task.getId());

    }

    /**
     * 提交任务
     */
    @PostMapping(value = "submit/task")
    @ResponseBody
    public R submitTask(@RequestParam Map<String, Object> map) {
        LOGGER.info("submit task ={}", map.toString());
        myProcessEngine.submitTask(map);
        return R.ok("提交成功");
    }

    /**
     * 任务表单
     *
     * @param taskId 任务ID
     * @return
     */
    @GetMapping(value = "/form/task/{taskId}")
    public ModelAndView taskForm(@PathVariable("taskId") String taskId) {
        ModelAndView mv = new ModelAndView();
        Map<String, Object> map = myProcessEngine.getTaskFormData(taskId);
        if (map == null) {
            mv.setViewName(Constant.NO_PREMISSIONS_NAME); // todo 跳转到没有权限的页面
            return mv;
        }
        FlowVariable flowVariable = (FlowVariable) map.get(Constant.FLOW_VARIABLE_KEY);
        mv.setViewName(Constant.FORM_TEMP);
        mv.addObject(Constant.TASK_DATA_KEY , map);
        FormCodeData taskFormCode = FormParseUtils.getTaskFormCode(flowVariable.getFormKey(), map);
//        LOGGER.info("taskFormCode={}", taskFormCode.toString());
        mv.addObject("table", taskFormCode.getTableHtml());
        mv.addObject("script", taskFormCode.getScript());
        return mv;
    }

    @GetMapping(value = "/form/histask/{taskId}")
    public ModelAndView histaskForm(@PathVariable("taskId") String taskId) {
        ModelAndView mv = new ModelAndView();
        Map<String, Object> map = myProcessEngine.getTaskFormValue(taskId);

        if (map == null) {
            mv.setViewName(Constant.DEFAULT_FORM_NAME); // todo 跳转到没有权限的页面
            return mv;
        }
        FlowVariable flowVariable = (FlowVariable) map.get(Constant.FLOW_VARIABLE_KEY);

        flowVariable.setFirstNode(false);

        mv.setViewName(Constant.HIS_FORM_TEMP);
        mv.addObject(Constant.TASK_DATA_KEY, map);
        FormCodeData taskFormCode = FormParseUtils.getTaskFormCode(flowVariable.getFormKey(), map);
//        LOGGER.info("taskFormCode={}", taskFormCode.toString());
        mv.addObject("table", taskFormCode.getTableHtml());
        mv.addObject("script", taskFormCode.getScript());
        return mv;
    }
    

    /**
     * 所有已发布的流程
     *
     * @return
     */
    @GetMapping(value = "deployed/processes")
    public R deployedProcesses() {
        return R.ok().put("deployedProcesses", myProcessEngine.getDeployedProcesses());
    }

    /**
     * 我的待办
     *
     * @return
     */
    @GetMapping(value = "task/list")
    public R myTaskList() {
        String userId = String.valueOf(ShiroUserUtils.getUserId());
        List<ProdefTask>   taskList=myProcessEngine.getTaskList(userId);

        return R.ok().put("tasklist", taskList);
    }

    /**
     * 我的已办
     *
     * @return
     */
    @GetMapping(value = "hisTask/list")
    public R hisTaskList() {
        String userId = String.valueOf(ShiroUserUtils.getUserId());
        List<ProdefTask> hisTaskList = myProcessEngine.getHisTaskList(userId);

        return R.ok().put("hisTaskList", hisTaskList);
    }

    @GetMapping(value = "submitTask")
    public ModelAndView submitTask() {
        return new ModelAndView("activiti/submit_task");
    }

    /**
     * 导出流程xml
     * @param modelId 模型ID
     * @param response resp
     */
    @GetMapping(value = "export/processXml")
    public void exportProcessXml(String modelId, HttpServletResponse response) {
        BufferedOutputStream bos = null;
        try {
            org.activiti.engine.repository.Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            JsonNode editorNode;
            editorNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
            BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
            byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);
            String name = StrUtil.trim(bpmnModel.getMainProcess().getName());
            String filename = name + ".bpmn20.xml";

            bos = new BufferedOutputStream(response.getOutputStream());
            bos.write(bpmnBytes);

            response.setContentType("UTF-8");
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
            response.flushBuffer();
        } catch (Exception e) {
            LOGGER.error("导出流程XML失败:" + e.getMessage());
        } finally {
            if (bos != null) {
                try {
                    bos.flush();
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 前进到任意节点
     * @param map 业务数据
     * @return
     */
    @PostMapping(value = "goForward/anyTask")
    @ResponseBody
    public R goForwardAnyTask(@RequestParam Map<String, Object> map) {
        FlowVariable flowVariable = (FlowVariable) map.get(Constant.FLOW_VARIABLE_KEY);
        myProcessEngine.gotoAnyTask(flowVariable.getTargetTaskDefKey(), map, null, null, Constant.NODE_JUMP_TYPE_GO);
        return R.ok();
    }

    /**
     * 回退到任意节点
     * @param taskId 任务ID
     * @param targetNode 目标节点
     * @return
     */
    @PostMapping(value = "rollBack/anyTask")
    public R rollBackAnyTask(String taskId, String targetNode) {
        Map<String, Object> map = myProcessEngine.getTaskFormData(taskId);
        if (map == null) {
            return R.error("当前任务不存在或已被提交");
        }
//        FlowVariable flowVariable = (FlowVariable) map.get(Constant.FLOW_VARIABLE_KEY);
        myProcessEngine.gotoAnyTask(targetNode, map, null, null, Constant.NODE_JUMP_TYPE_ROLL);
        return R.ok();
    }

    /**
     * 回退到首环节
     * @param taskId 当前任务ID
     * @return
     */
    @PostMapping(value = "rollBack/firstTask")
    public R rollBackFirstTask(String taskId) {
        myProcessEngine.gotoFirstTask(taskId);
        return R.ok();
    }

    /**
     * 驳回上环节
     * @param taskId 当前任务ID
     * @return
     */
    @PostMapping(value = "rollBack/preTask")
    public R rollBackPreTask(String taskId) {
        myProcessEngine.gotoPreTask(taskId);
        return R.ok();
    }

    /**
     * 实时流程图
     * @param processInstanceId 流程实例ID
     * @return
     */
    @GetMapping(value = "current/flowChart")
    public ModelAndView currentFlowChart(String processInstanceId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("processInstanceId", processInstanceId);
        modelAndView.setViewName(Constant.FLOW_CHART);
        return modelAndView;
    }

    /**
     * 流程图像，已执行节点和流程线高亮显示
     * @param processInstanceId 流程实例ID
     * @param response
     */
    @GetMapping(value = "flowChart/image")
    public void flowChartImage(String processInstanceId, HttpServletResponse response) {
        LOGGER.info("[开始]-获取流程图图像");
        // 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            //  获取历史流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(processInstanceId).singleResult();

            if (historicProcessInstance == null) {
                throw new YuheOAException("获取流程实例ID[" + processInstanceId + "]对应的历史流程实例失败！");
            } else {
                // 获取流程定义
                ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                        .getDeployedProcessDefinition(historicProcessInstance.getProcessDefinitionId());

                // 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
                List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().list();

                // 已执行的节点ID集合
                List<String> executedActivityIdList = new ArrayList<>();
                int index = 1;
                LOGGER.info("获取已经执行的节点ID");
                for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
                    executedActivityIdList.add(activityInstance.getActivityId());
                    LOGGER.info("第[" + index + "]个已执行节点=" + activityInstance.getActivityId() + " : " +activityInstance.getActivityName());
                    index++;
                }

                // 获取流程图图像字符流
                InputStream imageStream = ProcessDiagramGenerator.generateDiagram(processDefinition, "png", executedActivityIdList);

                response.setContentType("image/png");
                OutputStream os = response.getOutputStream();
                int bytesRead;
                byte[] buffer = new byte[8192];
                while ((bytesRead = imageStream.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                imageStream.close();
            }
            LOGGER.info("[完成]-获取流程图图像");
        } catch (Exception e) {
            LOGGER.error("【异常】-获取流程图失败！" + e.getMessage());
            throw new YuheOAException("获取流程图失败！" + e.getMessage());
        }
    }


}
