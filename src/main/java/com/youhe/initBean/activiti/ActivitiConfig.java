package com.youhe.initBean.activiti;


import org.activiti.engine.*;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;

/**
 * activiti配置
 */
@Configuration
public class ActivitiConfig {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private GlobalActivitiEventListener globalActivitiEventListener;

    @Bean
    @ConfigurationProperties(prefix = "spring.activiti")
    public SpringProcessEngineConfiguration springProcessEngineConfiguration() {
        SpringProcessEngineConfiguration spec = new SpringProcessEngineConfiguration();
        spec.setDataSource(dataSource);
        spec.setTransactionManager(platformTransactionManager);
        Resource[] resources = null;

        // 启动自动部署流程
        /*try {
            resources = new PathMatchingResourcePatternResolver().getResources("classpath*:processes/*.bpmn");
        } catch (IOException e) {
            e.printStackTrace();
        }
        spec.setDeploymentResources(resources);*/

        // 添加全局监听事件
        ArrayList<ActivitiEventListener> activitiEventListeners = new ArrayList<>();
        activitiEventListeners.add(globalActivitiEventListener);
        spec.setEventListeners(activitiEventListeners);

        // 解决流程图汉字乱码问题
        spec.setActivityFontName("宋体");
        spec.setLabelFontName("宋体");
        spec.setAnnotationFontName("宋体");

        return spec;
    }

    @Bean(value = "myProcessEngine")
    public ProcessEngineFactoryBean processEngine() {
        ProcessEngineFactoryBean processEngineFactoryBean = new ProcessEngineFactoryBean();
        processEngineFactoryBean.setProcessEngineConfiguration(springProcessEngineConfiguration());
        return processEngineFactoryBean;
    }

    @Bean
    public RuntimeService runtimeService(ProcessEngine processEngine) {
        return processEngine.getRuntimeService();
    }

    @Bean
    public RepositoryService repositoryService(ProcessEngine processEngine) {
        return processEngine.getRepositoryService();
    }

    @Bean
    public TaskService taskService(ProcessEngine processEngine) {
        return processEngine.getTaskService();
    }

    @Bean
    public HistoryService historyService(ProcessEngine processEngine) {
        return processEngine.getHistoryService();
    }

    @Bean
    public ManagementService managementService(ProcessEngine processEngine) {
        return processEngine.getManagementService();
    }

    @Bean
    public FormService formService(ProcessEngine processEngine) {
        return processEngine.getFormService();
    }

    @Bean
    public IdentityService identityService(ProcessEngine processEngine) {
        return processEngine.getIdentityService();
    }

}
