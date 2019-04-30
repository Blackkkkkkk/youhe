package com.youhe.initBean.activiti;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.youhe.common.Constant;
import com.youhe.entity.activiti.FlowVariable;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.IdentityLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class GlobalActivitiEventListener implements ActivitiEventListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalActivitiEventListener.class);

    @Override
    public void onEvent(ActivitiEvent event) {
//        LOGGER.info("onEvent...");
//        LOGGER.info("getType = {}", event.getType());
        if (ActivitiEventType.TASK_CREATED.equals(event.getType())) {
//            TaskListener taskListener = (TaskListener) event;
            LOGGER.info("ExecutionId = {}, ProcessDefinitionId = {}, ProcessInstanceId = {}", event.getExecutionId(), event.getProcessDefinitionId(), event.getProcessInstanceId());
            ActivitiEntityEvent entityEvent = (ActivitiEntityEvent) event;
            Object entity = entityEvent.getEntity();
            if(entity instanceof TaskEntity) {
                TaskEntity task = (TaskEntity) entity;
                String nextUser = task.getAssignee();   // 节点已配置的审批人
                Set<IdentityLink> candidates = task.getCandidates();    // 节点已配置的审批人候选组

                LOGGER.info("节点已配置的审批人：{}", nextUser);
                if (StrUtil.isBlank(nextUser)) {
                    Map<String, Object> variables = task.getVariables();
                    FlowVariable flowVariable = new FlowVariable();
                    BeanUtil.copyProperties(variables.get(Constant.FLOW_VARIABLE_KEY), flowVariable);
                    LOGGER.info("flowVariable = {}", flowVariable.toString());
                    nextUser = flowVariable.getNextUserId();
                    LOGGER.info("nextUser = {}", nextUser);
                }

                // 设置审批人
                task.setAssignee(nextUser);
                // 添加候选组用户
                candidates.forEach(candidate -> {
                    task.addCandidateUser(candidate.getUserId());
                    LOGGER.info("节点候选组审批人：{}", candidate.getUserId());
                });
            }
        }
    }

    @Override
    public boolean isFailOnException() {
        LOGGER.info("isFailOnException ....");
        return true;
    }
}
