package com.youhe.service.activiti;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.youhe.entity.activiti.Delegate;

/**
 * <p>
 * 委托表 服务类
 * </p>
 *
 * @author Kalvin
 * @since 2019-05-23
 */
public interface DelegateService extends IService<Delegate> {


    IPage<Delegate> listDelegatePage(String processName, int current, int size);

    Delegate getDelegateAssigneeAndProcessDefId(String assignee, String processDefId);

    /**
     * 是否委托任务
     * @param assignee 办理人
     * @param processDefId 流程定义ID
     * @return
     */
    boolean isDelegate(String assignee, String processDefId);

}
