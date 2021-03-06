package com.youhe.activiti.ext;

import cn.hutool.core.util.StrUtil;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.DelegationState;

import java.util.Iterator;
import java.util.Map;

/**
 * 【作用】节点跳转<br>
 * 【说明】（无）
 *
 * @author kalvin
 * @Date 2019年03月27日 12:04
 */
public class NodeJumpTaskCmd implements Command<Void> {

    private String executionId;
    private ActivityImpl desActivity;
    private Map<String, Object> varMap;
    private ActivityImpl currentActivity;

    @Override
    public Void execute(CommandContext commandContext) {
        ExecutionEntityManager executionEntityManager = commandContext.getExecutionEntityManager();
        // 获取当前流程的executionId，因为在并发的情况下executionId是唯一的。
        ExecutionEntity executionEntity = executionEntityManager
                .findExecutionById(executionId);
        executionEntity.setVariables(varMap);
        executionEntity.setEventSource(this.currentActivity);
        executionEntity.setActivity(this.currentActivity);
        // 根据executionId 获取Task
        Iterator<TaskEntity> localIterator = commandContext
                .getTaskEntityManager()
                .findTasksByExecutionId(this.executionId).iterator();

        while (localIterator.hasNext()) {
            TaskEntity taskEntity = localIterator.next();

            // owner不为空说明可能存在委托任务
            if (StrUtil.isNotBlank(taskEntity.getOwner())) {
                DelegationState delegationState = taskEntity.getDelegationState();
                // 把被委托人代理处理后的任务交回给委托人
                if (DelegationState.PENDING == delegationState) {
                    taskEntity.resolve();
                }
            }

            // 触发任务监听
            taskEntity.fireEvent("complete");
            // 删除任务的原因
            commandContext.getTaskEntityManager().deleteTask(taskEntity, "completed", false);
        }
        executionEntity.executeActivity(this.desActivity);
        return null;
    }

    /**
     * 构造参数 可以根据自己的业务需要添加更多的字段
     *
     * @param executionId     执行ID
     * @param desActivity     目标活动
     * @param varMap          流程变量
     * @param currentActivity 当前活动
     */
    public NodeJumpTaskCmd(String executionId, ActivityImpl desActivity,
                           Map<String, Object> varMap, ActivityImpl currentActivity) {
        this.executionId = executionId;
        this.desActivity = desActivity;
        this.varMap = varMap;
        this.currentActivity = currentActivity;
    }

}
