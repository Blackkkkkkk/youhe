package com.youhe.initBean.activiti;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.youhe.common.Constant;
import com.youhe.entity.activiti.Delegate;
import com.youhe.entity.activiti.FlowVariable;
import com.youhe.service.activiti.DelegateService;
import com.youhe.service.activiti.HiDelegateService;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.IdentityLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Component
public class GlobalActivitiEventListener implements ActivitiEventListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(GlobalActivitiEventListener.class);

    @Autowired
    private DelegateService delegateService;
    @Autowired
    private HiDelegateService hiDelegateService;

    @Override
    public void onEvent(ActivitiEvent event) {
        if (ActivitiEventType.TASK_CREATED.equals(event.getType())) {
            LOGGER.info("ExecutionId = {}, ProcessDefinitionId = {}, ProcessInstanceId = {}", event.getExecutionId(), event.getProcessDefinitionId(), event.getProcessInstanceId());
            ActivitiEntityEvent entityEvent = (ActivitiEntityEvent) event;
            Object entity = entityEvent.getEntity();
            if(entity instanceof TaskEntity) {
                TaskEntity task = (TaskEntity) entity;
                LOGGER.info("TASK_CREATED task={}", task.getTaskDefinitionKey());
                String nextUser = task.getAssignee();   // 节点已配置的审批人
                Set<IdentityLink> candidates = task.getCandidates();    // 节点已配置的审批人候选组

                Map<String, Object> variables = task.getVariables();
                FlowVariable flowVariable = new FlowVariable();
                BeanUtil.copyProperties(variables.get(Constant.FLOW_VARIABLE_KEY), flowVariable);
                LOGGER.info("flowVariable = {}", flowVariable.toString());

                LOGGER.info("节点已配置的审批人：{}", nextUser);
                if (StrUtil.isBlank(nextUser)) {
                    nextUser = flowVariable.getNextUserId();
                    LOGGER.info("nextUser = {}", nextUser);
                }

                // 设置审批人
                if (nextUser.split(",").length > 1) {   // 多用户任务分配
                    task.addCandidateUsers(Arrays.asList(nextUser.split(",")));
                } else {
                    task.setAssignee(nextUser);
                }

                // 设置了代理申请
                if (flowVariable.isAgency()) {
                    Delegate agency = delegateService
                            .getDelegateAssigneeAndProcessDefId(nextUser, task.getProcessDefinitionId(), Constant.DELEGATE_TYPE_0);
                    if (agency != null) {
                        Date startTime = agency.getStartTime();
                        Date endTime = agency.getEndTime();
                        long l = DateUtil.betweenMs(startTime, endTime);
                        if (l >= 0) {   // 由代理人发起申请
                            task.delegate(agency.getAttorney());
                            hiDelegateService.saveHiDelegate(nextUser, agency.getAttorney(), task.getId(), Constant.DELEGATE_TYPE_0);
                        }
                    }
                }

                // 如果开启委托功能，把当前任务转交到委托代理人
                if (!flowVariable.isFirstNode()) {
                    Delegate delegate = delegateService
                            .getDelegateAssigneeAndProcessDefId(nextUser, task.getProcessDefinitionId(), Constant.DELEGATE_TYPE_1);
                    if (delegate != null) {
                        Date startTime = delegate.getStartTime();
                        Date endTime = delegate.getEndTime();
                        long l = DateUtil.betweenMs(startTime, endTime);
                        if (l >= 0) {   // 转交委托人办理
                            task.delegate(delegate.getAttorney());
                            hiDelegateService.saveHiDelegate(nextUser, delegate.getAttorney(), task.getId(), Constant.DELEGATE_TYPE_1);
                            LOGGER.info("任务实例{}委托给{}代理处理", task.getId(), delegate.getAttorney());
                        }
                    }
                }

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
