package com.youhe.controller.activiti;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.youhe.biz.activiti.ActivitiBiz;
import com.youhe.controller.ActivitiModelController;
import com.youhe.entity.activitiData.ACT_RE_MODEL_PROCDEF;

import com.youhe.utils.shiro.AuthRealm;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import com.youhe.utils.workflow.ExportFlow;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/activiti")
public class ActivitiController {

    private static final Logger log = LogManager.getLogger(ActivitiModelController.class);


    @Autowired
    private ProcessEngine processEngine;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private ActivitiBiz activitiBiz;

    @RequestMapping(value = "/edit")
    public String Eidt() {

        return "activiti/activitiEidt";
    }

    @RequestMapping(value = "/deployList")
    public String deployList(Model model) {


        List<ACT_RE_MODEL_PROCDEF> list = activitiBiz.findList();


        model.addAttribute("list", list);

        return "activiti/DeployList";
    }

    /**
     * 发布模型为流程定义
     */
    @RequestMapping("/deploy")
    @ResponseBody
    public Map<String, String> deploy(String modelId) throws Exception {

        Map<String, String> hashMap = new HashMap<>();


        String message = "部署成功!";


        try {
            //获取模型
            RepositoryService repositoryService = processEngine.getRepositoryService();
            org.activiti.engine.repository.Model modelData = repositoryService.getModel(modelId);
            byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

            if (bytes == null) {

                message = "模型数据为空，请先设计流程并成功保存，再进行发布。";
                hashMap.put("message", "message");
                return hashMap;

            }

            JsonNode modelNode = new ObjectMapper().readTree(bytes);

            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            if (model.getProcesses().size() == 0) {

                message = "数据模型不符要求，请至少设计一条主线流程。";
                hashMap.put("message", "message");
                return hashMap;
            }
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

            //发布流程
            String processName = modelData.getName() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment()
                    .name(modelData.getName())
                    .addString(processName, new String(bpmnBytes, "UTF-8"))
                    .deploy();
            modelData.setDeploymentId(deployment.getId());
            repositoryService.saveModel(modelData);

            System.out.println(deployment.getId());
            System.out.println(deployment.getName());

        } catch (Exception e) {
            message = "数据异常!";

        }

        hashMap.put("message", message);
        return hashMap;
    }


    /**
     * 启动流程
     */
    @RequestMapping("/start")
    @ResponseBody
    public Map<String, String> startProcess(String keyName) {

        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();

        Map<String, String> hashMap = new HashMap<>();


        String message = "流程启动成功!";
        String state = "fail";

        //  后期基于shiro 做权限控制，可启动流程者
        if (shiroUser.getUserAccount() == null) {
            message = "会话失效，请重新登录页面！";

        } else {
            try {

                // 流程变量
                Map<String, Object> hashMap1 = new HashMap<>();

                hashMap1.put("employeeName", "Kermit");
                hashMap1.put("numberOfDays", new Integer(4));
                hashMap1.put("vacationMotivation", "I'm really tired!");
                RuntimeService runtimeService = processEngine.getRuntimeService();
                //  ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("first", hashMap1);

                ProcessInstance process = processEngine.getRuntimeService().startProcessInstanceByKey(keyName);
                state = "success";
                //  log.info(process.getId() + " : " + process.getProcessDefinitionId());
            } catch (ActivitiException e) {
                if (e.getMessage().indexOf("no processes deployed with key") != -1) {
                    log.warn("没有部署流程!", e);
                    message = "没有部署流程";
                } else if (e.getMessage().indexOf("Cannot start process instance") != -1) {
                    log.warn("该流程已被暂停启动，请重新激活该流程!", e);
                    message = "该流程已被暂停启动，请重新激活该流程!";
                } else {
                    log.error("启动请假流程失败：", e);
                    message = "启动请假流程失败";
                }
            } catch (Exception e) {
                log.error("启动请假流程失败：", e);
                message = "启动请假流程失败";
            }
        }

        hashMap.put("message", message);
        hashMap.put("state", state);

        return hashMap;
    }


