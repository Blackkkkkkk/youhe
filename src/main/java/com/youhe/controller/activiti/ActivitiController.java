package com.youhe.controller.activiti;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.youhe.controller.ActivitiModelController;
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
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
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
    ProcessEngine processEngine;
    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping(value = "/edit")
    public String Eidt() {

        return "activiti/activitiEidt";
    }

    @RequestMapping(value = "/deployList")
    public String deployList(Model model) {

        RepositoryService repositoryService = processEngine.getRepositoryService();

        model.addAttribute("list", repositoryService.createModelQuery().list());

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



}
