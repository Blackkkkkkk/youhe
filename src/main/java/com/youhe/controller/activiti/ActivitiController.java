package com.youhe.controller.activiti;


import com.youhe.activiti.engine.MyProcessEngine;
import com.youhe.common.Constant;
import com.youhe.controller.comm.BaseController;
import com.youhe.entity.activiti.FlowVariable;
import com.youhe.entity.activitiData.ACT_RE_MODEL_PROCDEF;
import com.youhe.entity.activitiData.ProdefTask;
import com.youhe.exception.YuheOAException;
import com.youhe.mapper.activiti.ActivitiMapper;
import com.youhe.utils.R;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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


    @GetMapping(value = "ProcessManagement")
    public ModelAndView ProcessManagement() {
        return new ModelAndView("activiti/manage/ProcessManagement");
    }

    @GetMapping(value = "/dealwith")
    public ModelAndView indexMyDealWith() {
        return new ModelAndView("activiti/manage/MyToDo");
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
    public  List<Model> modelList() {
        List<Model> modelList = myProcessEngine.getModelList();
      return modelList;
    }

    /**
     * 发布模型为流程定义
     */
    @RequestMapping("/deploy")
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
    @GetMapping(value = "start/{deploymentId}")
    public ModelAndView startProcess(@PathVariable String deploymentId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
        ProcessInstance processInstance = myProcessEngine.start(processDefinition.getId());
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        return new ModelAndView("redirect:form/task/" + task.getId());
    }

    /**
     * 提交任务
     */
    @RequestMapping(value = "submit/task")
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
        Map map = myProcessEngine.getTaskForm(taskId);
        if (map == null) {
            mv.setViewName(Constant.DEFAULT_FORM_NAME); // todo 跳转到没有权限的页面
            return mv;
        }
        FlowVariable flowVariable = (FlowVariable) map.get(Constant.FLOW_VARIABLE_KEY);
        mv.setViewName(Constant.FORM_PRFIX + flowVariable.getFormKey());
        mv.addObject(Constant.TASK_DATA_KEY, map);
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
     * @param userId 用户ID
     * @return
     */
    @GetMapping(value = "task/list")
    @ResponseBody
    public R myTaskList(String userId) {
        userId="1";
        List<ProdefTask> taskList=new ArrayList<>();
        try {
            taskList=myProcessEngine.getTaskList(userId);
            if(taskList.size()==0){
                throw new YuheOAException(500,"无数据");
            }
        }catch (YuheOAException e){
            R.error(e.getErrorCode(),e.getMsg());
        }
        return R.ok().put("tasklist", taskList);
    }

    /**
     * 我的已办
     *
     * @param userId 用户ID
     * @return
     */
    @GetMapping(value = "hisTask/list")
    public R hisTaskList(String userId) {

        return R.ok().put("hisTaskList", myProcessEngine.getHisTaskList(userId));
    }

    @GetMapping(value = "submitTask")
    public ModelAndView submitTask() {
        return new ModelAndView("activiti/submit_task");
    }
}
