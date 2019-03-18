package com.youhe.initBean.activiti;

import cn.hutool.core.bean.BeanUtil;
import com.youhe.common.Constant;
import com.youhe.entity.activiti.FlowVariable;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GlobalActivitiEventListener implements ActivitiEventListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalActivitiEventListener.class);

    @Override
    public void onEvent(ActivitiEvent event) {
//        LOGGER.info("onEvent...");
//        LOGGER.info("getType = {}", event.getType());
        if (ActivitiEventType.TASK_CREATED.equals(event.getType())) {
//            TaskListener taskListener = (TaskListener) event;
            LOGGER.info("getExecutionId = {}", event.getExecutionId());
            LOGGER.info("getProcessDefinitionId = {}", event.getProcessDefinitionId());
            LOGGER.info("getProcessInstanceId = {}", event.getProcessInstanceId());
            ActivitiEntityEvent entityEvent = (ActivitiEntityEvent) event;
            Object entity = entityEvent.getEntity();
            if(entity instanceof TaskEntity) {
                TaskEntity task = (TaskEntity) entity;
                Map<String, Object> variables = task.getVariables();
                FlowVariable flowVariable = new FlowVariable();
                BeanUtil.copyProperties(variables.get(Constant.FLOW_VARIABLE_KEY), flowVariable);
                LOGGER.info("flowVariable = {}", flowVariable.toString());
                String nextUser = flowVariable.getNextUserId();
                String originUserId = task.getAssignee();
                LOGGER.info("originUserId = {}", originUserId);
                LOGGER.info("nextUser = {}", nextUser);
                task.setAssignee(nextUser);
                /*String newUserId = userMap.get(originUserId);
                if(StringUtil.isNotBlank(newUserId)){
                    task.setAssignee(newUserId);
                }*/
            }
        }
    }

    @Override
    public boolean isFailOnException() {
        LOGGER.info("isFailOnException ....");
        return true;
    }
}
