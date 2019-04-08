package com.youhe.activiti.event;

import com.youhe.activiti.engine.MyProcessEngine;
import com.youhe.utils.spring.SpringContextUtils;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 【作用】请假申请结束事件示例<br>
 * 【说明】（无）
 *
 * @author kalvin
 * @Date 2019年04月08日 11:46
 */
public class AppEndEvent implements ExecutionListener {

    private MyProcessEngine myProcessEngine = SpringContextUtils.bean(MyProcessEngine.class);

    private final static Logger LOGGER = LoggerFactory.getLogger(AppEndEvent.class);

    @Override
    public void notify(DelegateExecution execution) {
        // 这里可以处理业务逻辑
        String eventName = execution.getEventName();
        LOGGER.info("流程监听器，执行了事件:{}", eventName);
        LOGGER.info("当前节点名称:{}", execution.getCurrentActivityName());
        String startUserId = myProcessEngine.getStartUserId(execution.getProcessInstanceId());
        LOGGER.info("流程启动者：{}", startUserId);
    }
}
