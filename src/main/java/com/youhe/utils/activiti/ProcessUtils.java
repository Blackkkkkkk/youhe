package com.youhe.utils.activiti;

import org.activiti.engine.*;

/**
 * 流程引擎工具类
 */
public class ProcessUtils {

    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    private static IdentityService identityService = processEngine.getIdentityService();
    private static RuntimeService runtimeService = processEngine.getRuntimeService();
    private static TaskService taskService = processEngine.getTaskService();
    private static RepositoryService repositoryService = processEngine.getRepositoryService();
    private static HistoryService historyService = processEngine.getHistoryService();


    public static void gotoAnyTask() {

    }

    public static void main(String[] args) {

        ProcessUtils.gotoAnyTask();
    }

}
