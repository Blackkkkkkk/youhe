package com.youhe.controller.activiti;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youhe.activiti.engine.MyProcessEngine;
import com.youhe.common.Constant;
import com.youhe.controller.comm.BaseController;
import com.youhe.entity.activiti.FlowVariable;
import com.youhe.entity.activiti.FormCodeData;
import com.youhe.entity.activitiData.ProdefTask;
import com.youhe.utils.R;
import com.youhe.utils.activiti.FormParseUtils;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.html.HTMLParagraphElement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
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
        Map<String, Object> map = myProcessEngine.getTaskForm(taskId);
        if (map == null) {
            mv.setViewName(Constant.DEFAULT_FORM_NAME); // todo 跳转到没有权限的页面
            return mv;
        }
        FlowVariable flowVariable = (FlowVariable) map.get(Constant.FLOW_VARIABLE_KEY);
//        mv.setViewName(Constant.FORM_PRFIX + flowVariable.getFormKey());
        mv.setViewName("activiti/common/form_temp");
        mv.addObject(Constant.TASK_DATA_KEY, map);
        FormCodeData taskFormCode = FormParseUtils.getTaskFormCode(flowVariable.getFormKey(), map);
        LOGGER.info("taskFormCode={}", taskFormCode.toString());
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
     * @param userId 用户ID
     * @return
     */
    @GetMapping(value = "task/list")
    @ResponseBody
    public R myTaskList(String userId) {

        List<ProdefTask>   taskList=myProcessEngine.getTaskList(userId);
            if(taskList.size()==0){
                return R.ok().put("tasklist", taskList);
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
        List<ProdefTask> hisTaskList = myProcessEngine.getHisTaskList(userId);
        if(hisTaskList.size()==0){
            return R.ok().put("hisTaskList", hisTaskList);
        }
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

    @GetMapping(value = "test")
    public ModelAndView test() {
        ModelAndView mv = new ModelAndView("activiti/common/form_temp");
        String s = FileUtil.readString(ClassUtil.getClassPath() + "templates/activiti/form/test.html", "UTF-8");
        Document doc = Jsoup.parse(s);
        Elements input = doc.select("table .form-control");
        Elements script = doc.select("script");
        String scriptStr = script.html();   // js脚本
        script.remove();

//        System.out.println(s);
        System.out.println("input.size() = " + input.size());
        input.forEach(ip -> {
            Element element = new Element("span");
            element.attr("name", ip.attr("name"));
            element.text("admin test");
            System.out.println("element.toString() = " + element.toString());
//            ip.replaceWith(element);
            System.out.println("ip.toString() = " + ip.toString());
        });
//        input.forEach();
        Elements table = doc.select("table");

        LOGGER.info("s={}", table.toString());
        mv.addObject("table", table.toString());
        mv.addObject("script", scriptStr);
        return mv;
    }
}