    /**
     * 暂停流程
     */
    @RequestMapping("/stop")
    @ResponseBody
    public Map<String, String> stopProcess(String keyName) {
        Map<String, String> hashMap = new HashMap<String, String>();
        String message = "流程暂停成功!";
        String state = "fail";


        try {
            /*
            ------------------  流程挂起-----------------------------------
              流程挂起:挂起后，节点流程无法处理节点

              报错信息：Cannot com plete a suspended task

              RuntimeService runtimeService = engine.getRuntimeService();
              String processInstanceId="1801";
              //根据一个流程实例的id挂起该流程实例
              runtimeService.suspendProcessInstanceById(processInstanceId);

              挂起激活
              RuntimeService runtimeService = engine.getRuntimeService();
              String processInstanceId="1801";

              runtimeService.activateProcessInstanceById(processInstanceId);

              执行步骤：

              　　1:通过流程定义的key或者id启动一个流程实例
　　              2:根据流程实例的id来挂起这个流程实例
　　              3:得到下一个节点的对应的任务的id，调用taskService来完成这个任务观察效果
                 4:重新激活这个流程实例
                 5:继续完成这个流程实例

             ------------------------------------------------------------



             ------------------  流程暂停-----------------------------------

              流程挂起:在程序中，我们需要暂停一个流程定义，停止所有的该流程定义下的流程实例，并且不允许发起这个流程定义的流程实例，
                      那么我们就需要挂起这个流程定义

              报错信息：Cannot start process instance


              RepositoryService repositoryService = engine.getRepositoryService();

              String processDefinitionKey ="purchasingflow";
              //根据流程定义的key暂停一个流程定义
              repositoryService.suspendProcessDefinitionByKey(processDefinitionKey );

              暂停激活
              RuntimeService runtimeService = engine.getRuntimeService();
              String processInstanceId="1801";

              runtimeService.activateProcessInstanceById(processInstanceId);

              执行步骤：

              　　1:通过流程定义的key或者id启动一个流程实例
　　              2:根据流程实例的id来挂起这个流程实例
　　              3:得到下一个节点的对应的任务的id，调用taskService来完成这个任务观察效果
                 4:重新激活这个流程实例
                 5:继续完成这个流程实例

             ------------------------------------------------------------


             */

            RepositoryService repositoryService = processEngine.getRepositoryService();

            repositoryService.suspendProcessDefinitionByKey(keyName);

            state = "success";
        } catch (ActivitiException e) {

            message = "流程暂停失败！";
            log.debug("stopProcess erro:" + e);
        }
        hashMap.put("message", message);
        hashMap.put("state", state);

        return hashMap;

    }

    /**
     * 激活流程
     */
    @RequestMapping("/activation")
    @ResponseBody
    public Map<String, String> activationProcess(String keyName) {
        Map<String, String> hashMap = new HashMap<String, String>();
        String message = "流程激活成功!";
        String state = "fail";


        try {

            RepositoryService repositoryService = processEngine.getRepositoryService();
            repositoryService.activateProcessDefinitionByKey(keyName);

            state = "success";
        } catch (ActivitiException e) {

            message = "流程激活失败！";
            log.debug("activationProcess erro:" + e);
        }

        hashMap.put("message", message);
        hashMap.put("state", state);

        return hashMap;
    }

    /**
     * 删除流程流程
     */
    @RequestMapping("/del")
    @ResponseBody
    public Map<String, String> del(String keyName) {
        Map<String, String> hashMap = new HashMap<String, String>();
        String message = "流程删除成功!";
        String state = "fail";


        try {

            RepositoryService repositoryService = processEngine.getRepositoryService();
            // 获取仓库服务对象


            // 普通删除，如果当前规则下有正在执行的流程，则抛异常
            // repositoryService.deleteDeployment(keyName);
            // 级联删除,会删除和当前规则相关的所有信息，包括历史
            repositoryService.deleteDeployment(keyName, true);

            state = "success";
        } catch (ActivitiException e) {

            message = "流程删除失败！";
            log.debug("activationProcess erro:" + e);
        }

        hashMap.put("message", message);
        hashMap.put("state", state);

        return hashMap;
    }


}
